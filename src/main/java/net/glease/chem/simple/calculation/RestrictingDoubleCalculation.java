package net.glease.chem.simple.calculation;

/**
 * A {@link DoubleCalculation} that have its variable restricted to a
 * certain {@link DoubleRange}.
 *
 * @author glease
 *
 */
public interface RestrictingDoubleCalculation extends DoubleCalculation {
	/**
	 * Get the allowed range of given variable.
	 * @param name
	 * @return
	 */
	DoubleRange getVariableRange(String name);
}
