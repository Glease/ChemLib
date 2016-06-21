package net.glease.chem.simple.calculation;

import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.StreamSupport;

import net.glease.chem.simple.parsers.FactoryNotFoundException;

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
 * <h2>Basic stuff</h2>
 * <p>
 * Some stuff must be supported by all {@link CalculationProvider}s.
 * <p>
 * <table border = "1">
 * <tr>
 * <td>Type</td>
 * <td>Must</td>
 * <td>Recommended</td>
 * </tr>
 * <tr>
 * <td>Constants</td>
 * <td>{@link Math#PI PI}, {@link Math#E E}, {@link Double#NaN NaN}</td>
 * <td>/</td>
 * </tr>
 * <tr>
 * <td>Functions</td>
 * <td>every double function<br/>
 * in {@link StrictMath}</td>
 * <td>sum, multiplicative,<br/>
 * definite integral</td>
 * </tr>
 * <tr>
 * <td>Ranges</td>
 * <td>{@link ContinuousDoubleRange}</td>
 * <td>a union of multiple<br/>
 * {@link ContinuousDoubleRange}</td>
 * </tr>
 * </table>
 * <h2>Concurrency</h2>
 * <p>
 * Methods in this class should support concurrent method invocation.
 *
 * @author glease
 * @since 0.1
 */
public interface CalculationProvider {
	/**
	 * Compile a well-formed function literal (see <a href="#syntax">Syntax</a>
	 * chapter for details) into a {@link DoubleCalculation} without
	 * restriction. {@link CalculationProvider} are required not to retain a
	 * strong reference to the created {@link DoubleCalculation}.
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

	/**
	 * Create a new provider. Not suggested if you need some commonly
	 * implemented non-standard functionalities.
	 *
	 * @return a newly-created {@link CalculationProvider}
	 * @throws FactoryNotFoundException
	 *             if no service provider is found or something wrong happened
	 *             during loading.
	 */
	static CalculationProvider newInstance() {
		try {
			return StreamSupport.stream(ServiceLoader.load(CalculationProvider.class).spliterator(), false)
					.filter(p -> p != null).findFirst().get();
		} catch (Exception e) {
			throw new FactoryNotFoundException(e);
		}
	}

	/**
	 * Create a new provider. Not suggested if you need some commonly
	 * implemented non-standard functionalities.
	 *
	 * @param cl
	 *            the {@link ClassLoader} used to locate services
	 * @return a newly-created {@link CalculationProvider}
	 * @throws FactoryNotFoundException
	 *             if no service provider is found or something wrong happened
	 *             during loading.
	 */
	static CalculationProvider newInstance(final ClassLoader cl) {
		try {
			return StreamSupport.stream(ServiceLoader.load(CalculationProvider.class, cl).spliterator(), false)
					.filter(p -> p != null).findFirst().get();
		} catch (Exception e) {
			throw new FactoryNotFoundException(e);
		}
	}

	/**
	 * Create a new provider.
	 *
	 * @param name
	 *            the fully-qualified name of your service.
	 * @param cl
	 *            the {@link ClassLoader} used to locate service
	 * @return a newly-created {@link CalculationProvider}
	 * @throws FactoryNotFoundException
	 *             if no service provider is found or something wrong happened
	 *             during loading.
	 */
	static CalculationProvider newInstance(final String name, final ClassLoader cl) {
		try {
			return (CalculationProvider) Class.forName(name, true, cl).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new FactoryNotFoundException(e);
		}
	}
}
