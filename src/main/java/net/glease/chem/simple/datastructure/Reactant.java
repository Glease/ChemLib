
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * Reactant complex type的 Java 类。
 *
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="Reactant">
 *   &lt;complexContent>
 *     &lt;extension base="{http://glease.net/chem/simple/DataStructure}EquationComponent">
 *       &lt;attribute name="purity" type="{http://glease.net/chem/simple/DataStructure}Percentage" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
public interface Reactant extends EquationComponent {

	/**
	 * 获取purity属性的值。
	 * 
	 * @return possible object is {@link float }
	 * 
	 */
	float getPurity();

	/**
	 * 设置purity属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link float }
	 * 
	 */
	void setPurity(Float value);

}
