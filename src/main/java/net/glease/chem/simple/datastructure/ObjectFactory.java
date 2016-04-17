
package net.glease.chem.simple.datastructure;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the net.glease.chem.simple.datastructure
 * package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _Database_QNAME = new QName("http://glease.net/chem/simple/DataStructure", "database");
	private final static QName _EquationCatalyst_QNAME = new QName("http://glease.net/chem/simple/DataStructure",
			"catalyst");
	private final static QName _EquationResultant_QNAME = new QName("http://glease.net/chem/simple/DataStructure",
			"resultant");
	private final static QName _EquationCondition_QNAME = new QName("http://glease.net/chem/simple/DataStructure",
			"condition");
	private final static QName _EquationReactant_QNAME = new QName("http://glease.net/chem/simple/DataStructure",
			"reactant");
	public static final String NAMESPACE = "http://glease.net/chem/simple/DataStructure";

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: net.glease.chem.simple.datastructure
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link Atom }
	 * 
	 */
	public Atom createAtom() {
		return new Atom();
	}

	/**
	 * Create an instance of {@link ChemDatabase }
	 * 
	 */
	public ChemDatabase createChemDatabase() {
		return new ChemDatabase();
	}

	/**
	 * Create an instance of {@link ChemDatabase.Atoms }
	 * 
	 */
	public ChemDatabase.Atoms createChemDatabaseAtoms() {
		return new ChemDatabase.Atoms();
	}

	/**
	 * Create an instance of {@link ChemDatabase.Equations }
	 * 
	 */
	public ChemDatabase.Equations createChemDatabaseEquations() {
		return new ChemDatabase.Equations();
	}

	/**
	 * Create an instance of {@link ChemDatabase.Reagents }
	 * 
	 */
	public ChemDatabase.Reagents createChemDatabaseReagents() {
		return new ChemDatabase.Reagents();
	}

	/**
	 * Create an instance of {@link ChemDatabase.Substances }
	 * 
	 */
	public ChemDatabase.Substances createChemDatabaseSubstances() {
		return new ChemDatabase.Substances();
	}

	/**
	 * Create an instance of {@link CountedAtom }
	 * 
	 */
	public CountedAtom createCountedAtom() {
		return new CountedAtom();
	}

	/**
	 * Create an instance of {@link CountedReagent }
	 * 
	 */
	public CountedReagent createCountedReagent() {
		return new CountedReagent();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ChemDatabase }
	 * {@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://glease.net/chem/simple/DataStructure", name = "database")
	public JAXBElement<ChemDatabase> createDatabase(ChemDatabase value) {
		return new JAXBElement<ChemDatabase>(_Database_QNAME, ChemDatabase.class, null, value);
	}

	/**
	 * Create an instance of {@link Equation }
	 * 
	 */
	public Equation createEquation() {
		return new Equation();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Reagent }
	 * {@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://glease.net/chem/simple/DataStructure", name = "catalyst", scope = Equation.class)
	public JAXBElement<Reagent> createEquationCatalyst(Reagent value) {
		return new JAXBElement<Reagent>(_EquationCatalyst_QNAME, Reagent.class, Equation.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }
	 * {@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://glease.net/chem/simple/DataStructure", name = "condition", scope = Equation.class)
	public JAXBElement<String> createEquationCondition(String value) {
		return new JAXBElement<String>(_EquationCondition_QNAME, String.class, Equation.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link CountedReagent
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://glease.net/chem/simple/DataStructure", name = "reactant", scope = Equation.class)
	public JAXBElement<CountedReagent> createEquationReactant(CountedReagent value) {
		return new JAXBElement<CountedReagent>(_EquationReactant_QNAME, CountedReagent.class, Equation.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link CountedReagent
	 * }{@code >}}
	 * 
	 */
	@XmlElementDecl(namespace = "http://glease.net/chem/simple/DataStructure", name = "resultant", scope = Equation.class)
	public JAXBElement<CountedReagent> createEquationResultant(CountedReagent value) {
		return new JAXBElement<CountedReagent>(_EquationResultant_QNAME, CountedReagent.class, Equation.class, value);
	}

	/**
	 * Create an instance of {@link Reagent }
	 * 
	 */
	public Reagent createReagent() {
		return new Reagent();
	}

	/**
	 * Create an instance of {@link Substance }
	 * 
	 */
	public Substance createSubstance() {
		return new Substance();
	}

	/**
	 * Create an instance of {@link SubstanceContent }
	 * 
	 */
	public SubstanceContent createSubstanceContent() {
		return new SubstanceContent();
	}

	/**
	 * Create an instance of {@link Substance.Dissovle }
	 * 
	 */
	public Substance.Dissovle createSubstanceDissovle() {
		return new Substance.Dissovle();
	}

}
