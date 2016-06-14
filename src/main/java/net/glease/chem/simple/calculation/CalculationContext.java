package net.glease.chem.simple.calculation;

import java.math.RoundingMode;

import net.glease.chem.simple.calculation.VariableTable.ImmutableVariableTable;

/**
 * An immutable CalculationContext that specific behavior of
 * @author glease
 * @since 0.1
 */
public interface CalculationContext {
	/**
	 * Get the rounding mode.
	 *
	 * @return the rounding mode
	 */
	RoundingMode getRoundingMode();

	/**
	 * Copy this and set the copied one's rounding mode to given mode.
	 *
	 * @param mode
	 *            the rounding mode
	 * @return a new {@link CalculationContext} differs with this only in
	 *         rounding mode.
	 */
	CalculationContext setRoundingMode(RoundingMode mode);

	/**
	 * Get the precision.
	 *
	 * @return the precision
	 */
	double getPrecision();

	/**
	 * Copy this and set the copied one's precision to given precision.
	 *
	 * @param mode
	 *            the precision
	 * @return a new {@link CalculationContext} differs with this only in
	 *         precision.
	 */
	CalculationContext setPrecision(double precision);

	/**
	 * Get the constant table.
	 *
	 * @return the constant table
	 */
	ImmutableVariableTable getConstants();

	/**
	 * Copy this and set the copied one's precision to given constant table.
	 *
	 * @param mode
	 *            the constant table
	 * @return a new {@link CalculationContext} differs with this only in
	 *         constant table.
	 */
	default CalculationContext setConstants(final VariableTable consts) {
		return setConstants(consts.immutable());
	}

	/**
	 * Copy this and set the copied one's precision to given constant table.
	 *
	 * @param mode
	 *            the constant table
	 * @return a new {@link CalculationContext} differs with this only in
	 *         constant table.
	 */
	CalculationContext setConstants(ImmutableVariableTable consts);

	/**
	 * Get the {@link CalculationProvider} that creates this
	 * {@link CalculationContext}.
	 *
	 * @return the {@link CalculationProvider}
	 */
	CalculationProvider provider();

}
