
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * CountedAtom complex type�� Java �ࡣ
 *
 * <p>
 * ����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
	 * ��ȡatom���Ե�ֵ��
	 * 
	 * @return possible object is {@link Object }
	 * 
	 */
	Atom getAtom();

	/**
	 * ��ȡmol���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
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
	 *            allowed object is {@link String }
	 * 
	 */
	void setMol(Integer value);

}
