
package net.glease.chem.simple.datastructure.impl;

import java.awt.Color;
import java.io.Serializable;
import java.util.Objects;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.Reagent;
import net.glease.chem.simple.datastructure.ReagentState;
import net.glease.chem.simple.datastructure.Substance;

public class ReagentImpl implements Serializable, net.glease.chem.simple.datastructure.Reagent {

	private final static long serialVersionUID = 1L;
	protected String id;
	protected String name;
	protected Substance substance;
	protected Substance solvent;
	protected double concentration = 100d;
	protected ReagentState state;
	protected Color color;

	protected ChemDatabase scope;

	@Override
	public void bind(ChemDatabase scope) {
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
		if (!(obj instanceof Reagent)) {
			return false;
		}
		Reagent other = (Reagent) obj;
		if (scope != other.scope()) {
			return false;
		}
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
		result = prime * result + ((scope() == null) ? 0 : scope().hashCode());
		result = prime * result + id.hashCode();
		return result;
	}

	@Override
	public ChemDatabase scope() {
		return scope;
	}

	@Override
	public void setColor(Color value) {
		this.color = Objects.requireNonNull(value);
	}

	@Override
	public void setConcentration(double value) {
		this.concentration = value;
	}

	@Override
	public void setId(String value) {
		if (Objects.requireNonNull(value).isEmpty())
			throw new IllegalArgumentException("empty id");
		this.id = value;
	}

	@Override
	public void setName(String value) {
		this.name = Objects.requireNonNull(value);
	}

	@Override
	public void setSolvent(Substance value) {
		this.solvent = Objects.requireNonNull(value);
	}

	@Override
	public void setState(ReagentState value) {
		this.state = Objects.requireNonNull(value);
	}

	@Override
	public void setSubstance(Substance value) {
		this.substance = Objects.requireNonNull(value);
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
