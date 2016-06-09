package net.glease.chem.simple.scoping;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

class ScopeManager<T_PARENT extends IScope<?, T_PARENT>, T_THIS extends IScope<T_PARENT, T_THIS>> {
	static WeakMapping<IScope<?, ?>, ScopeManager<?, ?>> instances = new WeakMapping<>(ScopeManager::clear);

	@SuppressWarnings("unchecked")
	static <T_PARENT extends IScope<?, T_PARENT>, T_THIS extends IScope<T_PARENT, T_THIS>> ScopeManager<T_PARENT, T_THIS> get(
			IScope<T_PARENT, T_THIS> scope) {
		return (ScopeManager<T_PARENT, T_THIS>) instances.get(scope, ScopeManager::new);
	}

	private final WeakReference<IScope<T_PARENT, T_THIS>> ref;
	private final Map<String, IScoped<T_THIS>> ids = new HashMap<>();
	private final Set<BindingPlugin<T_THIS>> plugins = Collections.newSetFromMap(new IdentityHashMap<>());

	T_PARENT parent;

	ScopeManager(IScope<T_PARENT, T_THIS> scope) {
		ref = new WeakReference<IScope<T_PARENT, T_THIS>>(scope);
	}

	void clear() {
		ids.clear();
	}

	public void install(BindingPlugin<T_THIS> plugin) {
		if (!plugins.add(plugin))
			throw new IllegalArgumentException("duplicate plugin: " + plugin);
	}

	public void onBind(IScoped<T_THIS> o) {
		IScope<T_PARENT, T_THIS> s = ref.get();
		if (ref == null) {// Theoretically impossible
			ids.clear();
			return;
		}
		String id = o.getId();
		IScoped<T_THIS> old = ids.get(id);
		if (!o.equals(old) && old != null)
			throw new DuplicateElementInScopeException(old, o, s, id);
		ids.put(id, o);
		for (BindingPlugin<T_THIS> plugin : plugins) {
			plugin.onBind(s, o);
		}
	}

	public void onUnbind(IScoped<T_THIS> o) {
		IScope<T_PARENT, T_THIS> s = ref.get();
		if (ref == null) {// Theoretically impossible
			ids.clear();
			return;
		}
		String id = o.getId();
		IScoped<T_THIS> old = ids.get(id);
		if (!o.equals(old))
			throw new ScopeException("Not binded to this scope", s, o);
		ids.remove(id);
		for (BindingPlugin<T_THIS> plugin : plugins) {
			plugin.onUnbind(s, o);
		}
	}
}
