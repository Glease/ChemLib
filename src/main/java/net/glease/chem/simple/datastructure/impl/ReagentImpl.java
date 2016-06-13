package net.glease.chem.simple.datastructure.impl;

import static net.glease.chem.simple.datastructure.impl.ScopeUtils.bindSub;
import static net.glease.chem.simple.datastructure.impl.ScopeUtils.orphan;

import java.awt.Color;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.Reagent;
import net.glease.chem.simple.datastructure.ReagentState;
import net.glease.chem.simple.datastructure.Substance;

public class ReagentImpl implements Serializable, Reagent {

	private final static long serialVersionUID = 1L;
	public static Reagent copyOf(final Reagent o) {
		ReagentImpl r = new ReagentImpl();
		r.color = o.getColor();
		r.concentration = o.getConcentration();
		r.id = o.getId();
		r.name = o.getName();
		r.solvent = o.getSolvent();
		r.state = o.getState();
		r.substance = SubstanceImpl.copyOf(o.getSubstance());
		return r;
	}
	protected String id;
	protected String name;
	protected Substance substance;
	protected Substance solvent;
	protected double concentration = 100d;
	protected ReagentState state;

	protected Color color;

	@Override
	public boolean bind(final ChemDatabase newScope) {
		if (id == null)
			throw new IllegalStateException("Must set an id before binding.");
		Set<Substance> added = bindSub(Arrays.asList(substance, solvent).stream()
				.map(s-> s.scope() == null ? s : SubstanceImpl.copyOf(s)), this, newScope);

		try {
			return Reagent.super.bind(newScope);
		} catch (Exception e) {
			orphan(added);
			throw e;
		}
	}

	@Override
	public Reagent copy() {
		return copyOf(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Reagent))
			return false;
		Reagent other = (Reagent) obj;
		if (scope() == null || scope() != other.scope())
			return false;
		return id.equals(other.getId());
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public double getConcentration() {
		return concentration;
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
	public Substance getSolvent() {
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
		result = prime * result + (scope() == null ? 0 : scope().hashCode());
		result = prime * result + id.hashCode();
		return result;
	}

	@Override
	public void setColor(final Color value) {
		color = Objects.requireNonNull(value);
	}

	@Override
	public void setConcentration(final double value) {
		concentration = value;
	}

	@Override
	public void setId(final String value) {
		if (Objects.requireNonNull(value).isEmpty())
			throw new IllegalArgumentException("empty id");
		id = value;
	}

	@Override
	public void setName(final String value) {
		name = Objects.requireNonNull(value);
	}

	@Override
	public void setSolvent(final Substance value) {
		solvent = Objects.requireNonNull(value);
	}

	@Override
	public void setState(final ReagentState value) {
		state = Objects.requireNonNull(value);
	}

	@Override
	public void setSubstance(final Substance value) {
		substance = Objects.requireNonNull(value);
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
		builder.append("substance=");
		builder.append(substance);
		builder.append(", ");
		if (solvent != null) {
			builder.append("solvent=");
			builder.append(solvent);
			builder.append(", ");
		}
		builder.append("concentration=");
		builder.append(concentration);
		builder.append(", ");
		if (state != null) {
			builder.append("state=");
			builder.append(state);
		}
		builder.append("]");
		return builder.toString();
	}
}
