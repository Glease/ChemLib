
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * The Java class of EquationComponent.
 *
 * <p>
 * The following XML Schema snippet contains the expect content of this class.
 *
 * <pre>
 * &lt;complexType name="ReactionComponent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="mol" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" default="1" />
 *       &lt;attribute ref="{http://glease.net/chem/simple/DataStructure}substance use="required""/>
 *       &lt;attribute name="state" type="{http://glease.net/chem/simple/DataStructure}ReagentState" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
public interface ReactionComponent extends Element<Reaction> {

	@Override
	default String getId() {
		return getSubstance().getId();
	}
	
	/**
	 * Get the value of mol.
	 * 
	 * @return possible object is {@link Integer }
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
	 * @return possible object is {@link Object }
	 * 
	 */
	Substance getSubstance();

	/**
	 * Set the value of mol.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
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
	 *            allowed object is {@link Object }
	 * 
	 */
	void setSubstance(Substance value);

}
