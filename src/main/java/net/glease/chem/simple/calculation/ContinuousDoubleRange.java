package net.glease.chem.simple.calculation;

import java.util.Collections;
import java.util.Set;
/**
 * TODO javadoc
 * @author glease
 *
 */
public interface ContinuousDoubleRange extends DoubleRange {
	@Override
	default ContinuousDoubleRange maxes() {
		return this;
	}

	/**
	 * Get the lower bound of this {@link ContinuousDoubleRange}.
	 *
	 * @return the lower bound.
	 */
	double getLowerBound();

	/**
	 * Get the upper bound of this {@link ContinuousDoubleRange}.
	 *
	 * @return the upper bound.
	 */
	double getUpperBound();

	/**
	 * Test if the lower bound of this {@link ContinuousDoubleRange} is
	 * inclusive.
	 *
	 * @return <code>true</code> if the lower bound of this
	 *         {@link ContinuousDoubleRange} is inclusive, <code>false</code>
	 *         otherwise.
	 */
	boolean isLowerBoundInclusive();

	/**
	 * Test if the upper bound of this {@link ContinuousDoubleRange} is
	 * inclusive.
	 *
	 * @return <code>true</code> if the upper bound of this
	 *         {@link ContinuousDoubleRange} is inclusive, <code>false</code>
	 *         otherwise.
	 */
	boolean isUpperBoundInclusive();

	@Override
	default Set<ContinuousDoubleRange> getComponents() {
		return Collections.singleton(this);
	}
}
