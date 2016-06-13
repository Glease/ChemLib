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
 * @author glease
 * @since 0.1
 */
public interface Reagent extends Element<ChemDatabase, Reagent> {

	/**
	 * Get the value of color.
	 * 
	 * @return possible object is {@link Color }
	 * 
	 */
	Color getColor();

	/**
	 * Get the value of concentration.
	 * 
	 * @return possible object is {@link double }
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
	 * @return possible object is {@link Substance }
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
	 * @return possible object is {@link Substance }
	 * 
	 */
	Substance getSubstance();

	/**
	 * Get the value of concentration.
	 * 
	 * @param value
	 *            allowed object is {@link Color }
	 * 
	 */

	void setColor(Color color);

	/**
	 * Set the value of concentration.
	 * 
	 * @param value
	 *            allowed object is {@link double }
	 * 
	 */
	void setConcentration(double value);

	/**
	 * Set the value of id.
	 * 
	 * @param value
	 *            allowed object is {@link String }
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
	 *            allowed object is {@link Substance }
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
	 *            allowed object is {@link Substance }
	 * 
	 */
	void setSubstance(Substance value);

}
