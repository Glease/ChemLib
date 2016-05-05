
package net.glease.chem.simple.datastructure;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;

import net.glease.chem.simple.datastructure.impl.AtomImpl;
import net.glease.chem.simple.datastructure.impl.CountedAtomImpl;
import net.glease.chem.simple.datastructure.impl.EquationImpl;
import net.glease.chem.simple.datastructure.impl.ReactantImpl;
import net.glease.chem.simple.datastructure.impl.ReagentImpl;
import net.glease.chem.simple.datastructure.impl.ResultantImpl;
import net.glease.chem.simple.datastructure.impl.SubstanceContentImpl;
import net.glease.chem.simple.datastructure.impl.SubstanceImpl;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the net.glease.chem.simple.datastructure2
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

	/*
	 * What is this? TODO
	 */
	@SuppressWarnings("unused")
	private final static Void _useJAXBProperties = null;

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: net.glease.chem.simple.datastructure2
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link Atom }
	 * 
	 */
	public Atom createAtom() {
		return new AtomImpl();
	}

	/**
	 * Create an instance of {@link ChemDatabase }
	 * 
	 */
	public ChemDatabase createChemDatabase() {
		return new ChemDatabase();
	}

	/**
	 * Create an instance of {@link CountedAtom }
	 * 
	 */
	public CountedAtom createCountedAtom() {
		return new CountedAtomImpl();
	}

	/**
	 * Create an instance of {@link Database }}
	 * 
	 */
	@XmlElementDecl(namespace = "http://glease.net/chem/simple/DataStructure", name = "database")
	public Database createDatabase(ChemDatabase value) {
		return new Database(((ChemDatabase) value));
	}

	/**
	 * Create an instance of {@link Equation }
	 * 
	 */
	public Equation createEquation() {
		return new EquationImpl();
	}

	/**
	 * Create an instance of {@link Equation.Catalyst }
	 * 
	 */
	public Equation.Catalyst createEquationCatalyst() {
		return new EquationImpl.CatalystImpl();
	}

	/**
	 * Create an instance of {@link Reactant }
	 * 
	 */
	public Reactant createReactant() {
		return new ReactantImpl();
	}

	/**
	 * Create an instance of {@link Reagent }
	 * 
	 */
	public Reagent createReagent() {
		return new ReagentImpl();
	}

	/**
	 * Create an instance of {@link Resultant }
	 * 
	 */
	public Resultant createResultant() {
		return new ResultantImpl();
	}

	/**
	 * Create an instance of {@link Substance }
	 * 
	 */
	public Substance createSubstance() {
		return new SubstanceImpl();
	}

	/**
	 * Create an instance of {@link SubstanceContent }
	 * 
	 */
	public SubstanceContent createSubstanceContent() {
		return new SubstanceContentImpl();
	}

	/**
	 * Create an instance of {@link Substance.Dissovle }
	 * 
	 */
	public Substance.Dissovle createSubstanceDissovle() {
		return new SubstanceImpl.DissovleImpl();
	}

}
