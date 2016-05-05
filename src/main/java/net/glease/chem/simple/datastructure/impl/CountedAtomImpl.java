
package net.glease.chem.simple.datastructure.impl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import net.glease.chem.simple.datastructure.Atom;
import net.glease.chem.simple.datastructure.CountedAtom;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CountedAtom")
public class CountedAtomImpl implements Serializable, CountedAtom {

	private final static long serialVersionUID = 1L;
	@XmlAttribute(name = "mol")
	protected int mol = 1;
	@XmlAttribute(name = "atom", namespace = "http://glease.net/chem/simple/DataStructure", required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Atom atom;

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
		CountedAtomImpl other = (CountedAtomImpl) obj;
		if (atom == null) {
			if (other.atom != null) {
				return false;
			}
		} else if (!atom.equals(other.atom)) {
			return false;
		}
		if (mol != other.mol) {
			return false;
		}
		return true;
	}

	@Override
	public Atom getAtom() {
		return atom;
	}

	@Override
	public int getMol() {
		return mol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atom == null) ? 0 : atom.hashCode());
		result = prime * result + mol;
		return result;
	}

	@Override
	public void setAtom(Atom value) {
		this.atom = value;
	}

	@Override
	public void setMol(Integer value) {
		this.mol = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CountedAtomImpl [");
		if (atom != null) {
			builder.append("atom=");
			builder.append(atom);
			builder.append(", ");
		}
		builder.append("getMol()=");
		builder.append(getMol());
		builder.append("]");
		return builder.toString();
	}

}
