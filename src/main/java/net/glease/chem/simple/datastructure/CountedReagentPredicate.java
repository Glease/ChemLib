package net.glease.chem.simple.datastructure;

import java.util.function.Predicate;

import com.google.common.reflect.TypeToken;

class CountedReagentPredicate implements Predicate<Equation> {
	private final CountedReagent r;
	private final boolean reactant;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((r == null) ? 0 : r.hashCode());
		result = prime * result + (reactant ? 1231 : 1237);
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
		if (reactant != other.reactant) {
			return false;
		}
		return true;
	}

	public CountedReagentPredicate(CountedReagent r, boolean reactant) {
		super();
		this.r = r;
		this.reactant = reactant;
	}

	@Override
	public boolean test(Equation t) {
		return (reactant ? t.getReactants() : t.getResultants()).contains(r);
	}
	
	public static void main(String[] args) {
		System.out.println(TypeToken.of(CountedReagentPredicate.class.getGenericInterfaces()[0]).getRawType().getClass());
	}
}