
package net.glease.chem.simple.datastructure.impl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.glease.chem.simple.datastructure.FloatAdapter;
import net.glease.chem.simple.datastructure.Reagent;
import net.glease.chem.simple.datastructure.ReagentState;
import net.glease.chem.simple.datastructure.Substance;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Reagent")
public class ReagentImpl implements Serializable, net.glease.chem.simple.datastructure.Reagent {

	private final static long serialVersionUID = 1L;
	@XmlAttribute(name = "id", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;
	@XmlAttribute(name = "name")
	protected String name;
	@XmlAttribute(name = "substance", namespace = "http://glease.net/chem/simple/DataStructure", required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Substance substance;

	@XmlAttribute(name = "solvent", namespace = "http://glease.net/chem/simple/DataStructure")
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Reagent solvent;

	@XmlAttribute(name = "concentration")
	@XmlJavaTypeAdapter(FloatAdapter.class)
	protected Float concentration;

	@XmlAttribute(name = "state")
	protected ReagentState state;

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
		ReagentImpl other = (ReagentImpl) obj;
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
	public float getConcentration() {
		if (concentration == null) {
			return new FloatAdapter().unmarshal("100");
		} else {
			return concentration;
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Reagent getSolvent() {
		return solvent;
	}

	@Override
	public ReagentState getState() {
		return state;
	}

	@Override
	public Substance getSubstance() {
		return substance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public void setConcentration(Float value) {
		this.concentration = value;
	}

	@Override
	public void setId(String value) {
		this.id = value;
	}

	@Override
	public void setName(String value) {
		this.name = value;
	}

	@Override
	public void setSolvent(Reagent value) {
		this.solvent = value;
	}

	@Override
	public void setState(ReagentState value) {
		this.state = value;
	}

	@Override
	public void setSubstance(Substance value) {
		this.substance = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReagentImpl [");
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (substance != null) {
			builder.append("substance=");
			builder.append(substance);
			builder.append(", ");
		}
		if (solvent != null) {
			builder.append("solvent=");
			builder.append(solvent);
			builder.append(", ");
		}
		if (concentration != null) {
			builder.append("concentration=");
			builder.append(concentration);
			builder.append(", ");
		}
		if (state != null) {
			builder.append("state=");
			builder.append(state);
		}
		builder.append("]");
		return builder.toString();
	}

}
