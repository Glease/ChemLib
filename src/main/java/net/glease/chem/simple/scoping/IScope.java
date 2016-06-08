package net.glease.chem.simple.scoping;

/**
 * While all four methods have default implementation, clients are still
 * suggested to properly provide local implementations.
 * <p>
 * Typical usage:
 * 
 * <pre>
 * public class Common implements IScope&lt;IScope.ROOT, Common> {
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
		public void bind(ROOT parent) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getId() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void onBind(IScoped<ROOT> o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void onUnbind(IScoped<ROOT> o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ROOT scope() {
			throw new UnsupportedOperationException();
		}
	}

	default void install(BindingPlugin<T_THIS> plugin) {
		ScopeManager.get(this).install(plugin);
	}

	/**
	 * Called upon {@link IScoped#bind(IScope) o.bind(this)}.
	 * {@link #onUnbind(IScoped) o.scope().onUnbind()} are already called, but
	 * {@link IScoped#scope()} still return {@code o}'s original scope.
	 * 
	 * @param o
	 *            the {@link IScoped} to be bind to this {@link IScope}.
	 * @throws DuplicateElementInScopeException
	 *             if there is an {@link IScoped} with same id already present
	 *             and it's not {@code o} (i.e. {@code existing != o})
	 * @throws ScopeException
	 *             if this {@link IScope} deny such action. If thrown, it should
	 *             be guaranteed that no observable state change happens to this
	 *             {@link IScope} unless the {@link IScope} thinks the given
	 *             {@link IScoped} is malicious and require human moderation.
	 */
	default void onBind(IScoped<T_THIS> o) {
		ScopeManager.get(this).onBind(o);
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
	default void onUnbind(IScoped<T_THIS> o) {
		ScopeManager.get(this).onUnbind(o);
	}
}
