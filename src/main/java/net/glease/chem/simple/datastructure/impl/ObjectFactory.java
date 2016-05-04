
package net.glease.chem.simple.datastructure.impl;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;

import net.glease.chem.simple.datastructure.Atom;
import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.CountedAtom;
import net.glease.chem.simple.datastructure.Database;
import net.glease.chem.simple.datastructure.Equation;
import net.glease.chem.simple.datastructure.Reactant;
import net.glease.chem.simple.datastructure.Reagent;
import net.glease.chem.simple.datastructure.Resultant;
import net.glease.chem.simple.datastructure.Substance;
import net.glease.chem.simple.datastructure.SubstanceContent;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the net.glease.chem.simple.datastructure2.impl
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

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package:
	 * net.glease.chem.simple.datastructure2.impl
	 *
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link Atom }
	 *
	 */
	public AtomImpl createAtom() {
		return new AtomImpl();
	}

	/**
	 * Create an instance of {@link ChemDatabase }
	 *
	 */
	public ChemDatabaseImpl createChemDatabase() {
		return new ChemDatabaseImpl();
	}

	/**
	 * Create an instance of {@link CountedAtom }
	 *
	 */
	public CountedAtomImpl createCountedAtom() {
		return new CountedAtomImpl();
	}

	/**
	 * Create an instance of {@link Database }}
	 *
	 */
	@XmlElementDecl(namespace = "http://glease.net/chem/simple/DataStructure", name = "database")
	public Database createDatabase(ChemDatabaseImpl value) {
		return new Database(value);
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
	public EquationImpl.CatalystImpl createEquationCatalyst() {
		return new EquationImpl.CatalystImpl();
	}

	/**
	 * Create an instance of {@link Reactant }
	 *
	 */
	public ReactantImpl createReactant() {
		return new ReactantImpl();
	}

	/**
	 * Create an instance of {@link Reagent }
	 *
	 */
	public ReagentImpl createReagent() {
		return new ReagentImpl();
	}

	/**
	 * Create an instance of {@link Resultant }
	 *
	 */
	public ResultantImpl createResultant() {
		return new ResultantImpl();
	}

	/**
	 * Create an instance of {@link Substance }
	 *
	 */
	public SubstanceImpl createSubstance() {
		return new SubstanceImpl();
	}

	/**
	 * Create an instance of {@link SubstanceContent }
	 *
	 */
	public SubstanceContentImpl createSubstanceContent() {
		return new SubstanceContentImpl();
	}

	/**
	 * Create an instance of {@link Substance.Dissovle }
	 *
	 */
	public SubstanceImpl.DissovleImpl createSubstanceDissovle() {
		return new SubstanceImpl.DissovleImpl();
	}

}
