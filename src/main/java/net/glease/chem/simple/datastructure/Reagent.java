
package net.glease.chem.simple.datastructure;

import java.awt.Color;



/**
 *
 * Reagent is something you can obtain in the lab, or somewhere else in the real
 * world.
 *
 * Reagents and substances differ in that reagents have states like temperature,
 * solvent, state, etc., while substance never has such kind of states.
 * 
 *
 * <p>
 * The Java class of Reagent.
 *
 * <p>
 * The following XML Schema snippet contains the expect content of this class.
 *
 * <pre>
 * &lt;complexType name="Reagent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute ref="{http://glease.net/chem/simple/DataStructure}substance use="required""/>
 *       &lt;attribute ref="{http://glease.net/chem/simple/DataStructure}solvent"/>
 *       &lt;attribute name="concentration" type="{http://glease.net/chem/simple/DataStructure}Percentage" default="100" />
 *       &lt;attribute name="state" type="{http://glease.net/chem/simple/DataStructure}ReagentState" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
public interface Reagent extends Element<ChemDatabase> {

	/**
	 * Get the value of concentration.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	double getConcentration();

	/**
	 * 
	 * Sometimes some certain kind of reagents has different names with their
	 * corresponding pure substance. Fill it here. If it don't have, leave it
	 * empty.
	 * 
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getName();

	/**
	 * Get the value of solvent.
	 * 
	 * @return possible object is {@link Object }
	 * 
	 */
	Substance getSolvent();

	/**
	 * 
	 * The melt/boil point defined in the substance tag is ignored if state is
	 * solution, since it may be changed.
	 * 
	 * 
	 * @return possible object is {@link ReagentState }
	 * 
	 */
	ReagentState getState();

	/**
	 * Get the value of substance.
	 * 
	 * @return possible object is {@link Object }
	 * 
	 */
	Substance getSubstance();

	/**
	 * Set the value of concentration.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setConcentration(double value);
	
	/**
	 * Set the value of id.
	 * 
	 */
	void setId(String value);

	/**
	 * Set the value of name.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setName(String value);

	/**
	 * Set the value of solvent.
	 * 
	 * @param value
	 *            allowed object is {@link Object }
	 * 
	 */
	void setSolvent(Substance value);

	/**
	 * Set the value of state.
	 * 
	 * @param value
	 *            allowed object is {@link ReagentState }
	 * 
	 */
	void setState(ReagentState value);

	/**
	 * Set the value of substance.
	 * 
	 * @param value
	 *            allowed object is {@link Object }
	 * 
	 */
	void setSubstance(Substance value);

	void setColor(Color color);

	Color getColor();

}
