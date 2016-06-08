package net.glease.chem.simple.parsers;

import net.glease.chem.simple.datastructure.ChemDatabase;

/**
 * Signals a {@link ChemDatabase} parsing went wrong, somehow.
 * 
 * @author glease
 * @since 0.1
 */
public class CDBParseException extends Exception {

	private static final long serialVersionUID = 1L;

	public CDBParseException() {
		super();
	}

	public CDBParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public CDBParseException(String message) {
		super(message);
	}

	public CDBParseException(Throwable cause) {
		super(cause);
	}

}
