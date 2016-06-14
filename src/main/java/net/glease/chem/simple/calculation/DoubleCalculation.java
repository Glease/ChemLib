package net.glease.chem.simple.calculation;

import java.util.Map;
import java.util.Set;

/**
 * The base interface of all double calculation types.
 * @author glease
 * @since 0.1
 */
public interface DoubleCalculation {

	/**
	 * Calculate based on values stored in given variables.
	 * @param variables
	 * @return calculation result.
	 */
	double calculate(VariableTable variables);
	/**
	 * The calculation literal.
	 * @return
	 */
	String literal();

	/**
	 * Get the {@link CalculationProvider} that creates this {@link DoubleCalculation}.
	 * @return the {@link CalculationProvider}
	 */
	CalculationProvider provider();

	/**
	 * Wraps this {@link DoubleCalculation} into a {@link RestrictingDoubleCalculation}.
	 * @param rules rules to restrict
	 * @return a restricted version.
	 */
	default RestrictingDoubleCalculation restricted(final Map<String, DoubleRange> rules) {
		return new RestrictingDoubleCalculation() {

			@Override
			public double calculate(final VariableTable variables) {
				return DoubleCalculation.this.calculate(variables);
			}

			@Override
			public DoubleRange getVariableRange(final String name) {
				return rules.get(name);
			}

			@Override
			public String literal() {
				return DoubleCalculation.this.literal();
			}

			@Override
			public CalculationProvider provider() {
				return DoubleCalculation.this.provider();
			}

			@Override
			public Set<String> variables() {
				return DoubleCalculation.this.variables();
			}
		};
	}

	/**
	 * Get all the variables that this {@link DoubleCalculation} uses.
	 * @return a set of used variables
	 */
	Set<String> variables();
}
