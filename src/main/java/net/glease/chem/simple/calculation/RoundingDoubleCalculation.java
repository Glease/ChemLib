package net.glease.chem.simple.calculation;

/**
 * Represents a calculation.
 * @author glease
 * @since 0.1
 */
public interface RoundingDoubleCalculation extends DoubleCalculation {

	/**
	 * Calculate the given result.
	 * @param vars
	 * @param ctx
	 * @return
	 */
	double calculate(VariableTable vars, CalculationContext ctx);
}
