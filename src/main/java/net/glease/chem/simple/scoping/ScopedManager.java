package net.glease.chem.simple.scoping;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

class ScopedManager<T_SCOPE extends IScope<?, T_SCOPE>> {
	private static WeakMapping<IScoped<?>, ScopedManager<?>> instances = new WeakMapping<IScoped<?>, ScopedManager<?>>(ScopedManager::clear);

	@SuppressWarnings("unchecked")
	static <T_SCOPE extends IScope<?, T_SCOPE>> ScopedManager<T_SCOPE> get(final IScoped<T_SCOPE> scope) {
		return (ScopedManager<T_SCOPE>) instances.get(scope, ScopedManager::new);
	}

	private final IScoped<T_SCOPE> ref;
	private T_SCOPE scope;
	private final Set<ScopedPlugin<T_SCOPE>> plugins = Collections.newSetFromMap(new WeakHashMap<>());

	private ScopedManager(final IScoped<T_SCOPE> ref) {
		super();
		this.ref = ref;
	}

	private void clear() {
		scope = null;
	}

	public boolean bind(final T_SCOPE s) {
		if (s == scope)
			return false;

		if (scope != null)
			scope.onUnbind(ref);
		if (s != null)
			s.onBind(ref);
		scope = s;
		return true;
	}

	public T_SCOPE scope() {
		return scope;
	}

	public void install(final ScopedPlugin<T_SCOPE> plugin) {
		if(!plugins.add(plugin))
			throw new IllegalArgumentException("duplicate plugin: " + plugin);
	}

	public boolean uninstall(final ScopedPlugin<T_SCOPE> plugin) {
		return plugins.remove(plugin);
	}

	public Set<ScopedPlugin<T_SCOPE>> plugins() {
		return Collections.unmodifiableSet(plugins);
	}

}
