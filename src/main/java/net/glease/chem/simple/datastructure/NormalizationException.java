package net.glease.chem.simple.datastructure;

/**
 * Indicates that there is something wrong during the normalization, like
 * failure of inference, unexpected exceptions, etc..
 * @author glease
 * @since 0.1
 *
 */
public class NormalizationException extends Exception {

	private static final long serialVersionUID = 1L;

	public NormalizationException(String message) {
		super(message);
	}

	public NormalizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public NormalizationException(Throwable cause) {
		super(cause);
	}

}