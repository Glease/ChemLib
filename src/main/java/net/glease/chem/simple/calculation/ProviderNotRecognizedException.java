package net.glease.chem.simple.calculation;

/**
 * Thrown when anything created by a {@link CalculationProvider} found its
 * method parameter isn't known and has nothing to do with it, even if its
 * javadoc and method declaration didn't mention it.
 * <p>
 * Any methods that takes a type created by a {@link CalculationProvider} as a
 * parameter may throw this exception if their providers' classes isn't equal.
 * It may also just adapt the method parameter into a known one, too. Such
 * decision should be documented to avoid confusion.
 *
 * @author glease
 * @since 0.1
 */
public class ProviderNotRecognizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProviderNotRecognizedException() {
	}

	public ProviderNotRecognizedException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ProviderNotRecognizedException(final String message) {
		super(message);
	}

	public ProviderNotRecognizedException(final Throwable cause) {
		super(cause);
	}

}
