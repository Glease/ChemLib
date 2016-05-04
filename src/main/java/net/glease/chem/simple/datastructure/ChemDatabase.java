
package net.glease.chem.simple.datastructure;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * ChemDatabase complex type�� Java �ࡣ
 *
 * <p>
 * ����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 *
 * <pre>
 * &lt;complexType name="ChemDatabase">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all minOccurs="0">
 *         &lt;element name="substances" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="substance" type="{http://glease.net/chem/simple/DataStructure}Substance" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="equations" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="equation" type="{http://glease.net/chem/simple/DataStructure}Equation" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="atoms" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="atom" type="{http://glease.net/chem/simple/DataStructure}Atom" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="reagents" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="reagent" type="{http://glease.net/chem/simple/DataStructure}Reagent" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *       &lt;attribute name="UUID" use="required" type="{http://glease.net/chem/simple/DataStructure}UUID" />
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="info" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
public interface ChemDatabase {

	default ChemDatabaseFinder finder() {
		return new ChemDatabaseFinder(this);
	}
	
	/**
	 * ��ȡatoms���Ե�ֵ��
	 * 
	 * @return possible object is {@link List<Atom> }
	 * 
	 */
	List<Atom> getAtoms();

	/**
	 * ��ȡequations���Ե�ֵ��
	 * 
	 * @return possible object is {@link List<Equation> }
	 * 
	 */
	List<Equation> getEquations();

	/**
	 * ��ȡinfo���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getInfo();

	/**
	 * ��ȡreagents���Ե�ֵ��
	 * 
	 * @return possible object is {@link List<Reagent> }
	 * 
	 */
	List<Reagent> getReagents();

	/**
	 * ��ȡsubstances���Ե�ֵ��
	 * 
	 * @return possible object is {@link List<Substance> }
	 * 
	 */
	List<Substance> getSubstances();

	/**
	 * ��ȡuuid���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	UUID getUUID();

	/**
	 * 
	 * A version string. Loading databases with same UUID but different version
	 * should result in a crash.
	 * 
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getVersion();

	/**
	 * ����atoms���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link List<Atom> }
	 * 
	 */
	void setAtoms(List<Atom> value);

	/**
	 * ����equations���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link List<Equation> }
	 * 
	 */
	void setEquations(List<Equation> value);

	/**
	 * ����info���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setInfo(String value);

	/**
	 * ����reagents���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link List<Reagent> }
	 * 
	 */
	void setReagents(List<Reagent> value);

	/**
	 * ����substances���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link List<Substance> }
	 * 
	 */
	void setSubstances(List<Substance> value);

	/**
	 * ����uuid���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setUUID(UUID value);

	/**
	 * ����version���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setVersion(String value);

}
