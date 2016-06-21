package net.glease.chem.simple.calculation;

/**
 * TODO javadoc
 * @author glease
 *
 */
public interface RestrictingVariableTable extends VariableTable {
	/**
	 *
	 * @throws IllegalArgumentException if that variable is restricted and the given value
	 * violates that restriction.
	 */
	@Override
	void set(String name, double value);
	DoubleRange getRange(String name);
}
