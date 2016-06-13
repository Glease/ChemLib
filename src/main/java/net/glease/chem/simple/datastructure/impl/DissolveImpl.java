package net.glease.chem.simple.datastructure.impl;

import java.io.Serializable;
import java.util.Objects;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.Dissolve;
import net.glease.chem.simple.datastructure.Reagent;
import net.glease.chem.simple.datastructure.Substance;

public class DissolveImpl implements Serializable, Dissolve {

	private final static long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public static Dissolve copyOf(final Dissolve o) {
		DissolveImpl d = new DissolveImpl();
		d.s2TFunction = o.getS2TFunction();
		d.solvent = ReagentImpl.copyOf(o.getSolvent());
		return d;
	}
	protected Reagent solvent;

	protected String s2TFunction;

	@Override
	public boolean bind(final Substance newScope) {
		if (newScope == scope())
			return false;
		ChemDatabase cdb = newScope.scope();
		if (cdb == null || solvent == null)
			return Dissolve.super.bind(newScope);
		Reagent s = solvent.scope() == null ? solvent : ReagentImpl.copyOf(solvent);
		boolean added = s.bind(cdb);
		try {
			return Dissolve.super.bind(newScope);
		} catch (Exception e) {
			if (added)
				s.bind(null);
			throw e;
		}
	}

	@Override
	public Dissolve copy() {
		return copyOf(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Dissolve))
			return false;
		Dissolve other = (Dissolve) obj;
		if (scope() == null || scope() != other.scope())
			return false;
		if (!s2TFunction.equals(other.getS2TFunction()))
			return false;
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
		result = prime * result + (scope() == null ? 0 : scope().hashCode());
		result = prime * result + s2TFunction.hashCode();
		result = prime * result + solvent.hashCode();
		return result;
	}

	@Override
	public void setS2TFunction(final String value) {
		s2TFunction = Objects.requireNonNull(value);
	}

	@Override
	public void setSolvent(final Reagent value) {
		solvent = Objects.requireNonNull(value);
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
}