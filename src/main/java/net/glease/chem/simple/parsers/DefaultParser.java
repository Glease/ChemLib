package net.glease.chem.simple.parsers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.validation.ValidatorHandler;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.impl.ChemDatabaseImpl;
import net.glease.chem.simple.normalizers.NormalizationPlugin;
import net.glease.chem.simple.parsers.CDBParserFactory.DefaultFactory;
import net.glease.chem.simple.scoping.BindingPlugin;

/**
 * @author glease
 *
 */
class DefaultParser implements XMLChemDatabaseParser {
	private final EntityResolver er;
	private final ErrorHandler eh;

	private final Map<String, Boolean> fs;

	private final Map<String, Object> ps;

	private final Map<Class<?>, Set<BindingPlugin<?>>> bindings;
	private final Set<NormalizationPlugin> normalizors;

	public DefaultParser(final DefaultFactory f) {
		er = f.er;
		eh = f.eh;
		fs = new HashMap<>(f.fs);
		ps = new HashMap<>(f.ps);
		bindings = f.plugins.stream()
				.map(ParserPlugin::injectedBindingPlugin)
				.flatMap(Set::stream)
				.collect(Collectors.groupingBy(BindingPlugin::target, Collectors.toSet()));
		normalizors = f.plugins.stream()
				.map(ParserPlugin::injectedNormalizationPlugin)
				.flatMap(Set::stream)
				.collect(Collectors.toSet());
	}

	@Override
	public void marshal(final ChemDatabase db, final XMLStreamWriter out) throws CDBParseException {
		try {
			new CDBMarshaller(db).marshal(out);
		} catch (XMLStreamException e) {
			throw new CDBParseException(e);
		}
	}

	@Override
	public ChemDatabase unmarshal(final InputSource in) throws CDBParseException, IOException {
		XMLReader reader;
		try {
			SAXParserFactory f = SAXParserFactory.newInstance();
			for (Entry<String, Boolean> e : fs.entrySet())
				f.setFeature(e.getKey(), e.getValue());
			SAXParser p = f.newSAXParser();
			for (Entry<String, Object> e : ps.entrySet())
				p.setProperty(e.getKey(), e.getValue());
			reader = p.getXMLReader();
		} catch (SAXException | ParserConfigurationException e) {
			throw new CDBParseException("can't create org.sax.XMLReader", e);
		}
		UnmarshallingHanlder uh = new UnmarshallingHanlder(bindings);
		ValidatorHandler vh = Holders.SCHEMA.newValidatorHandler();
		vh.setContentHandler(uh);
		vh.setErrorHandler(eh);
		reader.setContentHandler(vh);
		reader.setErrorHandler(eh);
		reader.setEntityResolver(er);
		try {
			reader.parse(in);
		} catch (SAXException e) {
			throw new CDBParseException(e);
		}
		ChemDatabaseImpl cdb = uh.get();
		normalizors.forEach(cdb::install);
		return cdb;
	}

}
