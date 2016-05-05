package net.glease.chem.simple.datastructure.impl;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public final class NormalizationException extends Exception {

	private static final long serialVersionUID = 1L;

	NormalizationException(String message, Throwable cause) {
		super(message, cause);
	}

	NormalizationException(String message) {
		super(message);
	}

	NormalizationException(Throwable cause) {
		super(cause);
	}

}