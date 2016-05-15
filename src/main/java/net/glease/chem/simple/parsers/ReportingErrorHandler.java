package net.glease.chem.simple.parsers;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

enum ReportingErrorHandler implements ErrorHandler {
	I;
	@Override
	public void warning(SAXParseException exception) throws SAXException {
		ParseLogger.warn(exception);
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		ParseLogger.error(exception);
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		ParseLogger.fatal(exception);
	}

}
