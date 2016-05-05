
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * EquationComponent complex type的 Java 类。
 *
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
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
public interface ReactionComponent {

	/**
	 * 获取mol属性的值。
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	int getMol();

	/**
	 * 获取state属性的值。
	 * 
	 * @return possible object is {@link ReagentState }
	 * 
	 */
	ReagentState getState();

	/**
	 * 获取substance属性的值。
	 * 
	 * @return possible object is {@link Object }
	 * 
	 */
	Substance getSubstance();

	/**
	 * 设置mol属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	void setMol(Integer value);

	/**
	 * 设置state属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link ReagentState }
	 * 
	 */
	void setState(ReagentState value);

	/**
	 * 设置substance属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link Object }
	 * 
	 */
	void setSubstance(Substance value);

}
