package net.glease.chem.simple.calculation;

import java.util.Set;

/**
 * Represent a range. May not be continuous. Immutable ones must implements
 * {@link Immutable the marker interface}.
 *
 * @author glease
 * @since 0.1
 */
public interface DoubleRange {
	static boolean isPositiveInfinityIncluded(final DoubleRange range) {
		return range.isInRange(Double.POSITIVE_INFINITY, 1.0d);
	}

	static boolean isNegativeInfinityIncluded(final DoubleRange range) {
		return range.isInRange(Double.NEGATIVE_INFINITY, 1.0d);
	}

	/**
	 * Get the smallest {@link ContinuousDoubleRange} among the supersets of
	 * this {@link DoubleRange}.
	 *
	 * @return
	 */
	ContinuousDoubleRange maxes();

	/**
	 * Get the intersection of two {@link DoubleRange}
	 *
	 * @param other
	 * @return
	 */
	DoubleRange intersect(DoubleRange other);

	/**
	 * Get a set of {@link ContinuousDoubleRange} (may include
	 * {@link DoublePoint}) that make up this {@link DoubleRange}.
	 * <p>
	 * The set should be backed by this DoubleRange.
	 *
	 * @return
	 */
	Set<ContinuousDoubleRange> getComponents();

	/**
	 * Get a set of {@link ContinuousDoubleRange} (may include
	 * {@link DoublePoint}) that make up this {@link DoubleRange}.
	 * <p>
	 * The set should not be backed by this DoubleRange and its elements should
	 * all implement {@link Immutable}.
	 *
	 * @return
	 */
	Set<ContinuousDoubleRange> getComponentsSnapshot();

	/**
	 * Test if given value is included in this {@link DoubleRange}.
	 *
	 * @param value
	 *            the value to test
	 * @param ulp
	 *            the minimum delta client can tolerate
	 * @return
	 */
	boolean isInRange(double value, double delta);

	/**
	 * Get the {@link CalculationProvider} that creates this
	 * {@link DoubleRange}.
	 *
	 * @return the {@link CalculationProvider}, or <code>null</code> iff it's a
	 *         default implementation
	 */
	CalculationProvider provider();
}
