
package net.glease.chem.simple.datastructure;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * The Java class of CountedReagent complex type.
 *
 * <p>
 * The following XML Schema snipplet contains the expect content of this class.
 *
 * <pre>
 * &lt;complexType name="CountedReagent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="mol" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" default="1" />
 *       &lt;attribute name="reagent" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "CountedReagent")
public class CountedReagent {

	protected BigInteger mol;
	protected Reagent reagent;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CountedReagent)) {
			return false;
		}
		CountedReagent other = (CountedReagent) obj;
		if (mol == null) {
			if (other.mol != null) {
				return false;
			}
		} else if (!mol.equals(other.mol)) {
			return false;
		}
		if (reagent == null) {
			if (other.reagent != null) {
				return false;
			}
		} else if (!reagent.equals(other.reagent)) {
			return false;
		}
		return true;
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

	/**
	 * Get the value of reagent.
	 * 
	 * @return possible object is {@link Reagent }
	 * 
	 */
	@XmlAttribute(name = "reagent", required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	public Reagent getReagent() {
		return reagent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (mol == null ? 0 : mol.hashCode());
		result = prime * result + (reagent == null ? 0 : reagent.hashCode());
		return result;
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

	/**
	 * Set the value of reagent.
	 * 
	 * @param value
	 *            allowed object is {@link Reagent }
	 * 
	 */
	public void setReagent(Reagent value) {
		this.reagent = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CountedReagent [hashCode()=");
		builder.append(hashCode());
		builder.append(", getMol()=");
		builder.append(getMol());
		builder.append(", getReagent()=");
		builder.append(getReagent());
		builder.append("]");
		return builder.toString();
	}

}
