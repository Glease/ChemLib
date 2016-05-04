
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * Reactant complex type�� Java �ࡣ
 *
 * <p>
 * ����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
	 * ��ȡpurity���Ե�ֵ��
	 * 
	 * @return possible object is {@link float }
	 * 
	 */
	float getPurity();

	/**
	 * ����purity���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link float }
	 * 
	 */
	void setPurity(Float value);

}
