
package net.glease.chem.simple.datastructure;

/**
 *
 * Reagent is something you can obtain in the lab, or somewhere else in the real
 * world.
 *
 * Reagents and substances differ in that reagents have states like temperature,
 * solvent, state, etc., while substance never has such kind of states.
 * 
 *
 * <p>
 * Reagent complex type�� Java �ࡣ
 *
 * <p>
 * ����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 *
 * <pre>
 * &lt;complexType name="Reagent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute ref="{http://glease.net/chem/simple/DataStructure}substance use="required""/>
 *       &lt;attribute ref="{http://glease.net/chem/simple/DataStructure}solvent"/>
 *       &lt;attribute name="concentration" type="{http://glease.net/chem/simple/DataStructure}Percentage" default="100" />
 *       &lt;attribute name="state" type="{http://glease.net/chem/simple/DataStructure}ReagentState" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
public interface Reagent {

	/**
	 * ��ȡconcentration���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	float getConcentration();

	/**
	 * ��ȡid���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getId();

	/**
	 * 
	 * Sometimes some certain kind of reagents has different names with their
	 * corresponding pure substance. Fill it here. If it don't have, leave it
	 * empty.
	 * 
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getName();

	/**
	 * ��ȡsolvent���Ե�ֵ��
	 * 
	 * @return possible object is {@link Object }
	 * 
	 */
	Reagent getSolvent();

	/**
	 * 
	 * The melt/boil point defined in the substance tag is ignored if state is
	 * solution, since it may be changed.
	 * 
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
	 * ����concentration���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setConcentration(Float value);

	/**
	 * ����id���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setId(String value);

	/**
	 * ����name���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setName(String value);

	/**
	 * ����solvent���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link Object }
	 * 
	 */
	void setSolvent(Reagent value);

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
