
package net.glease.chem.simple.datastructure.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import net.glease.chem.simple.datastructure.Atom;
import net.glease.chem.simple.datastructure.Substance;
import net.glease.chem.simple.datastructure.SubstanceContent;

public class SubstanceContentImpl implements Serializable, SubstanceContent {

	private final static long serialVersionUID = 1L;

	protected Atom atom;
	protected int mol = 1;

	protected Substance scope;

	@Override
	public void bind(Substance scope) {
		if (this.scope != null)
			this.scope.onUnbind(this);
		this.scope = scope;
		if (scope != null)
			scope.onBind(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SubstanceContent)) {
			return false;
		}
		SubstanceContent other = (SubstanceContent) obj;
		if (scope == null || scope != other.scope()) {
			return false;
		}
		if (atom == null) {
			if (other.getAtom() != null) {
				return false;
			}
		} else if (!atom.equals(other.getAtom())) {
			return false;
		}
		if (mol != other.getMol()) {
			return false;
		}
		return true;
	}

	@Override
	public Atom getAtom() {
		return atom;
	}

	@Override
	public String getId() {
		return atom.getId();
	}

	@Override
	public int getMol() {
		return mol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((scope() == null) ? 0 : scope().hashCode());
		result = prime * result + ((atom == null) ? 0 : atom.hashCode());
		result = prime * result + mol;
		return result;
	}

	@Override
	public Substance scope() {
		return scope;
	}

	@Override
	public void setAtom(Atom value) {
		this.atom = Objects.requireNonNull(value);
	}

	@Override
	public void setMol(int value) {
		this.mol = value;
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

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeObject(scope());
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		bind((Substance) in.readObject());
	}
}
