package net.glease.chem.simple.calculation;

import java.util.Collections;
import java.util.Set;

public enum EmptyRange implements ContinuousDoubleRange {
	INSTANCE;
	@Override
	public ContinuousDoubleRange maxes() {
		return this;
	}

	@Override
	public DoubleRange intersect(final DoubleRange other) {
		return this;
	}

	@Override
	public boolean isInRange(final double value, final double ulp) {
		return false;
	}

	@Override
	public double getLowerBound() {
		return Double.NaN;
	}

	@Override
	public double getUpperBound() {
		return Double.NaN;
	}

	@Override
	public boolean isLowerBoundInclusive() {
		return false;
	}

	@Override
	public boolean isUpperBoundInclusive() {
		return false;
	}

	@Override
	public Set<ContinuousDoubleRange> getComponents() {
		return Collections.emptySet();
	}

	@Override
	public Set<ContinuousDoubleRange> getComponentsSnapshot() {
		return Collections.emptySet();
	}

	@Override
	public CalculationProvider provider() {
		return null;
	}
}