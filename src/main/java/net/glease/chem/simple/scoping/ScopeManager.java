package net.glease.chem.simple.scoping;

import java.lang.ref.WeakReference;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class ScopeManager<T_PARENT extends IScope<?, T_PARENT>, T_THIS extends IScope<T_PARENT, T_THIS>> {
	private static final WeakMapping<IScope<?, ?>, ScopeManager<?, ?>> instances = new WeakMapping<IScope<?, ?>, ScopeManager<?, ?>>(
			ScopeManager::clear);
	private static final ThreadLocal<WeakReference<Map.Entry<IScope<?, ?>, ScopeManager<?, ?>>>> WORKING = new ThreadLocal<>();

	static <T_PARENT extends IScope<?, T_PARENT>, T_THIS extends IScope<T_PARENT, T_THIS>> ScopeManager<T_PARENT, T_THIS> get(
			final IScope<T_PARENT, T_THIS> scope) {
		WeakReference<Entry<IScope<?, ?>, ScopeManager<?, ?>>> r = WORKING.get();

		if (r != null) {
			Entry<IScope<?, ?>, ScopeManager<?, ?>> e;
			if ((e = r.get()) != null && scope == e.getKey()) {
				@SuppressWarnings("unchecked")
				ScopeManager<T_PARENT, T_THIS> v = (ScopeManager<T_PARENT, T_THIS>) e.getValue();
				return v;
			}
		}

		@SuppressWarnings("unchecked")
		ScopeManager<T_PARENT, T_THIS> found = (ScopeManager<T_PARENT, T_THIS>) instances.get(scope, ScopeManager::new);
		WORKING.set(new WeakReference<>(new AbstractMap.SimpleEntry<>(scope, found)));
		return found;
	}

	private final T_THIS ref;
	private T_PARENT scope;
	private final Map<String, IScoped<T_THIS>> ids = new HashMap<>();
	private final Set<BindingPlugin<T_THIS>> plugins = Collections.newSetFromMap(new IdentityHashMap<>());

	private ScopeManager(final T_THIS scope) {
		ref = scope;
	}

	private void clear() {
		scope = null;
		plugins.clear();
		ids.clear();
	}

	public void install(final BindingPlugin<T_THIS> plugin) {
		if (!plugins.add(plugin))
			throw new IllegalArgumentException("duplicate plugin: " + plugin);
	}

	public boolean bind(final T_PARENT s) {
		if (s == scope)
			return false;

		if (scope != null)
			scope.onUnbind(ref);
		if (s != null)
			s.onBind(ref);
		scope = s;
		return true;
	}

	public T_PARENT scope() {
		return scope;
	}

	public boolean onBind(final IScoped<T_THIS> o) {
		String id = o.getId();
		IScoped<T_THIS> old = ids.get(id);
		if (o.equals(old))
			return false;
		if (old != null)
			throw new DuplicateElementInScopeException(old, o, ref, id);
		ids.put(id, o);
		for (BindingPlugin<T_THIS> plugin : plugins)
			plugin.onBind(ref, o);
		return true;
	}

	public void onUnbind(final IScoped<T_THIS> o) {
		String id = o.getId();
		IScoped<T_THIS> old = ids.get(id);
		if (!o.equals(old))
			throw new ScopeException("Not binded to this scope", ref, o);
		ids.remove(id);
		for (BindingPlugin<T_THIS> plugin : plugins)
			plugin.onUnbind(ref, o);
	}

	public Set<BindingPlugin<T_THIS>> plugins() {
		return Collections.unmodifiableSet(plugins);
	}

	public boolean uninstall(final BindingPlugin<T_THIS> plugin) {
		return plugins.remove(plugin);
	}

	public void updateId(final String oldId, final IScoped<T_THIS> updated) {
		IScoped<T_THIS> o = ids.get(oldId);
		if (o == null)
			throw new ScopeException(String.format("No such element with old id \"%s\"!", oldId), ref, updated);
		ids.remove(o, updated);
		ids.put(updated.getId(), updated);
		for (BindingPlugin<T_THIS> plugin : plugins)
			plugin.updateId(ref, oldId, updated);
	}
}
