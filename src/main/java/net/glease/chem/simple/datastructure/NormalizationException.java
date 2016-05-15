package net.glease.chem.simple.datastructure;

public final class NormalizationException extends Exception {

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