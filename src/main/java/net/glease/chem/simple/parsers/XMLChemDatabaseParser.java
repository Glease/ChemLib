package net.glease.chem.simple.parsers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.glease.chem.simple.datastructure.ChemDatabase;

/**
 * Marshal or unmarshal a {@link ChemDatabase} instance. This class should be
 * safe to be used concurrently. However, when marshaling, it's the client's
 * responsibility to synchronize the {@link ChemDatabase} being marshaled.
 * 
 * @author glease
 *
 */
public interface XMLChemDatabaseParser {

	default ChemDatabase unmarshal(String xml) throws SAXException {
		try {
			return unmarshal(new StringReader(xml));
		} catch (IOException e) {
			throw new RuntimeException("impossible", e);
		}
	}

	default ChemDatabase unmarshal(File in) throws SAXException, IOException {
		return unmarshal(new FileReader(in));
	}

	default ChemDatabase unmarshal(InputStream in) throws SAXException, IOException {
		return unmarshal(new InputSource(in));
	}

	default ChemDatabase unmarshal(Reader in) throws SAXException, IOException {
		return unmarshal(new InputSource(in));
	}

	default ChemDatabase unmarshal(URL in) throws SAXException, IOException {
		return unmarshal(new InputSource(in.openStream()));
	}

	ChemDatabase unmarshal(InputSource in) throws SAXException, IOException;

	default String marshal(ChemDatabase db) throws XMLStreamException {
		StringWriter sw = new StringWriter(100);
		marshal(db, sw);
		return sw.toString();
	}

	default void marshal(ChemDatabase db, File out) throws XMLStreamException, IOException {
		marshal(db, new FileWriter(out));
	}

	default void marshal(ChemDatabase db, OutputStream out) throws XMLStreamException {
		marshal(db, XMLOutputFactory.newFactory().createXMLStreamWriter(out));
	}

	default void marshal(ChemDatabase db, Writer out) throws XMLStreamException {
		marshal(db, XMLOutputFactory.newFactory().createXMLStreamWriter(out));
	}

	void marshal(ChemDatabase db, XMLStreamWriter out) throws XMLStreamException;

}