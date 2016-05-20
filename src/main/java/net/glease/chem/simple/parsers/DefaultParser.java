package net.glease.chem.simple.parsers;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.validation.ValidatorHandler;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.parsers.CDBParserFactory.DefaultFactory;

/**
 * @author glease
 *
 */
class DefaultParser implements XMLChemDatabaseParser {
	private final DefaultFactory factory;

	public DefaultParser(DefaultFactory factory) {
		this.factory = factory;
	}

	@Override
	public void marshal(ChemDatabase db, XMLStreamWriter out) throws CDBParseException {
		try {
			new CDBMarshaller(db).marshal(out);
		} catch (XMLStreamException e) {
			throw new CDBParseException(e);
		}
	}

	@Override
	public ChemDatabase unmarshal(InputSource in) throws CDBParseException, IOException {
		XMLReader reader;
		try {
			reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		} catch (SAXException | ParserConfigurationException e) {
			throw new CDBParseException("can't create org.sax.XMLReader", e);
		}
		UnmarshallingHanlder uh = new UnmarshallingHanlder();
		ValidatorHandler vh = Holders.SCHEMA.newValidatorHandler();
		vh.setContentHandler(uh);
		vh.setErrorHandler(factory.eh);
		reader.setContentHandler(vh);
		reader.setErrorHandler(factory.eh);
		reader.setEntityResolver(factory.er);
		try {
			reader.parse(in);
		} catch (SAXException e) {
			throw new CDBParseException(e);
		}
		return uh.get();
	}
	
}
