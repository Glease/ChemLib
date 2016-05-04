
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * SubstanceContent complex type�� Java �ࡣ
 *
 * <p>
 * ����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 *
 * <pre>
 * &lt;complexType name="SubstanceContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute ref="{http://glease.net/chem/simple/DataStructure}atom use="required""/>
 *       &lt;attribute name="mol" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" default="1" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
public interface SubstanceContent {

	/**
	 * ��ȡatom���Ե�ֵ��
	 * 
	 * @return possible object is {@link Object }
	 * 
	 */
	Atom getAtom();

	/**
	 * ��ȡmol���Ե�ֵ��
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	int getMol();

	/**
	 * ����atom���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link Object }
	 * 
	 */
	void setAtom(Atom value);

	/**
	 * ����mol���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	void setMol(Integer value);

}
