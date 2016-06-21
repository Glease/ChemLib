package net.glease.chem.simple.calculation;

import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A table of variable. The interface is simple and could be extended to support
 * more. Thread safety isn't required. Immutable versions must also implements
 * {@link ImmutableVariableTable} interface.
 *
 * @author glease
 * @since 0.1
 */
public interface VariableTable {
	/**
	 * A immutable version of another variable table.
	 *
	 * @author glease
	 * @since 0.1
	 *
	 */
	interface ImmutableVariableTable extends Immutable<VariableTable>, VariableTable {

		/**
		 * Create a mutable copy of this {@link ImmutableVariableTable}.
		 * <p>
		 * The default implementation calls
		 * {@link CalculationProvider#mutable(Object)} to obtain the mutable
		 * copy.
		 *
		 * @return a mutable copy of this {@link VariableTable}
		 */
		default VariableTable mutable() {
			return provider().mutable(this);
		}
	}

	/**
	 * Get the value in this VariableTable.
	 *
	 * @param name
	 * @return the value of given variable, {@link Double#NaN} is allowed.
	 * @throws NoSuchElementException
	 *             if no such variable is found.
	 */
	double get(String name);

	/**
	 * Set a variable.
	 *
	 * @param name
	 *            the name of variable
	 * @param value
	 *            the value of variable
	 * @throws UnsupportedOperationException
	 *             if modifying is not allowed/supported.
	 */
	void set(String name, double value);

	/**
	 * Test if a variable exists.
	 *
	 * @param name
	 * @return true if such variable exists, false otherwise.
	 *
	 */
	boolean hasVariable(String name);

	/**
	 * Get a set of all variable name in this {@link VariableTable}.
	 *
	 * @return
	 */
	Set<String> variables();

	/**
	 * Get the {@link CalculationProvider} that creates this
	 * {@link VariableTable}.
	 *
	 * @return the {@link CalculationProvider}
	 */
	CalculationProvider provider();

	/**
	 * Create a immutable copy of this {@link VariableTable}.
	 * <p>
	 * The default implementation calls
	 * {@link CalculationProvider#immutable(Object)} to obtain the immutable
	 * copy or just return {@code this} if
	 * {@code this instanceof ImmutableVariableTable}. Providers should not rely
	 * on this method to provide a immutable {@link ImmutableVariableTable} .
	 *
	 * @return a immutable copy of this {@link VariableTable}
	 */
	default ImmutableVariableTable immutable() {
		return (ImmutableVariableTable) (this instanceof ImmutableVariableTable ? this : provider().immutable(this));
	}
}
