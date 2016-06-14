
package net.glease.chem.simple.datastructure.impl;

import java.io.Serializable;
import java.util.Objects;

import net.glease.chem.simple.datastructure.Atom;
import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.Substance;
import net.glease.chem.simple.datastructure.SubstanceContent;

public class SubstanceContentImpl implements SubstanceContent, Serializable {

	private final static long serialVersionUID = 1L;

	public static SubstanceContent copyOf(final SubstanceContent o) {
		SubstanceContentImpl c = new SubstanceContentImpl();
		c.atom = AtomImpl.copyOf(o.getAtom());
		c.mol = o.getMol();
		return c;
	}

	protected Atom atom;

	protected int mol = 1;

	@Override
	public boolean bind(final Substance newScope) {
		if (newScope == scope())
			return false;
		ChemDatabase cdb = newScope.scope();
		if (cdb == null || atom == null)
			return SubstanceContent.super.bind(newScope);
		Atom s = atom.scope() == null ? atom : AtomImpl.copyOf(atom);
		boolean added = s.bind(cdb);
		try {
			return SubstanceContent.super.bind(newScope);
		} catch (Exception e) {
			if (added)
				s.bind(null);
			throw e;
		}
	}

	@Override
	public SubstanceContent copy() {
		return copyOf(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SubstanceContent))
			return false;
		SubstanceContent other = (SubstanceContent) obj;
		if (scope() == null || scope() != other.scope())
			return false;
		if (atom == null) {
			if (other.getAtom() != null)
				return false;
		} else if (!atom.equals(other.getAtom()))
			return false;
		if (mol != other.getMol())
			return false;
		return true;
	}

	@Override
	public Atom getAtom() {
		return atom;
	}

	@Override
	public String getId() {
		return atom == null ? "" : atom.getId();
	}

	@Override
	public int getMol() {
		return mol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (scope() == null ? 0 : scope().hashCode());
		result = prime * result + (atom == null ? 0 : atom.hashCode());
		result = prime * result + mol;
		return result;
	}

	@Override
	public void setAtom(final Atom value) {
		atom = Objects.requireNonNull(value);
	}

	@Override
	public void setMol(final int value) {
		mol = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SubstanceContentImpl [atom=");
		builder.append(atom);
		builder.append(", mol=");
		builder.append(mol);
		builder.append("]");
		return builder.toString();
	}
}
