package net.glease.chem.simple.scoping;

import net.glease.chem.simple.scoping.IScope.ROOT;

/**
 * Indicates instances should have scopes. Read javadoc of {@link #bind(IScope)}
 * carefully, especially the &#64;throws section.
 * <p>
 * Typical usage:
 * 
 * <pre>
 * public class Common implements IScope&lt;IScope.ROOT, Common> {
 * }
 * 
 * public class Element implements IScoped&lt;Common> {
 * 	protected Common scope;
 * 	protected String id;
 * 	// other fields
 * 
 * 	&#64;Override
 * 	public void bind(Common scope) {
 * 		this.scope = scope;
 * 	}
 * 
 * 	&#64;Override
 * 	public String getId() {
 * 		return id;
 * 	}
 * 
 * 	&#64;Override
 * 	public Common scope() {
 * 		return scope;
 * 	}
 * 
 * 	public void setId(String id) {
 * 		this.id = Objects.requireNonNull(id, "ID can't be null");
 * 	}
 * 	// other methods
 * }
 * 
 * public class ElementWrapper implements IScoped&lt;Common> {
 * 	protected Common scope;
 * 	protected Element a;
 * 	// other fields
 * 
 * 	&#64;Override
 * 	public void bind(Common scope) {
 * 		this.scope = scope;
 * 	}
 * 
 * 	&#64;Override
 * 	public String getId() {
 * 		return a.getId();
 * 	}
 * 
 * 	&#64;Override
 * 	public Common scope() {
 * 		return scope;
 * 	}
 * 	// other methods
 * }
 * 
 * public class Bundle implements IScoped&lt;Common> {
 * 	protected Common scope;
 * 	protected Value someValue;
 * 	// other fields
 * 
 * 	&#64;Override
 * 	public void bind(Common scope) {
 * 		this.scope = scope;
 * 	}
 * 
 * 	&#64;Override
 * 	public String getId() {
 * 		return computeId(someValue);
 * 	}
 * 
 * 	&#64;Override
 * 	public Common scope() {
 * 		return scope;
 * 	}
 * 	// other methods
 * }
 * </pre>
 * 
 * @author glease
 *
 * @param <T_SCOPE>
 *            the type of scope, can't be {@link ROOT}
 * @see IScoped
 */
public interface IScoped<T_SCOPE extends IScope<?, T_SCOPE>> {
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
	 * Get the scope this element binds to. May be <code>null</code>. a
	 * <code>null</code> scope means scope undefined, not something else, like
	 * the default scope. Two elements whose scopes are both undefined shouldn't
	 * be considered in the same scope.
	 * 
	 * @return the scope. non-null
	 */
	T_SCOPE scope();

	/**
	 * Bind this element to a given scope. May be <code>null</code>. a
	 * <code>null</code> scope means scope undefined, not something else, like
	 * the default scope. Two elements whose scopes are both undefined shouldn't
	 * be considered in the same scope.
	 * <p>
	 * Unless {@code scope} is <code>null</code>, implementations should call
	 * {@link IScope#onUnbind(IScoped) scope().onUnbind(this)} then
	 * {@link IScope#onBind(IScoped) scope.onBind(this)} immediately after
	 * checking that this {@link IScoped} accept to be binded to the given
	 * {@link IScope}. Anything that may change states of given scope or this
	 * {@link IScoped} must happen after that call.
	 * <p>
	 * If {@code scope} is <code>null</code>, implementors are free to decide
	 * whether they accept it
	 * 
	 * @param scope
	 *            the scope to be bind to. may be <code>null</code>
	 * @throws ScopeException
	 *             thrown if {@link IScope#onUnbind(IScoped)
	 *             scope().onUnbind(this)} or {@link IScope#onBind(IScoped)
	 *             scope.onbind(this)} throw an {@link ScopeException}, or if
	 *             this instance itself deny this action. If thrown, it should
	 *             be guaranteed that no observable state change happens to this
	 *             {@link IScoped} unless either {@link IScope} thinks that this
	 *             {@link IScoped} is malicious and requires human moderation.
	 */
	void bind(T_SCOPE scope);

}