
package net.glease.chem.simple.datastructure.impl;

import java.io.Serializable;
import java.util.Objects;

import net.glease.chem.simple.datastructure.Reaction;
import net.glease.chem.simple.datastructure.ReactionComponent;
import net.glease.chem.simple.datastructure.ReagentState;
import net.glease.chem.simple.datastructure.Substance;

public abstract class ReactionComponentImpl implements Serializable, ReactionComponent {

	private final static long serialVersionUID = 1L;

	protected int mol = 1;
	protected Substance substance;
	protected ReagentState state = ReagentState.POWDER;

	protected Reaction scope;

	@Override
	public void bind(Reaction scope) {
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
		if (!(obj instanceof ReactionComponent)) {
			return false;
		}
		ReactionComponent other = (ReactionComponent) obj;
		if (scope == null || scope != other.scope()) {
			return false;
		}
		if (mol != other.getMol()) {
			return false;
		}
		if (state != other.getState()) {
			return false;
		}
		if (substance == null) {
			if (other.getSubstance() != null) {
				return false;
			}
		} else if (!substance.equals(other.getSubstance())) {
			return false;
		}
		return true;
	}

	@Override
	public String getId() {
		return substance.getId();
	}

	@Override
	public int getMol() {
		return mol;
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
		result = prime * result + mol;
		result = prime * result + state.hashCode();
		result = prime * result + substance.hashCode();
		return result;
	}

	@Override
	public Reaction scope() {
		return scope;
	}

	@Override
	public void setMol(int value) {
		this.mol = value;
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
		builder.append("ReactionComponentImpl [");
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
