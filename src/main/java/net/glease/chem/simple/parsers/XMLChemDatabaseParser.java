package net.glease.chem.simple.parsers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.InputSource;

import net.glease.chem.simple.datastructure.ChemDatabase;

/**
 * Marshal or unmarshal a {@link ChemDatabase} instance. This class should be
 * safe to be used concurrently. However, when marshaling, it's the client's
 * responsibility to synchronize the {@link ChemDatabase} being marshaled.
 * 
 * @author glease
 * @since 0.1
 */
public interface XMLChemDatabaseParser {

	default String marshal(ChemDatabase db) throws CDBParseException {
		StringWriter sw = new StringWriter(100);
		marshal(db, sw);
		return sw.toString();
	}

	default void marshal(ChemDatabase db, File out) throws CDBParseException, IOException {
		marshal(db, new FileWriter(out));
	}

	default void marshal(ChemDatabase db, OutputStream out) throws CDBParseException {
		try {
			marshal(db, XMLOutputFactory.newFactory().createXMLStreamWriter(out));
		} catch (XMLStreamException | FactoryConfigurationError e) {
			throw new CDBParseException("can't create XMLStreamWriter with given output", e);
		}
	}

	default void marshal(ChemDatabase db, Writer out) throws CDBParseException {
		try {
			marshal(db, XMLOutputFactory.newFactory().createXMLStreamWriter(out));
		} catch (XMLStreamException | FactoryConfigurationError e) {
			throw new CDBParseException("can't create XMLStreamWriter with given output", e);
		}
	}

	default void marshal(ChemDatabase db, Result out) throws CDBParseException {
		Transformer t;
		try {
			t = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
			throw new CDBParseException("can't create javax.transform.Transformer!", e);
		}
		PipedReader pr = new PipedReader();
		PipedWriter pw;
		try {
			pw = new PipedWriter(pr);
		} catch (IOException e) {
			throw new CDBParseException("can't create java.io.PipedWriter! this is impossible", e);
		}
		marshal(db, pw);
		try {
			t.transform(new StreamSource(pr), out);
		} catch (TransformerException e) {
			throw new CDBParseException(e);
		}
	}

	void marshal(ChemDatabase db, XMLStreamWriter out) throws CDBParseException;

	default ChemDatabase unmarshal(File in) throws CDBParseException, IOException {
		return unmarshal(new FileReader(in));
	}

	ChemDatabase unmarshal(InputSource in) throws CDBParseException, IOException;

	default ChemDatabase unmarshal(InputStream in) throws CDBParseException, IOException {
		return unmarshal(new InputSource(in));
	}

	default ChemDatabase unmarshal(Source in) throws CDBParseException {
		Transformer t;
		try {
			t = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
			throw new CDBParseException("can't create javax.transform.Transformer!", e);
		}
		PipedReader pr = new PipedReader();
		PipedWriter pw;
		try {
			pw = new PipedWriter(pr);
		} catch (IOException e) {
			throw new CDBParseException("can't create java.io.PipedWriter! this is impossible", e);
		}
		StreamResult r = new StreamResult(pw);
		try {
			t.transform(in, r);
		} catch (TransformerException e) {
			throw new CDBParseException("can't transform given input into a stream of character", e);
		}
		try {
			return unmarshal(pr);
		} catch (IOException e) {
			throw new RuntimeException("impossible", e);
		}
	}

	default ChemDatabase unmarshal(Reader in) throws CDBParseException, IOException {
		return unmarshal(new InputSource(in));
	}

	default ChemDatabase unmarshal(String xml) throws CDBParseException {
		try {
			return unmarshal(new StringReader(xml));
		} catch (IOException e) {
			throw new RuntimeException("impossible", e);
		}
	}

	default ChemDatabase unmarshal(URL in) throws CDBParseException, IOException {
		return unmarshal(new InputSource(in.openStream()));
	}

}