
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * The Java class of EquationComponent. Common super interface of
 * {@link Reactant} and {@link Resultant}. Directly implementing/extending this
 * is prohibited.
 * 
 * @author glease
 * @since 0.1
 *
 */
public interface ReactionComponent<T_THIS extends ReactionComponent<T_THIS>> extends Element<Reaction, T_THIS> {

	/**
	 * Should be always effectively the same as {@code getSubstance().getId()}.
	 */
	@Override
	default String getId() {
		return getSubstance().getId();
	}

	/**
	 * Get the value of mol.
	 * 
	 * @return possible object is {@link int }
	 * 
	 */
	int getMol();

	/**
	 * Get the value of state.
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
	 * Set the value of mol.
	 * 
	 * @param value
	 *            allowed object is {@link int }
	 * 
	 */
	void setMol(int value);

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
