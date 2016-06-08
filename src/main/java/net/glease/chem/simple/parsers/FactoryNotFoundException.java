package net.glease.chem.simple.parsers;

/**
 * Signals a requested {@link CDBParserFactory} can't be found.
 * 
 * @author glease
 * @since 0.1
 */
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
