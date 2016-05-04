
package net.glease.chem.simple.datastructure.impl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import net.glease.chem.simple.datastructure.EquationComponent;
import net.glease.chem.simple.datastructure.ReagentState;
import net.glease.chem.simple.datastructure.Substance;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EquationComponent")
@XmlSeeAlso({ ReactantImpl.class, ResultantImpl.class })
public abstract class EquationComponentImpl implements Serializable, EquationComponent {

	private final static long serialVersionUID = 1L;
	@XmlAttribute(name = "mol")
	@XmlSchemaType(name = "unsignedShort")
	protected Integer mol;
	@XmlAttribute(name = "substance", namespace = "http://glease.net/chem/simple/DataStructure", required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Substance substance;
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
		EquationComponentImpl other = (EquationComponentImpl) obj;
		if (mol == null) {
			if (other.mol != null) {
				return false;
			}
		} else if (!mol.equals(other.mol)) {
			return false;
		}
		if (state != other.state) {
			return false;
		}
		if (substance == null) {
			if (other.substance != null) {
				return false;
			}
		} else if (!substance.equals(other.substance)) {
			return false;
		}
		return true;
	}

	@Override
	public int getMol() {
		if (mol == null) {
			return 1;
		} else {
			return mol;
		}
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
		result = prime * result + ((mol == null) ? 0 : mol.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((substance == null) ? 0 : substance.hashCode());
		return result;
	}

	@Override
	public void setMol(Integer value) {
		this.mol = value;
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
		builder.append("EquationComponentImpl [");
		if (substance != null) {
			builder.append("substance=");
			builder.append(substance);
			builder.append(", ");
		}
		if (state != null) {
			builder.append("state=");
			builder.append(state);
			builder.append(", ");
		}
		builder.append("getMol()=");
		builder.append(getMol());
		builder.append("]");
		return builder.toString();
	}

}
