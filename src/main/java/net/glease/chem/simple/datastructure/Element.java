package net.glease.chem.simple.datastructure;

import net.glease.chem.simple.scoping.IScope;
import net.glease.chem.simple.scoping.IScoped;

/**
 * An element with given scope of type <i>T</i>.
 * 
 * @author glease
 * @since 1.0
 * @param <T>
 *            scope type
 */
public interface Element<T extends IScope<?, T>> extends IScoped<T>, ChemDatabaseComponent {
	/**
	 * Two elements are considered <i>value-equal</i> if all their properties
	 * are <i>value-equal</i> and their scope <i>identity-equal</i> and both
	 * non-null:
	 * 
	 * <pre>
	 * 		Element&lt;T> e1 = Elements.createA();
	 * 		Element&lt;T> e2 = Elements.createB();
	 * 		assert e1.equals(e2) == ((e1.scope()==null||
	 * 			e1.scope() == e2.scope() && ... // other properties
	 * </pre>
	 * <p>
	 * {@inheritDoc}
	 * 
	 * @param obj
	 *            the reference object with which to compare.
	 * @return <code>true</code> if this object is the same as the obj argument;
	 *         <code>false</code> otherwise.
	 * @see #hashCode()
	 */
	@Override
	boolean equals(Object obj);

	/**
	 * Two elements are considered <i>value-equal</i> if all their properties
	 * are <i>value-equal</i> and their scope <i>identity-equal</i> and both
	 * non-null.
	 * <p>
	 * {@inheritDoc}
	 * 
	 * @return a hash code value for this object.
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @see java.lang.System#identityHashCode
	 */
	@Override
	int hashCode();
}