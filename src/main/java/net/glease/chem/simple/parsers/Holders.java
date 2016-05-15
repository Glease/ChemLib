package net.glease.chem.simple.parsers;

import static net.glease.chem.simple.parsers.ParseLogger.*;

import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

class Holders {
	private static final String SCHEMA_LOCATION = "CDB-Simple.xsd";
	public static final boolean REQUIRE_SCHEMA = true;
	public static final Schema SCHEMA;
	static {
		ParseLogger.info(SETUP, "Setting up DeefaultParser globals...");
		SCHEMA = getSchema();
		
		if(SCHEMA == null &&REQUIRE_SCHEMA)
			throw new RuntimeException("Schema can't be initialized and the implementation require a schema.");
		
		ParseLogger.info(SETUP, "DeefaultParser globals setup finished.");
	}

	private static Schema getSchema() {
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		sf.setErrorHandler(ReportingErrorHandler.I);
		URL url = UnmarshallingHanlder.class.getClassLoader().getResource(SCHEMA_LOCATION);
		if (url == null) {
			ParseLogger.warn(SETUP, "Schema not found. Please verify if your CDB-Simple distribution is broken.");
			return null;
		}
		try {
			Schema schema = sf.newSchema(url);
			return schema;
		} catch (SAXException e) {
			ParseLogger.warn(SETUP,
					"Schema parse failed. Validation not enabled. Please verify if your CDB-Simple distribution is broken.",
					e);
			return null;
		}
	}
}