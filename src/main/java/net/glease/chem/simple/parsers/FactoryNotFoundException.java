package net.glease.chem.simple.parsers;

public class FactoryNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FactoryNotFoundException() {
		super();
	}

	public FactoryNotFoundException(String message) {
		super(message);
	}

	public FactoryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public FactoryNotFoundException(Throwable cause) {
		super(cause);
	}

}
