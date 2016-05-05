package net.glease.chem.simple.parsers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.impl.ChemDatabaseImpl;

/**
 * A simple wrapper around JAXB.
 * 
 * @author glease
 *
 */
public class DefaultParser {
	private final static QName _Database_QNAME = new QName("http://glease.net/chem/simple/DataStructure", "database", "cdbs");

	private JAXBContext ctx;
	private Unmarshaller un;
	private Marshaller ma;

	public DefaultParser() {
	}

	public void init() throws JAXBException {
		ctx = JAXBContext.newInstance(ChemDatabaseImpl.class);
	}

	protected Unmarshaller getUnmarshaller() throws JAXBException {
		if (ctx == null)
			throw new IllegalStateException("not initialized");
		return un = un == null ? ctx.createUnmarshaller() : un;
	}

	protected Marshaller getMarshaller() throws JAXBException {
		if (ctx == null)
			throw new IllegalStateException("not initialized");
		return ma = ma == null ? ctx.createMarshaller() : ma;
	}

	public ChemDatabase unmarshal(byte[] bytes) throws JAXBException {
		return unmarshal(new ByteArrayInputStream(bytes));
	}

	public ChemDatabaseImpl unmarshal(File in) throws JAXBException {
		return (ChemDatabaseImpl) ((JAXBElement<?>) getUnmarshaller().unmarshal(in)).getValue();
	}

	public ChemDatabase unmarshal(InputStream in) throws JAXBException {
		return (ChemDatabase) ((JAXBElement<?>) getUnmarshaller().unmarshal(in)).getValue();
	}

	public ChemDatabase unmarshal(Reader in) throws JAXBException {
		return (ChemDatabase) ((JAXBElement<?>) getUnmarshaller().unmarshal(in)).getValue();
	}

	public ChemDatabase unmarshal(URL in) throws JAXBException {
		return (ChemDatabase) ((JAXBElement<?>) getUnmarshaller().unmarshal(in)).getValue();
	}

	public byte[] marshal(ChemDatabaseImpl db) throws JAXBException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		marshal(db, baos);
		return baos.toByteArray();
	}

	public void marshal(ChemDatabaseImpl db, File out) throws JAXBException {
		getMarshaller().marshal(new JAXBElement<ChemDatabaseImpl>(_Database_QNAME, ChemDatabaseImpl.class, db), out);
	}

	public void marshal(ChemDatabaseImpl db, OutputStream out) throws JAXBException {
		getMarshaller().marshal(new JAXBElement<ChemDatabaseImpl>(_Database_QNAME, ChemDatabaseImpl.class, db), out);
	}

	public void marshal(ChemDatabaseImpl db, Writer out) throws JAXBException {
		getMarshaller().marshal(new JAXBElement<ChemDatabaseImpl>(_Database_QNAME, ChemDatabaseImpl.class, db), out);
	}

}
