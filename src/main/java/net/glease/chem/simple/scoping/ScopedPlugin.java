package net.glease.chem.simple.scoping;

public interface ScopedPlugin<T_SCOPE extends IScope<?, T_SCOPE>> {
	/**
	 * Get the version info of this {@link BindingPlugin}
	 *
	 * @return the version info
	 */
	String info();

	/**
	 * Get the name of this {@link BindingPlugin}
	 *
	 * @return the name
	 */
	String name();

	/**
	 * Invoked before changing everything in {@link IScoped#bind(IScope)}.
	 *
	 * @param newScope
	 *            the new scope
	 * @param elem
	 *            element
	 */
	void beforeBind(T_SCOPE newScope, IScoped<T_SCOPE> elem);

	/**
	 * Invoked after everything in {@link IScoped#bind(IScope)}. <b> SHOULD NOT
	 * THROW ANY SCOPE EXCEPTIONS!</b>
	 *
	 * @param newScope
	 *            the new scope
	 * @param elem
	 *            element
	 */
	void afterBind(T_SCOPE newScope, IScoped<T_SCOPE> elem);

	/**
	 * Get the target of this {@link BindingPlugin}. A generic binding plugin
	 * can choose to return {@code IScope.class}
	 *
	 * @return the target
	 */
	Class<?> target();

	/**
	 * Get the vendor info of this {@link BindingPlugin}
	 *
	 * @return the vendor info
	 */
	String vendor();

	/**
	 * Get the version info of this {@link BindingPlugin}
	 *
	 * @return the version info
	 */
	String version();
}
