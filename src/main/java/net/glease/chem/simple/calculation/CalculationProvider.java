package net.glease.chem.simple.calculation;

import java.util.Set;

/**
 * The service provider interface to provide creation and conversion of various
 * classes in this package.
 * <h2>Syntax</h2>
 * <p>
 * The syntax is simple: basically it's the same as Java numeric calculation.
 * However, there are some differences:
 * <ul>
 * <li>It has built-in functions. Functions are used like this :
 * {@code pow(2.5,3.7)}.
 * <li>It may has built-in constants. Default constants can be acquired via
 * {@code calculationProvider.defaultContext().getConstants()}.
 * <li>No bit-wise operation, nor boolean operations, at least no native
 * support. It can be achieved with functions, though.
 * </ul>
 * <h2>Concurrency</h2> Methods in this class should support concurrent
 * invocation.
 *
 * @author glease
 * @since 0.1
 */
public interface CalculationProvider {
	/**
	 * Compile a well-formed function literal (see <a href="#syntax">Syntax</a>
	 * chapter for details) into a {@link DoubleCalculation} without restriction.
	 * {@link CalculationProvider} are required not to retain a strong reference
	 * to the created {@link DoubleCalculation}.
	 *
	 * @param literal
	 */
	DoubleCalculation compile(String literal);

	/**
	 * Create a {@link ContinuousDoubleRange} with given attributes.
	 * {@link CalculationProvider} are required not to retain a strong reference
	 * to the created {@link ContinuousDoubleRange}.
	 *
	 * @param up
	 *            upper bound
	 * @param inclusiveUp
	 *            if the upper bound is inclusive
	 * @param low
	 *            lower bound
	 * @param inclusiveLow
	 *            if the lower bound is inclusive
	 * @return a {@link ContinuousDoubleRange} with given attributes.
	 */
	ContinuousDoubleRange createContinuousDoubleRange(double up, boolean inclusiveUp, double low, boolean inclusiveLow);

	/**
	 * Create an brand-new empty mutable {@link VariableTable}.
	 * {@link CalculationProvider} are required not to retain a strong reference
	 * to the created {@link VariableTable}.
	 *
	 * @return an empty mutable variable table
	 */
	VariableTable createVarTable();

	/**
	 * Get the default {@link CalculationContext} used by this
	 * {@link CalculationProvider}. May change over time.
	 *
	 * @return the default {@link CalculationContext}
	 */
	CalculationContext defaultContext();

	/**
	 * Get a list of functions available during {@link #compile(String) compile
	 * process}. Every double function available in {@link java.lang.Math}
	 * should all be supported.
	 *
	 *
	 * @return
	 */
	Set<FunctionDescriptor> getAvaliableFunctions();

	/**
	 * Convert a possibly mutable object to an immutable one. All interfaces in
	 * this package must be supported.
	 *
	 * @param mutable
	 *            a possibly mutable object
	 * @return the immutable object
	 */
	<T> Immutable<T> immutable(T mutable);

	/**
	 * Create a {@link DoubleRange}, somehow. {@link CalculationProvider} are
	 * required not to retain a strong reference to the created
	 * {@link DoubleRange}.
	 *
	 * @param args1
	 *            args1
	 * @param args2
	 *            args2
	 * @param args3
	 *            args3
	 * @return a {@link DoubleRange}
	 */
	DoubleRange make(double[] args1, boolean[] args2, Object[] args3);

	/**
	 * Convert an immutable object back to a mutable one. All interfaces in this
	 * package except {@link CalculationContext} must all be supported.
	 *
	 * @param immutable
	 *            an immutable object
	 * @return the immutable object
	 */
	<T> T mutable(Immutable<T> immutable);
}
