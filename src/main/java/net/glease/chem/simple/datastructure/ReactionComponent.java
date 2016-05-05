
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * EquationComponent complex type�� Java �ࡣ
 *
 * <p>
 * ����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
	 * ��ȡmol���Ե�ֵ��
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	int getMol();

	/**
	 * ��ȡstate���Ե�ֵ��
	 * 
	 * @return possible object is {@link ReagentState }
	 * 
	 */
	ReagentState getState();

	/**
	 * ��ȡsubstance���Ե�ֵ��
	 * 
	 * @return possible object is {@link Object }
	 * 
	 */
	Substance getSubstance();

	/**
	 * ����mol���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	void setMol(Integer value);

	/**
	 * ����state���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link ReagentState }
	 * 
	 */
	void setState(ReagentState value);

	/**
	 * ����substance���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link Object }
	 * 
	 */
	void setSubstance(Substance value);

}
