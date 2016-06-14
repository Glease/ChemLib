package net.glease.chem.simple.scoping;

import java.lang.ref.WeakReference;

/**
 * Indicates this is a scope. Scopes themselves are also elements of other
 * scopes, unless they declare their parent scope is {@link ROOT}.
 * <h2>Scopes with their parent declared as {@link ROOT}</h2> These scopes's
 * {@link #bind(IScope)} method should always throw an
 *
 * <h2>Implementation Notes</h2>
 * <p>
 * While all four methods have default implementation, clients are still
 * suggested to properly provide local implementations to avoid lag spikes
 * caused enormous internal hash maps (used to store the mapping from
 * {@link IScope} to scope managers) resulted from the heavy use of default
 * implementation.
 *
 * <h2>Reference Strength</h2>
 * <p>
 * An {@link IScope} should ensure all its elements are strongly reachable
 * unless itself becomes phantom reachable or unreachable. It should also clear
 * the references to its elements as soon as itself becomes phantom reachable or
 * unreachable.
 *
 * <h2>Calling default implementation from sub-classes</h2>
 * <p>
 * Sub-classes can happily invoke default implementations many times during
 * one of their method call. <p>
 * e.g.
 * <pre>
 * 	&#64;Override
 * 	public boolean bind(SomeScope newScope) {
 * 		if (newScope == scope())
 * 			return false;
 * 		// .. do something
 * 		SomeScope oldScope = scope();
 * 		// ..
 * 		return IScope.super.bind(newScope);
 * 	}
 * </pre>
 * We cache scope manager on a per thread basis using {@link WeakReference}.
 * <h2>Typical Usage</h2>
 *
 * <pre>
 * public class Common implements IScope&lt;IScope.ROOT, Common> {
 * 	&#64;Override
 * 	public boolean bind(IScope.ROOT unused) {
 * 		throw new UnsupportedOperationException();
 * 	}
 *
 * 	&#64;Override
 * 	public IScope.ROOT scope() {
 * 		throw new UnsupportedOperationException();
 * 	}
 * }
 *
 * public class SubScope implements IScope&lt;Common, SubScope> {
 * }
 * </pre>
 *
 * @author glease
 *
 * @param <T_PARENT>
 *            its parent {@link IScope} type.
 * @param <T_THIS>
 *            its type.
 * @since 0.1
 */
public interface IScope<T_PARENT extends IScope<?, T_PARENT>, T_THIS extends IScope<T_PARENT, T_THIS>>
extends IScoped<T_PARENT> {
	/**
	 * Root is the synthetic holder indicating some {@link IScope} is a top
	 * level scope, i.e. has no parent. If a {@link IScope} declare it's parent
	 * to be of type {@link ROOT}, it can be always considered in an undefined
	 * scope.
	 *
	 * @author glease
	 *
	 */
	final class ROOT implements IScope<ROOT, ROOT> {
		private ROOT() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean bind(final ROOT parent) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getId() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean onBind(final IScoped<ROOT> o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void onUnbind(final IScoped<ROOT> o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ROOT scope() {
			throw new UnsupportedOperationException();
		}
	}

	default void install(final BindingPlugin<T_THIS> plugin) {
		ScopeManager.get(this).install(plugin);
	}

	/**
	 * Called upon {@link IScoped#bind(IScope) o.bind(this)}.
	 * {@link #onUnbind(IScoped) o.scope().onUnbind()} are already called at the
	 * moment, but {@link IScoped#scope()} still return {@code o}'s original
	 * scope.
	 * <p>
	 * Implementations should return false as soon as it found the given
	 * {@code o} is already in this scope. It should not notify any installed
	 * plugin about this addition. It should not substitute the old element with
	 * the new equivalent, either.
	 *
	 * @param o
	 *            the {@link IScoped} to be bind to this {@link IScope}.
	 * @return <code>true</code> if element changed, <code>false</code> if
	 *         binded element is already in this scope.
	 * @throws DuplicateElementInScopeException
	 *             if there is an {@link IScoped} with same id already present
	 *             and it's not equal to {@code o} (i.e.
	 *             {@code !o.equals(existing)})
	 * @throws ScopeException
	 *             if this {@link IScope} deny such action. If thrown, it should
	 *             be guaranteed that no observable state change happens to this
	 *             {@link IScope} unless the {@link IScope} thinks the given
	 *             {@link IScoped} is malicious and require human moderation.
	 */
	default boolean onBind(final IScoped<T_THIS> o) {
		return ScopeManager.get(this).onBind(o);
	}

	/**
	 * Called upon {@link IScoped#bind(IScope) o.bind(this)}.
	 * {@link #onBind(IScoped) newScope.onBind(o)} aren't called yet.
	 * {@link IScoped#scope()} still return {@code this}.
	 *
	 * @param o
	 *            the {@link IScoped} which will be unbinded.
	 * @throws ScopeException
	 *             if this {@link IScope} deny such action. If thrown, it should
	 *             be guaranteed that no observable state change happens to this
	 *             {@link IScope} unless the {@link IScope} thinks the given
	 *             {@link IScoped} is malicious and require human moderation.
	 */
	default void onUnbind(final IScoped<T_THIS> o) {
		ScopeManager.get(this).onUnbind(o);
	}

	@Override
	default <T extends T_PARENT> boolean bind(final T newScope) {
		return ScopeManager.get(this).bind(newScope);
	}

	@Override
	default T_PARENT scope() {
		return ScopeManager.get(this).scope();
	}
}
