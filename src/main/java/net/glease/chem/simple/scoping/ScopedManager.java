package net.glease.chem.simple.scoping;

class ScopedManager<T_SCOPE extends IScope<?, T_SCOPE>> {
	private static WeakMapping<IScoped<?>, ScopedManager<?>> instances = new WeakMapping<IScoped<?>, ScopedManager<?>>(ScopedManager::clear);

	@SuppressWarnings("unchecked")
	static <T_SCOPE extends IScope<?, T_SCOPE>> ScopedManager<T_SCOPE> get(IScoped<T_SCOPE> scope) {
		return (ScopedManager<T_SCOPE>) instances.get(scope, ScopedManager::new);
	}

	private final IScoped<T_SCOPE> ref;
	private T_SCOPE scope;

	private ScopedManager(IScoped<T_SCOPE> ref) {
		super();
		this.ref = ref;
	}
	
	private void clear() {
		scope = null;
	}

	public boolean bind(T_SCOPE s) {
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

}
