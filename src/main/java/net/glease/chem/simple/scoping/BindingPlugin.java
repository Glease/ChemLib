package net.glease.chem.simple.scoping;

/**
 * Define a plugin to be installed on {@link IScope IScopes}. The plugin should be
 * stateless.
 *
 * @author glease
 *
 * @param <T_THIS>
 *            the type of scope this {@link BindingPlugin} is designed for
 * @since 0.1
 */
public interface BindingPlugin<T_THIS extends IScope<?, T_THIS>> {
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
	 * {@link IScope} instance, the invocation order of these plugins are
	 * undefined. Clients should synchronize externally if a instance of
	 * {@link BindingPlugin} has to be shared among multiple threads.
	 *
	 * @param scope
	 * @param elem
	 */
	void onBind(T_THIS scope, IScoped<T_THIS> elem);

	/**
	 * Invoked immediately after the {@link IScope}'s own housekeeping job is
	 * done. If there are multiple {@link BindingPlugin} installed on a single
	 * {@link IScope} instance, the invocation order of these plugins are
	 * undefined. Clients should synchronize externally if a instance of
	 * {@link BindingPlugin} has to be shared among multiple threads.
	 *
	 * @param scope
	 * @param oldId
	 * @param updated
	 */
	void updateId(T_THIS scope, String oldId, IScoped<T_THIS> updated);

	/**
	 * Invoked immediately after {@link IScope#onUnbind(IScoped) T.onUnbind()}.
	 * If there are multiple {@link BindingPlugin} installed on a single
	 * {@link IScope} instance, the invocation order of these plugins are
	 * undefined. Clients should synchronize externally if a instance of
	 * {@link BindingPlugin} has to be shared among multiple threads.
	 *
	 * @param scope
	 * @param elem
	 */
	void onUnbind(T_THIS scope, IScoped<T_THIS> elem);

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
