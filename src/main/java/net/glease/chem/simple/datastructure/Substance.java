
package net.glease.chem.simple.datastructure;

import java.util.List;

/**
 *
 * Substance is something you know on your textbook, like you know there is
 * something called Carbon Dioxide.
 * 
 *
 * <p>
 * Substance complex type的 Java 类。
 *
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="Substance">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="atom" type="{http://glease.net/chem/simple/DataStructure}SubstanceContent" maxOccurs="unbounded"/>
 *         &lt;element name="dissovle" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute ref="{http://glease.net/chem/simple/DataStructure}solvent use="required""/>
 *                 &lt;attribute name="s2TFunction" use="required" type="{http://glease.net/chem/simple/DataStructure}Expression" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="meltPoint" type="{http://glease.net/chem/simple/DataStructure}Temperature" />
 *       &lt;attribute name="boilPoint" type="{http://glease.net/chem/simple/DataStructure}Temperature" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="crystal" type="{http://glease.net/chem/simple/DataStructure}CrystalType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
public interface Substance {

	/**
	 * <p>
	 * anonymous complex type的 Java 类。
	 * 
	 * <p>
	 * 以下模式片段指定包含在此类中的预期内容。
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
	public interface Dissovle {

		/**
		 * 获取s2TFunction属性的值。
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		String getS2TFunction();

		/**
		 * 获取solvent属性的值。
		 * 
		 * @return possible object is {@link Object }
		 * 
		 */
		Reagent getSolvent();

		/**
		 * 设置s2TFunction属性的值。
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		void setS2TFunction(String value);

		/**
		 * 设置solvent属性的值。
		 * 
		 * @param value
		 *            allowed object is {@link Object }
		 * 
		 */
		void setSolvent(Reagent value);

	}

	/**
	 * Gets the value of the atom property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the atom property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAtom().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link SubstanceContent }
	 * 
	 * 
	 */
	List<SubstanceContent> getAtom();

	/**
	 * 
	 * Represents a temperature, measured in K, not Censils
	 * 
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	double getBoilPoint();

	/**
	 * 
	 * For mixed crystal, use none.
	 * 
	 * 
	 * @return possible object is {@link CrystalType }
	 * 
	 */
	CrystalType getCrystal();

	/**
	 * Gets the value of the dissovle property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the dissovle property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getDissovle().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link Substance.Dissovle }
	 * 
	 * 
	 */
	List<Substance.Dissovle> getDissovle();

	/**
	 * 获取id属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getId();

	/**
	 * 
	 * Represents a temperature, measured in K, not Censils
	 * 
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	double getMeltPoint();

	/**
	 * 获取name属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getName();

	/**
	 * 设置boilPoint属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setBoilPoint(double value);

	/**
	 * 设置crystal属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link CrystalType }
	 * 
	 */
	void setCrystal(CrystalType value);

	/**
	 * 设置id属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setId(String value);

	/**
	 * 设置meltPoint属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setMeltPoint(double value);

	/**
	 * 设置name属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setName(String value);

}
