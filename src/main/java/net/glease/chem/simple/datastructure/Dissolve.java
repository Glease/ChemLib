package net.glease.chem.simple.datastructure;

/**
 * <p>
 * The Java class of anonymous.
 * 
 * <p>
 * The following XML Schema snippet contains the expect content of this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute ref="{http://glease.net/chem/simple/DataStructure}solvent use="required""/>
 *       &lt;attribute name="s2TFunction" use="required" type="{http://glease.net/chem/simple/DataStructure}Expression" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public interface Dissolve extends Element<Substance> {

	/**
	 * Get the value of s2TFunction.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getS2TFunction();

	/**
	 * Get the value of solvent.
	 * 
	 * @return possible object is {@link Object }
	 * 
	 */
	Reagent getSolvent();
	
	@Override
	default String getId() {
		return getSolvent().getId();
	}

	/**
	 * Set the value of s2TFunction.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setS2TFunction(String value);

	/**
	 * Set the value of solvent.
	 * 
	 * @param value
	 *            allowed object is {@link Object }
	 * 
	 */
	void setSolvent(Reagent value);

}