
package net.glease.chem.simple.datastructure.impl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.glease.chem.simple.datastructure.Atom;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Atom")
public class AtomImpl implements Serializable, Atom {

	private final static long serialVersionUID = 1L;

	@XmlAttribute(name = "localizedName")
	protected String localizedName;
	@XmlAttribute(name = "symbol")
	protected String symbol;
	@XmlAttribute(name = "molMass", required = true)
	protected int molMass;
	@XmlAttribute(name = "index", required = true)
	@XmlSchemaType(name = "unsignedByte")
	protected short index;
	@XmlAttribute(name = "id", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AtomImpl other = (AtomImpl) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public short getIndex() {
		return index;
	}

	@Override
	public String getLocalizedName() {
		return localizedName;
	}

	@Override
	public int getMolMass() {
		return molMass;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public void setId(String value) {
		this.id = value;
	}

	@Override
	public void setIndex(short value) {
		this.index = value;
	}

	@Override
	public void setLocalizedName(String value) {
		this.localizedName = value;
	}

	@Override
	public void setMolMass(int value) {
		this.molMass = value;
	}

	@Override
	public void setSymbol(String value) {
		this.symbol = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AtomImpl [");
		if (localizedName != null) {
			builder.append("localizedName=");
			builder.append(localizedName);
			builder.append(", ");
		}
		if (symbol != null) {
			builder.append("symbol=");
			builder.append(symbol);
			builder.append(", ");
		}
		builder.append("molMass=");
		builder.append(molMass);
		builder.append(", ");
		builder.append("index=");
		builder.append(index);
		builder.append(", ");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
		}
		builder.append("]");
		return builder.toString();
	}

}
