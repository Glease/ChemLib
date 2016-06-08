package net.glease.chem.simple.scoping;

/**
 * Define a plugin to be installed on {@link IScope IScopes}.
 * 
 * @author glease
 *
 * @param <T>
 *            the type of scope this {@link BindingPlugin} is designed for
 * @since 0.1
 */
public interface BindingPlugin<T extends IScope<?, T>> {
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
	 * Invoked immediately after {@link IScope#onBind(IScoped) T.onBind()}. If
	 * there are multiple {@link BindingPlugin} installed on a single
	 * {@link IScoped} instance, the invocation order of these plugins are
	 * undefined. Clients should synchronize externally if a instance of
	 * {@link BindingPlugin} has to be shared among multiple threads.
	 * 
	 * @param scope
	 * @param elem
	 */
	void onBind(IScope<?, T> scope, IScoped<T> elem);

	/**
	 * Invoked immediately after {@link IScope#onUnbind(IScoped) T.onUnbind()}.
	 * If there are multiple {@link BindingPlugin} installed on a single
	 * {@link IScoped} instance, the invocation order of these plugins are
	 * undefined. Clients should synchronize externally if a instance of
	 * {@link BindingPlugin} has to be shared among multiple threads.
	 * 
	 * @param scope
	 * @param elem
	 */
	void onUnbind(IScope<?, T> scope, IScoped<T> elem);

	Class<T> target();

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
