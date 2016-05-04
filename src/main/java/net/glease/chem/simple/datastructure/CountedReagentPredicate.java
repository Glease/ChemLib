package net.glease.chem.simple.datastructure;

import java.util.function.Predicate;

class CountedReagentPredicate implements Predicate<Equation> {
	private final EquationComponent r;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((r == null) ? 0 : r.hashCode());
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
		if (!(obj instanceof CountedReagentPredicate)) {
			return false;
		}
		CountedReagentPredicate other = (CountedReagentPredicate) obj;
		if (r == null) {
			if (other.r != null) {
				return false;
			}
		} else if (!r.equals(other.r)) {
			return false;
		}
		return true;
	}

	public CountedReagentPredicate(EquationComponent r) {
		super();
		this.r = r;
	}

	@Override
	public boolean test(Equation t) {
		return t.getAllEquationComponents().contains(r);
	}
	
}