
package net.glease.chem.simple.datastructure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * <p>
 * The Java class of Atom complex type.
 *
 * <p>
 * The following XML Schema snipplet contains the expect content of this class.
 *
 * <pre>
 * &lt;complexType name="Atom">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="localizedName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="symbol" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="molMass" use="required" type="{http://www.w3.org/2001/XMLSchema}float" />
 *       &lt;attribute name="index" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Atom")
public class Atom {

	protected String localizedName;

	protected String symbol;

	protected float molMass;

	protected short index;
	protected String id;
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Atom)) {
			return false;
		}
		Atom other = (Atom) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	/**
	 * Get the value of id.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	@XmlAttribute(name = "id", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	public String getId() {
		return id;
	}
	/**
	 * Get the value of index.
	 *
	 */
	@XmlAttribute(name = "index", required = true)
	@XmlSchemaType(name = "unsignedByte")
	public short getIndex() {
		return index;
	}

	/**
	 * Get the value of localizedName.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	@XmlAttribute(name = "localizedName")
	public String getLocalizedName() {
		return localizedName;
	}

	/**
	 * Get the value of molMass.
	 *
	 */
	@XmlAttribute(name = "molMass", required = true)
	public float getMolMass() {
		return molMass;
	}

	/**
	 * Get the value of symbol.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	@XmlAttribute(name = "symbol")
	public String getSymbol() {
		return symbol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Set the value of id.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 *
	 */
	public void setId(String value) {
		this.id = value;
	}

	/**
	 * Set the value of index.
	 *
	 */
	public void setIndex(short value) {
		this.index = value;
	}

	/**
	 * Set the value of localizedName.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 *
	 */
	public void setLocalizedName(String value) {
		this.localizedName = value;
	}

	/**
	 * Set the value of molMass.
	 *
	 */
	public void setMolMass(float value) {
		this.molMass = value;
	}

	/**
	 * Set the value of symbol.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 *
	 */
	public void setSymbol(String value) {
		this.symbol = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Atom [getLocalizedName()=");
		builder.append(getLocalizedName());
		builder.append(", getSymbol()=");
		builder.append(getSymbol());
		builder.append(", getMolMass()=");
		builder.append(getMolMass());
		builder.append(", getIndex()=");
		builder.append(getIndex());
		builder.append(", getId()=");
		builder.append(getId());
		builder.append("]");
		return builder.toString();
	}

}
