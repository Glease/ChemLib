package net.glease.chem.simple.datastructure;

import java.util.function.Predicate;

class EquationPredicateSusbtance implements Predicate<Equation> {
	private final Substance reagents;

	private final ReactionSide reactant;

	public EquationPredicateSusbtance(Substance reagents, ReactionSide reactant) {
		super();
		this.reagents = reagents;
		this.reactant = reactant;
	}

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
		EquationPredicateSusbtance other = (EquationPredicateSusbtance) obj;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reactant == null) ? 0 : reactant.hashCode());
		result = prime * result + ((reagents == null) ? 0 : reagents.hashCode());
		return result;
	}

	@Override
	public boolean test(Equation t) {
		return reactant.get(t).stream().anyMatch(r -> reagents.equals(r.getSubstance()));
	}

}