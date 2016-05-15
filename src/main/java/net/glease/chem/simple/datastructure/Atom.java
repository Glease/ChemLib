
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * The Java class of Atom.
 *
 * <p>
 * The following XML Schema snippet contains the expect content of this class.
 *
 * <pre>
 * &lt;complexType name="Atom">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="localizedName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="symbol">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *             &lt;maxLength value="2"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="molMass" use="required" type="{http://glease.net/chem/simple/DataStructure}JavaFloat" />
 *       &lt;attribute name="index" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" />
 *       &lt;attribute name="id" use="required" >
 *         &lt;xsd:simpleType>
 *           &lt;xsd:restriction base="xsd:ID">
 *             &lt;xsd:pattern value="atom\d{1,3}-\d{1,3}" />
 *           &lt;/xsd:restriction>
 *         &lt;/xsd:simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
public interface Atom extends Element<ChemDatabase> {

	/**
	 * 
	 */
	@Override
	default String getId() {
		return "atom" + getIndex() + "-" + getMolMass();
	}

	/**
	 * Get the value of index.
	 * 
	 */
	int getIndex();

	/**
	 * Get the value of localizedName.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getLocalizedName();

	/**
	 * Get the value of molMass.
	 * 
	 * @return possible object is {@link int }
	 * 
	 */
	int getMolMass();

	/**
	 * Get the value of symbol.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getSymbol();

	/**
	 * Set the value of index.
	 * 
	 */
	void setIndex(int value);

	/**
	 * Set the value of localizedName.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setLocalizedName(String value);

	/**
	 * Set the value of molMass.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setMolMass(int value);

	/**
	 * Set the value of symbol.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setSymbol(String value);

}
