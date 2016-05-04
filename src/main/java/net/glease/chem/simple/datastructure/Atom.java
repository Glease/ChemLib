
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * Atom complex type的 Java 类。
 *
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
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
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
public interface Atom {

	/**
	 * 获取id属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getId();

	/**
	 * 获取index属性的值。
	 * 
	 */
	short getIndex();

	/**
	 * 获取localizedName属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getLocalizedName();

	/**
	 * 获取molMass属性的值。
	 * 
	 * @return possible object is {@link int }
	 * 
	 */
	int getMolMass();

	/**
	 * 获取symbol属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getSymbol();

	/**
	 * 设置id属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setId(String value);

	/**
	 * 设置index属性的值。
	 * 
	 */
	void setIndex(short value);

	/**
	 * 设置localizedName属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setLocalizedName(String value);

	/**
	 * 设置molMass属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setMolMass(int value);

	/**
	 * 设置symbol属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setSymbol(String value);

}
