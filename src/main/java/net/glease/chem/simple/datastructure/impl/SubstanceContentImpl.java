
package net.glease.chem.simple.datastructure.impl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import net.glease.chem.simple.datastructure.Atom;
import net.glease.chem.simple.datastructure.SubstanceContent;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubstanceContent")
public class SubstanceContentImpl implements Serializable, SubstanceContent {

	private final static long serialVersionUID = 1L;
	@XmlAttribute(name = "atom", namespace = "http://glease.net/chem/simple/DataStructure", required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Atom atom;
	@XmlAttribute(name = "mol")
	@XmlSchemaType(name = "unsignedShort")
	protected int mol = 1;

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
		SubstanceContentImpl other = (SubstanceContentImpl) obj;
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

}
