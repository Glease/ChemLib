package net.glease.chem.simple.util;

import net.glease.chem.simple.datastructure.ChemDatabaseComponent;
import net.glease.chem.simple.scoping.IScope;
import net.glease.chem.simple.scoping.IScoped;

public interface BindingPlugin {
	Class<? extends ChemDatabaseComponent> target();

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
	<T extends IScope<?, T>> void onBind(IScope<?, T> scope, IScoped<T> elem);

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
	<T extends IScope<?, T>> void onUnbind(IScope<?, T> scope, IScoped<T> elem);

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
}
