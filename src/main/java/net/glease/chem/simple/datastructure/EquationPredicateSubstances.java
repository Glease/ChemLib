package net.glease.chem.simple.datastructure;

import java.util.Arrays;
import java.util.function.Predicate;

class EquationPredicateSubstances implements Predicate<Equation> {
	private final Substance[] reagents;
	private final ReactionSide[] reactant;
	private final boolean all;

	public EquationPredicateSubstances(ReactionSide[] reactant, Substance[] reagents, boolean all) {
		super();
		this.reagents = reagents;
		this.reactant = reactant;
		this.all = all;
	}


	@Override
	public boolean test(Equation t) {
		if(all) {
			for (int i = 0; i < reactant.length; i++) {
				if(!reactant[i].get(t).contains(reagents[i]))
					return false;
			}
			return true;
		} else {
			for (int i = 0; i < reactant.length; i++) {
				if(reactant[i].get(t).contains(reagents[i]))
					return true;
			}
			return false;
		}
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (all ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(reactant);
		result = prime * result + Arrays.hashCode(reagents);
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		EquationPredicateSubstances other = (EquationPredicateSubstances) obj;
		if (all != other.all) {
			return false;
		}
		if (!Arrays.equals(reactant, other.reactant)) {
			return false;
		}
		if (!Arrays.equals(reagents, other.reagents)) {
			return false;
		}
		return true;
	}

}
