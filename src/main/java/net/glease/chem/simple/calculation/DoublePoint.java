package net.glease.chem.simple.calculation;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * Any {@link DoubleRange} that represents a {@link DoublePoint} should be
 * converted into a {@link DoublePoint}
 *
 * @author glease
 *
 */
public interface DoublePoint extends ContinuousDoubleRange {
	final class ImmutableDoublePoint implements DoublePoint, Serializable, Immutable<DoublePoint> {
		private static final long serialVersionUID = 1L;
		private double d;

		private ImmutableDoublePoint(final double d) {
			super();
			this.d = d;
		}

		@Override
		public Set<ContinuousDoubleRange> getComponentsSnapshot() {
			return getComponents();
		}

		@Override
		public double getLowerBound() {
			return d;
		}

		public void set(final double d) {
			this.d = d;
		}

		@Override
		public CalculationProvider provider() {
			return null;
		}
	}

	final class MutableDoublePoint implements DoublePoint, Serializable {
		private static final long serialVersionUID = 1L;
		private double d;

		private MutableDoublePoint(final double d) {
			super();
			this.d = d;
		}

		@Override
		public Set<ContinuousDoubleRange> getComponentsSnapshot() {
			return Collections.singleton(ofImmutable(d));
		}

		@Override
		public double getLowerBound() {
			return d;
		}

		public void set(final double d) {
			this.d = d;
		}

		@Override
		public CalculationProvider provider() {
			return null;
		}
	}

	public static DoublePoint ofImmutable(final double d) {
		return new ImmutableDoublePoint(d);
	}

	public static MutableDoublePoint ofMutable(final double d) {
		return new MutableDoublePoint(d);
	}

	@Override
	default double getUpperBound() {
		return getLowerBound();
	}

	@Override
	default DoubleRange intersect(final DoubleRange other) {
		return other.isInRange(getLowerBound(), Math.ulp(getLowerBound())) ? this : EmptyRange.INSTANCE;
	}

	@Override
	default boolean isInRange(final double value, final double delta) {
		return Math.abs(getLowerBound() - value) < delta;
	}

	@Override
	default boolean isLowerBoundInclusive() {
		return true;
	}

	@Override
	default boolean isUpperBoundInclusive() {
		return false;
	}
}