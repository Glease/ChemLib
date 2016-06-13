package net.glease.chem.simple.parsers;

import static javax.xml.bind.DatatypeConverter.printDouble;
import static javax.xml.bind.DatatypeConverter.printInt;
import static net.glease.chem.simple.parsers.ParseLogger.debug;
import static net.glease.chem.simple.parsers.ParseLogger.trace;
import static net.glease.chem.simple.parsers.ParseLogger.warn;

import java.util.Comparator;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import net.glease.chem.simple.datastructure.Atom;
import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.Dissolve;
import net.glease.chem.simple.datastructure.Reactant;
import net.glease.chem.simple.datastructure.Reaction;
import net.glease.chem.simple.datastructure.Reagent;
import net.glease.chem.simple.datastructure.ReagentState;
import net.glease.chem.simple.datastructure.Resultant;
import net.glease.chem.simple.datastructure.Substance;
import net.glease.chem.simple.datastructure.SubstanceContent;
import net.glease.chem.simple.util.Adaptors;

class CDBMarshaller {
	ChemDatabase i;
	XMLStreamWriter out;
	String writing;

	public CDBMarshaller(final ChemDatabase db) {
		i = db;
	}

	public void marshal(final XMLStreamWriter out) throws XMLStreamException {
		this.out = out;

		debug("Start marshaling");

		out.writeStartDocument();
		out.setPrefix("cdbs", CDBParserFactory.XML_NAMESPACE);
		writeStartElement("database");

		writing = "database";

		writeAttribute("uuid", i.getUUID().toString());
		writeAttribute("version", i.getVersion());
		writeAttribute("info", i.getInfo());
		writeAttribute("locale", Adaptors.writeLocale(i.getLocale()));

		trace("Start writing atoms");
		writeStartElement("atoms");
		writing = "atom";
		i.getAtoms().values().stream()
		.sorted(Comparator.comparingInt(Atom::getIndex).thenComparingInt(Atom::getMolMass))
		.forEachOrdered(a -> {
			try {
				writeStartElement("atom");
				writeAttribute("id", a.getId());
				writeAttribute("index", a.getIndex());
				writeAttribute("molMass", a.getMolMass());
				writeAttribute("symbol", a.getSymbol());
				writeAttribute("localizedName", a.getLocalizedName());
				writeEndElement();
			} catch (XMLStreamException e) {
				throw new RuntimeException(e);
			}
		});
		writeEndElement();
		trace("Ended writing atoms");

		trace("Start writing substances");
		writeStartElement("substances");
		for (Substance s : i.getSubstances().values()) {
			writing = "substance";
			writeStartElement("atom");
			writeAttribute("id", s.getId());
			writeAttribute("boilPoint", s.getBoilPoint());
			writeAttribute("meltPoint", s.getMeltPoint());
			writeAttribute("name", s.getName());

			writing = "substance-atom";
			for (SubstanceContent c : s.getContent()) {
				writeStartElement("atom");
				writeAttribute("atom", c.getAtom().getId());
				writeAttribute("mol", c.getMol());
				writeEndElement();
			}

			writing = "substance-dissolve";
			for (Dissolve d : s.getDissolve()) {
				writeStartElement("dissolve");
				writeAttribute("solvent", d.getSolvent().getId());
				@SuppressWarnings("deprecation")
				String s2tFunction = d.getS2TFunction();
				writeAttribute("s2TFunction", s2tFunction);
				writeEndElement();
			}

			writeEndElement();
		}
		writeEndElement();
		trace("Ended writing substances");

		trace("Start writing reagents");
		writeStartElement("reagents");
		for (Reagent r : i.getReagents().values()) {
			writing = "reagent";
			writeStartElement("reagent");
			writeAttribute("id", r.getId());
			writeAttribute("concentration", r.getConcentration());
			writeAttribute("color", Adaptors.writeColor(r.getColor()));
			writeAttribute("name", r.getName());
			ReagentState state = r.getState();
			if (state != null) {
				writeAttribute("state", state.value());
			}
			Substance solvent = r.getSolvent();
			if (solvent != null) {
				writeAttribute("solvent", solvent.getId());
			}
			writeAttribute("substance", r.getSubstance().getId());
			writeEndElement();
		}
		writeEndElement();
		trace("Ended writing reagents");

		trace("Start writing reactions");
		writeStartElement("reactions");
		for (Reaction r : i.getReactions()) {
			writing = "reaction";
			writeStartElement("reaction");
			writeAttribute("heat", r.getHeat());
			writeAttribute("K", r.getK());
			writeAttribute("pressure", r.getPressure());
			writeAttribute("speed", r.getSpeed());
			writeAttribute("temp", r.getTemp());
			String id = r.getId();
			if (!id.startsWith("__r_")) {
				writeAttribute("id", id);
			}
			Reagent solvent = r.getSolvent();
			if (solvent != null) {
				writeAttribute("", solvent.getId());
			}

			writing = "reaction-catalyst";
			for (Reagent c : r.getCatalysts()) {
				writeStartElement("catalyst");
				writeAttribute("reagent", c.getId());
				writeEndElement();
			}

			writing = "reaction-condition";
			for (String c : r.getConditions()) {
				writeStartElement("condition");
				writeAttribute("value", c);
				writeEndElement();
			}

			writing = "reaction-reactant";
			for (Reactant c : r.getReactants()) {
				writeStartElement("reactant");
				writeAttribute("substance", c.getSubstance().getId());
				writeAttribute("mol", c.getMol());
				writeAttribute("purity", c.getPurity());
				ReagentState state = c.getState();
				if (state != null) {
					writeAttribute("state", state.value());
				}
				writeEndElement();
			}

			writing = "reaction-resultant";
			for (Resultant c : r.getResultants()) {
				writeStartElement("resultant");
				writeAttribute("substance", c.getSubstance().getId());
				writeAttribute("mol", c.getMol());
				ReagentState state = c.getState();
				if (state != null) {
					writeAttribute("state", state.value());
				}
				writeEndElement();
			}

			writeEndElement();
		}
		writeEndElement();
		trace("Ended writing reactions");

		writeEndElement();
		out.writeEndDocument();
		debug("Ended marshaling.");
	}

	private void writeAttribute(final String name, final double value) throws XMLStreamException {
		if (!Double.isNaN(value)) {
			writeAttribute(name, printDouble(value));
		} else {
			warn("NaN found writing " + writing + ": " + name + ". Try normalize first.");
		}
	}

	private void writeAttribute(final String name, final int value) throws XMLStreamException {
		if (value != -1) {
			writeAttribute(name, printInt(value));
		} else {
			warn("negative found writing " + writing + ": " + name + ". Try normalize first.");
		}
	}

	private void writeAttribute(final String name, final String value) throws XMLStreamException {
		if (value != null) {
			// out.writeAttribute(DEFAULT_NAMESPACE_PREFIX,
			// CDB_SIMPLE_NAMESPACE, name, value);
			out.writeAttribute(name, value);
		} else {
			warn("null found writing " + writing + ": " + name + ". Try normalize first.");
		}
	}

	private void writeEndElement() throws XMLStreamException {
		out.writeEndElement();
	}

	private void writeStartElement(final String name) throws XMLStreamException {
		out.writeStartElement(CDBParserFactory.XML_DEFAULT_NAMESPACE_PREFIX, name, CDBParserFactory.XML_NAMESPACE);
	}
}
