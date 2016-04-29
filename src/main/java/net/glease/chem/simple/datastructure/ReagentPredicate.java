package net.glease.chem.simple.datastructure;

import java.util.function.Predicate;

class ReagentPredicate implements Predicate<Equation> {
	private final Reagent reagents;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (reactant ? 1231 : 1237);
		result = prime * result + ((reagents == null) ? 0 : reagents.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ReagentPredicate)) {
			return false;
		}
		ReagentPredicate other = (ReagentPredicate) obj;
		if (reactant != other.reactant) {
			return false;
		}
		if (reagents == null) {
			if (other.reagents != null) {
				return false;
			}
		} else if (!reagents.equals(other.reagents)) {
			return false;
		}
		return true;
	}

	public ReagentPredicate(Reagent reagents, boolean reactant) {
		super();
		this.reagents = reagents;
		this.reactant = reactant;
	}

	private final boolean reactant;

	@Override
	public boolean test(Equation t) {
		return (reactant ? t.getReactants() : t.getResultants()).stream().anyMatch(r -> reagents.equals(r.getReagent()));
	}

}