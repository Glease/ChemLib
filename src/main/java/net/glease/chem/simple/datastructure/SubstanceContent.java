
package net.glease.chem.simple.datastructure;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * The Java class of SubstanceContent complex type.
 *
 * <p>
 * The following XML Schema snipplet contains the expect content of this class.
 *
 * <pre>
 * &lt;complexType name="SubstanceContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="atom" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="mol" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" default="1" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "SubstanceContent")
public class SubstanceContent {

	protected Atom atom;
	protected BigInteger mol;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SubstanceContent)) {
			return false;
		}
		SubstanceContent other = (SubstanceContent) obj;
		if (atom == null) {
			if (other.atom != null) {
				return false;
			}
		} else if (!atom.equals(other.atom)) {
			return false;
		}
		if (mol == null) {
			if (other.mol != null) {
				return false;
			}
		} else if (!mol.equals(other.mol)) {
			return false;
		}
		return true;
	}

	/**
	 * Get the value of atom.
	 * 
	 * @return possible object is {@link Atom }
	 * 
	 */
	@XmlAttribute(name = "atom", required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	public Atom getAtom() {
		return atom;
	}

	/**
	 * Get the value of mol.
	 * 
	 * @return possible object is {@link BigInteger }
	 * 
	 */
	@XmlAttribute(name = "mol")
	@XmlSchemaType(name = "positiveInteger")
	public BigInteger getMol() {
		if (mol == null) {
			return new BigInteger("1");
		} else {
			return mol;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (atom == null ? 0 : atom.hashCode());
		result = prime * result + (mol == null ? 0 : mol.hashCode());
		return result;
	}

	/**
	 * Set the value of atom.
	 * 
	 * @param value
	 *            allowed object is {@link Atom }
	 * 
	 */
	public void setAtom(Atom value) {
		this.atom = value;
	}

	/**
	 * Set the value of mol.
	 * 
	 * @param value
	 *            allowed object is {@link BigInteger }
	 * 
	 */
	public void setMol(BigInteger value) {
		this.mol = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SubstanceContent [getAtom()=");
		builder.append(getAtom());
		builder.append(", getMol()=");
		builder.append(getMol());
		builder.append("]");
		return builder.toString();
	}

}
