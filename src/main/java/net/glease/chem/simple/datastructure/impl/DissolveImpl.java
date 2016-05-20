package net.glease.chem.simple.datastructure.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import net.glease.chem.simple.datastructure.Dissolve;
import net.glease.chem.simple.datastructure.Reagent;
import net.glease.chem.simple.datastructure.Substance;

public class DissolveImpl implements Serializable, Dissolve {

	private final static long serialVersionUID = 1L;

	protected Reagent solvent;
	protected String s2TFunction;

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
		if (!(obj instanceof Dissolve)) {
			return false;
		}
		Dissolve other = (Dissolve) obj;
		if (scope == null || scope != other.scope()) {
			return false;
		}
		if (!s2TFunction.equals(other.getS2TFunction())) {
			return false;
		}
		return solvent.equals(other.getSolvent());
	}

	@Override
	public String getId() {
		return solvent.getId();
	}

	@Override
	public String getS2TFunction() {
		return s2TFunction;
	}

	@Override
	public Reagent getSolvent() {
		return solvent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((scope() == null) ? 0 : scope().hashCode());
		result = prime * result + s2TFunction.hashCode();
		result = prime * result + solvent.hashCode();
		return result;
	}

	@Override
	public Substance scope() {
		return scope;
	}

	@Override
	public void setS2TFunction(String value) {
		this.s2TFunction = Objects.requireNonNull(value);
	}

	@Override
	public void setSolvent(Reagent value) {
		this.solvent = Objects.requireNonNull(value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DissolveImpl [solvent=");
		builder.append(solvent);
		builder.append(", s2TFunction=");
		builder.append(s2TFunction);
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