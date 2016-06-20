package net.glease.chem.simple.scoping;

import java.util.Set;
import java.util.function.Consumer;

import net.glease.chem.simple.scoping.IScope.ROOT;

/**
 * Indicates instances should have scopes.
 *
 * <h2>Implementation Notes</h2>
 * <p>
 * Read javadoc of {@link #bind(IScope)} carefully, <b>especially the
 * &#64;throws section.</b> This section specific a lot of details that the
 * {@link IScoped#bind(IScope)} will expect.
 *
 * <h2>Reagarding null Scope</h2>
 * <p>
 * A <code>null</code> scope is permited.A <code>null</code> scope means scope
 * undefined, not something else, like the default scope. Two elements whose
 * scopes are both undefined shouldn't be considered in the same scope, but they
 * could still be considered equal (i.e. {@code a.equals(b)} if the
 * implementation like the idea.
 *
 * <h2>Reference Strength</h2>
 * <p>
 * An {@link IScoped} should ensure its scope's reference strength is strongly
 * reachable unless that {@link IScope} becomes phantom reachable or unreachable
 * itself. It should also clear the scope reference as soon as itself becomes
 * phantom reachable or unreachable itself.
 *
 * <h2>Typical Usage</h2>
 *
 * <pre>
 * public class Common implements IScope&lt;IScope.ROOT, Common> {
 * }
 *
 * public class Element implements IScoped&lt;Common> {
 * 	protected String id;
 * 	// other fields
 *
 * 	&#64;Override
 * 	public String getId() {
 * 		return id;
 * 	}
 *
 * 	public void setId(String id) {
 * 		this.id = Objects.requireNonNull(id, "ID can't be null");
 * 	}
 * 	// other methods
 * }
 *
 * public class ElementWrapper implements IScoped&lt;Common> {
 * 	protected Element a;
 * 	// other fields
 *
 * 	&#64;Override
 * 	public String getId() {
 * 		return a.getId();
 * 	}
 * 	// other methods
 * }
 *
 * public class Bundle implements IScoped&lt;Common> {
 * 	protected Value someValue;
 * 	// other fields
 *
 * 	// A recent update added default implementation of bind() and scope(),
 * 	// so you don't need to implement your own.
 *
 * 	// However providing other implementation is always suggested if
 * 	// a great many of IScoped instances are using default implementation
 * 	// as it may result in serious performance issue.
 *
 * 	&#64;Override
 * 	public String getId() {
 * 		return computeId(someValue);
 * 	}
 *
 * 	// other methods
 * }
 * </pre>
 *
 * @author glease
 *
 * @param <T_SCOPE>
 *            the type of scope, can't be {@link ROOT} unless this is also a
 *            {@link IScope}
 * @see IScoped
 * @since 0.1
 */
public interface IScoped<T_SCOPE extends IScope<?, T_SCOPE>> {

	default void install(final ScopedPlugin<T_SCOPE> plugin) {
		ScopedManager.get(this).install(plugin);
	}

	default boolean uninstall(final ScopedPlugin<T_SCOPE> plugin) {
		return ScopedManager.get(this).uninstall(plugin);
	}

	/**
	 * Get a unmmodifiable set view of all installed plugins backed by this
	 * {@link IScoped}, i.e. further installation will change the content of
	 * returned set.
	 *
	 * @return
	 */
	default Set<ScopedPlugin<T_SCOPE>> plugins() {
		return ScopedManager.get(this).plugins();
	}

	/**
	 * Update a key state of given {@link IScoped}, and notify its scope about
	 * its ID changes. Mainly exists to wipe boilerplate.
	 *
	 * @param updated
	 * @param updator
	 */
	static <T extends IScoped<T_SCOPE>, T_SCOPE extends IScope<?, T_SCOPE>> void updateIdBy(final T updated,
			final Consumer<T> updator) {
		String oldId = updated.getId();
		updator.accept(updated);
		T_SCOPE scope = updated.scope();
		if (scope != null)
			scope.updateId(oldId, updated);
	}

	/**
	 * Bind this element to a given scope.
	 * <h2>Order of Invocation</h2>
	 * <p>
	 * Unless original {@code scope} is <code>null</code>, implementations
	 * should call {@link IScope#onUnbind(IScoped) scope().onUnbind(this)} then
	 * {@link IScope#onBind(IScoped) scope.onBind(this)} immediately after
	 * checking that this {@link IScoped} accept to be binded to the given
	 * {@link IScope} (via {@link #isScopeAccepted(IScope)
	 * isScopeAccepted(scope)}). Anything that may change states of given scope
	 * or this {@link IScoped} must happen after the call to
	 * {@code newScope.onBind(this)}
	 * <p>
	 * If {@code scope} is <code>null</code>, implementors are free to decide
	 * whether they accept it.
	 * <p>
	 * A special case: binding a {@link IScoped} to its current scope again
	 * should be valid and should cause no visible effects. Implementation
	 * should return false as soon as they found it the same. (i.e.
	 * {@code newScope == scope()}
	 * <p>
	 * {@link #bind(IScope)} only ensure there will be an {@link IScoped} equals
	 * to this method, but don't guarantee this object to be in given scope.
	 *
	 * @param newScope
	 *            the scope to be binded to. may be <code>null</code>
	 * @return <code>true</code> if binding successful, <code>false</code> if
	 *         this {@link IScoped} is already in the given scope.
	 * @throws ScopeException
	 *             thrown if {@link IScope#onUnbind(IScoped)
	 *             scope().onUnbind(this)} or {@link IScope#onBind(IScoped)
	 *             scope.onbind(this)} throw an {@link ScopeException}, or if
	 *             this instance itself deny this action. If thrown, it should
	 *             be guaranteed that no observable state change happens to this
	 *             {@link IScoped} unless either {@link IScope} involved thinks
	 *             that this {@link IScoped} is malicious and requires human
	 *             moderation. It may also resulted from a failure in the test
	 *             of {@link #isScopeAccepted(IScope)} or a <code>false</code>
	 *             returned from that test.
	 */
	default <T extends T_SCOPE> boolean bind(final T newScope) {
		/*
		 * default implementation leave everything to ScopedManager. No
		 * short-circuiting (i.e. newScope == scope() check) is used because a
		 * ScopedManager.get(this) is unavoidable.
		 *
		 */
		return ScopedManager.get(this).bind(newScope);
	}

	/**
	 * Some how get an ID for this element. It could be explicitly set ID, or an
	 * generated value based on various internal data. Non-null. Empty
	 * {@link String} permitted for the sake of simplicity, but that's really
	 * not recommended.
	 *
	 * @return an non-null id.
	 */
	String getId();

	/**
	 * Test whether this {@link IScoped} accept to be binded to the given
	 * {@link IScope T_SCOPE}. The test result should only be based on this
	 * {@link IScoped}'s opinion, i.e. invoking
	 * {@code SomeExtendedIScope.isElementAccepted()} to test if given scope
	 * accept this {@link IScoped} to become its element is <i>not allowed</i>.
	 * <p>
	 * Default implementation always accept the new scope.
	 *
	 * @param newScope
	 *            scope others want this {@link IScoped} to be binded to.
	 * @return
	 * @throws ScopeException
	 *             if some testing failed due to an unexpected exception or this
	 *             {@link IScoped} thinks given T_SCOPE is malicious and
	 *             requires human moderation.
	 */
	default boolean isScopeAccepted(final T_SCOPE newScope) {
		return true;
	}

	/**
	 * Get the scope this element binds to. May be <code>null</code>. a
	 * <code>null</code> scope means scope undefined, not something else, like
	 * the default scope. Two elements whose scopes are both undefined shouldn't
	 * be considered in the same scope.
	 *
	 * @return the scope.
	 */
	default T_SCOPE scope() {
		return ScopedManager.get(this).scope();
	}
}