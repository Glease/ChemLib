
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * Atom complex type�� Java �ࡣ
 *
 * <p>
 * ����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
	 * ��ȡid���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getId();

	/**
	 * ��ȡindex���Ե�ֵ��
	 * 
	 */
	short getIndex();

	/**
	 * ��ȡlocalizedName���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getLocalizedName();

	/**
	 * ��ȡmolMass���Ե�ֵ��
	 * 
	 * @return possible object is {@link int }
	 * 
	 */
	int getMolMass();

	/**
	 * ��ȡsymbol���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getSymbol();

	/**
	 * ����id���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setId(String value);

	/**
	 * ����index���Ե�ֵ��
	 * 
	 */
	void setIndex(short value);

	/**
	 * ����localizedName���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setLocalizedName(String value);

	/**
	 * ����molMass���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setMolMass(int value);

	/**
	 * ����symbol���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setSymbol(String value);

}
