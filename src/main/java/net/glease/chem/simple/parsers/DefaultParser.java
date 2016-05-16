package net.glease.chem.simple.parsers;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.validation.ValidatorHandler;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.parsers.CDBParserFactory.DefaultFactory;

/**
 * @author glease
 *
 */
class DefaultParser implements XMLChemDatabaseParser {
	static final String CDB_SIMPLE_NAMESPACE = "http://glease.net/chem/simple/DataStructure";

	static final String DEFAULT_NAMESPACE_PREFIX = "cdbs";
	private final DefaultFactory factory;

	public DefaultParser(DefaultFactory factory) {
		this.factory = factory;
	}

	@Override
	public void marshal(ChemDatabase db, XMLStreamWriter out) throws XMLStreamException {
		new CDBMarshaller(db).marshal(out);
	}

	@Override
	public ChemDatabase unmarshal(InputSource in) throws SAXException, IOException {
		XMLReader reader = XMLReaderFactory.createXMLReader();
		UnmarshallingHanlder uh = new UnmarshallingHanlder();
		ValidatorHandler vh = Holders.SCHEMA.newValidatorHandler();
		vh.setContentHandler(uh);
		vh.setErrorHandler(factory.eh);
		reader.setContentHandler(vh);
		reader.setErrorHandler(factory.eh);
		reader.setEntityResolver(factory.er);
		reader.parse(in);
		return uh.get();
	}

}
