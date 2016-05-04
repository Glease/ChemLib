
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * CountedAtom complex type的 Java 类。
 *
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="CountedAtom">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="mol" type="{http://glease.net/chem/simple/DataStructure}PositiveInteger" default="1" />
 *       &lt;attribute ref="{http://glease.net/chem/simple/DataStructure}atom use="required""/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
public interface CountedAtom {

	/**
	 * 获取atom属性的值。
	 * 
	 * @return possible object is {@link Object }
	 * 
	 */
	Atom getAtom();

	/**
	 * 获取mol属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	int getMol();

	/**
	 * 设置atom属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link Object }
	 * 
	 */
	void setAtom(Atom value);

	/**
	 * 设置mol属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setMol(Integer value);

}
