
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * The Java class of SubstanceContent.
 *
 * <p>
 * The following XML Schema snippet contains the expect content of this class.
 *
 * <pre>
 * &lt;complexType name="SubstanceContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute ref="{http://glease.net/chem/simple/DataStructure}content use="required""/>
 *       &lt;attribute name="mol" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" default="1" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
public interface SubstanceContent extends Element<Substance> {

	/**
	 * Get the value of atom.
	 * 
	 * @return possible object is {@link Object }
	 * 
	 */
	Atom getAtom();

	@Override
	default String getId() {
		return getAtom().getId();
	}
	
	/**
	 * Get the value of mol.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	int getMol();

	/**
	 * Set the value of atom.
	 * 
	 * @param value
	 *            allowed object is {@link Atom }
	 * 
	 */
	void setAtom(Atom value);

	/**
	 * Set the value of mol.
	 * 
	 * @param value
	 *            allowed type is {@code int }
	 * 
	 */
	void setMol(int value);

}
