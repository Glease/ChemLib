package net.glease.chem.simple.parsers;

import static javax.xml.bind.DatatypeConverter.*;
import static net.glease.chem.simple.parsers.DefaultParser.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import net.glease.chem.simple.datastructure.Atom;
import net.glease.chem.simple.datastructure.CrystalType;
import net.glease.chem.simple.datastructure.Dissolve;
import net.glease.chem.simple.datastructure.Reactant;
import net.glease.chem.simple.datastructure.Reaction;
import net.glease.chem.simple.datastructure.Reagent;
import net.glease.chem.simple.datastructure.ReagentState;
import net.glease.chem.simple.datastructure.Resultant;
import net.glease.chem.simple.datastructure.Substance;
import net.glease.chem.simple.datastructure.SubstanceContent;
import net.glease.chem.simple.datastructure.impl.AtomImpl;
import net.glease.chem.simple.datastructure.impl.ChemDatabaseImpl;
import net.glease.chem.simple.datastructure.impl.DissolveImpl;
import net.glease.chem.simple.datastructure.impl.ReactantImpl;
import net.glease.chem.simple.datastructure.impl.ReactionImpl;
import net.glease.chem.simple.datastructure.impl.ReagentImpl;
import net.glease.chem.simple.datastructure.impl.ResultantImpl;
import net.glease.chem.simple.datastructure.impl.SubstanceContentImpl;
import net.glease.chem.simple.datastructure.impl.SubstanceImpl;
import net.glease.chem.simple.util.Adaptors;

class UnmarshallingHanlder implements ContentHandler {
	private static class SetterHelper {
		private Attributes atts;

		private SetterHelper() {
		}

		private String get(String localName) {
			String value = atts.getValue("", localName);
			return value;
		}

		private void set(String localName, Consumer<String> setter) {
			String v = get(localName);
			if (v != null)
				setter.accept(v);
		}

		private <T> void set(String localName, Consumer<String> setter, Supplier<String> defaults) {
			String v = get(localName);
			setter.accept(v == null ? defaults.get() : v);
		}

		private <T> void set(String localName, Consumer<T> setter, Function<String, T> mapper) {
			String v = get(localName);
			if (v != null)
				setter.accept(mapper.apply(v));
		}

		private void set(String localName, DoubleConsumer setter) {
			String v = get(localName);
			if (v != null)
				setter.accept(parseDouble(v));
		}

		public void setAtts(Attributes atts) {
			this.atts = atts;
		}

		private void setInt(String localName, IntConsumer setter) {
			String v = get(localName);
			if (v != null)
				setter.accept(parseInt(v));
		}

	}

	static class UnexpectedContentException extends SAXParseException {
		private static final long serialVersionUID = 1L;

		private UnexpectedContentException(String message, Locator locator) {
			super(message, locator);
		}
	}

	private Locator locator = null;
	private boolean parsing = false;
	private String prefix;
	private Object parent;
	private ChemDatabaseImpl instance;

	private final SetterHelper set = new SetterHelper();

	private Map<String, ReagentImpl> dummies = new HashMap<>();

	private int reactionId = 0;

	UnmarshallingHanlder() {
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	}

	private ReagentImpl createDummyReagent(String id) {
		ReagentImpl s = new ReagentImpl();
		s.setId(id);
		dummies.put(id, s);
		return s;
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (parsing && CDB_SIMPLE_NAMESPACE.equals(uri))
			switch (localName) {
			case "substance":
			case "reagent":
			case "reaction":
				parent = null;
				break;
			default:
				break;
			}
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		if (parsing && this.prefix.equals(prefix)) {
			parsing = false;
			this.prefix = null;
		}
	}

	public ChemDatabaseImpl get() {
		return instance;
	}

	private String getCurrentParentTypeName() {
		if (parent == null)
			return "null";
		Class<?>[] cs = parent.getClass().getInterfaces();
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] != Serializable.class)
				return cs[i].getSimpleName().toLowerCase();
		}
		throw new AssertionError();
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		unexpectedContent();
	}

	private void readAtom() throws SAXParseException {
		if (parent != null)
			throw new SAXParseException(
					"Illegal nested element, expecting atoms but found " + getCurrentParentTypeName(), locator);

		Atom a = new AtomImpl();

		set.setInt("index", a::setIndex);
		set.set("localizedName", a::setLocalizedName);
		set.setInt("molMass", a::setMolMass);
		set.set("symbol", a::setSymbol);

		a.bind(instance);
	}

	private void readCatalyst() throws SAXParseException {
		if (!(parent instanceof Reaction))
			throw new SAXParseException(
					"Illegal nested element, expecting reaction but found " + getCurrentParentTypeName(), locator);
		((Reaction) parent).getCatalysts().add(instance.getReagents().get(set.get("reagent")));
	}

	private void readCDBInfos() {
		ChemDatabaseImpl i = instance = new ChemDatabaseImpl();
		
		set.set("uuid", i::setUUID, UUID::fromString);
		set.set("info", i::setInfo);
		set.set("version", i::setVersion);
	}

	private void readCondition() throws SAXParseException {
		if (!(parent instanceof Reaction))
			throw new SAXParseException(
					"Illegal nested element, expecting reaction but found " + getCurrentParentTypeName(), locator);
		((Reaction) parent).getConditions().add(set.get("value"));
	}

	private void readDissolve() throws SAXParseException {
		if (!(parent instanceof Substance))
			throw new SAXParseException(
					"Illegal nested element, expecting substance but found " + getCurrentParentTypeName(), locator);

		Dissolve d = new DissolveImpl();

		set.set("solvent", d::setSolvent, this::createDummyReagent);
		set.set("s2TFunction", d::setS2TFunction);// TODO

		d.bind((Substance) parent);
	}

	private void readReactant() throws SAXParseException {
		if (!(parent instanceof Reaction))
			throw new SAXParseException(
					"Illegal nested element, expecting reaction but found " + getCurrentParentTypeName(), locator);

		Reactant b = new ReactantImpl();

		set.set("purity", b::setPurity);
		set.setInt("mol", b::setMol);
		set.set("state", b::setState, ReagentState::fromValue);
		set.set("substance", b::setSubstance, instance.getSubstances()::get);

		b.bind((Reaction) parent);
	}

	private void readReaction() throws SAXParseException {
		if (parent != null)
			throw new SAXParseException(
					"Illegal nested element, expecting reactions but found " + getCurrentParentTypeName(), locator);

		Reaction e = new ReactionImpl();

		set.set("speed", e::setSpeed);
		set.set("K", e::setK);
		set.set("temp", e::setTemp);
		set.set("pressure", e::setPressure);
		set.set("heat", e::setHeat);
		set.set("solvent", e::setSolvent, instance.getReagents()::get);
		set.set("id", e::setId, () -> "__r_" + reactionId++);
		set.set("name", e::setName);

		e.bind(instance); // bind only when id is set

		parent = e;
	}

	private void readReagent() throws SAXParseException {
		if (parent != null)
			throw new SAXParseException(
					"Illegal nested element, expecting reagents but found " + getCurrentParentTypeName(), locator);

		Reagent r;

		if ((r = dummies.get(set.get("id"))) == null) {
			r = new ReagentImpl();
			set.set("id", r::setId);
		}

		set.set("name", r::setName);
		set.set("color", r::setColor, Adaptors::readColor);
		set.set("concentration", r::setConcentration);
		set.set("solvent", r::setSolvent, instance.getSubstances()::get);
		set.set("substance", r::setSubstance, instance.getSubstances()::get);
		set.set("state", r::setState, ReagentState::fromValue);

		r.bind(instance);

		parent = r;
	}

	private void readResultant() throws SAXParseException {
		if (!(parent instanceof Reaction))
			throw new SAXParseException(
					"Illegal nested element, expecting reaction but found " + getCurrentParentTypeName(), locator);

		Resultant u = new ResultantImpl();

		set.setInt("mol", u::setMol);
		set.set("state", u::setState, ReagentState::fromValue);
		set.set("substance", u::setSubstance, instance.getSubstances()::get);

		u.bind((Reaction) parent);
	}

	private void readSubstance() throws SAXParseException {
		if (parent != null)
			throw new SAXParseException(
					"Illegal nested element, expecting substances but found " + getCurrentParentTypeName(), locator);

		Substance s = new SubstanceImpl();

		set.set("id", s::setId);
		set.set("name", s::setName);
		set.set("crystal", s::setCrystal, CrystalType::fromValue);
		set.set("meltPoint", s::setMeltPoint);
		set.set("boilPoint", s::setBoilPoint);

		s.bind(instance);

		parent = s;
	}

	private void readSubstanceContent() throws SAXParseException {
		if (!(parent instanceof Substance))
			throw new SAXParseException(
					"Illegal nested element, expecting substance but found " + getCurrentParentTypeName(), locator);

		SubstanceContent c = new SubstanceContentImpl();

		set.set("atom", c::setAtom, instance.getAtoms()::get);
		set.setInt("mol", c::setMol);

		c.bind((Substance) parent);
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		this.locator = locator;
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
	}

	@Override
	public void startDocument() throws SAXException {
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		if (!parsing || !CDB_SIMPLE_NAMESPACE.equals(uri))
			return;

		set.setAtts(atts);

		try {
			switch (localName) {
			case "database":
				readCDBInfos();
				break;
			case "atom":
				if (parent == null)
					readAtom();
				else
					readSubstanceContent();
				break;
			case "substance":
				readSubstance();
				break;
			case "reagent":
				readReagent();
				break;
			case "reaction":
				readReaction();
				break;
			case "dissolve":
				readDissolve();
				break;
			case "resultant":
				readResultant();
				break;
			case "reactant":
				readReactant();
				break;
			case "condition":
				readCondition();
				break;
			case "catalyst":
				readCatalyst();
				break;
			case "atoms":
			case "substances":
			case "reactions":
			case "reagents":
				break;
			default:
				unexpectedContent();
				break;
			}
		} catch (NullPointerException | IllegalArgumentException e) {
			throw new SAXParseException("Please validate your document", locator, e);
		} catch (Exception e) {
			throw new SAXParseException("Unexpected exception", locator, e);
		}
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		if (CDB_SIMPLE_NAMESPACE.equals(uri)) {
			parsing = true;
			this.prefix = prefix;
		}
	}

	public void unexpectedContent() throws SAXException {
		if (parsing)
			throw new UnexpectedContentException("Unexpected content", locator);
	}

}
