package net.glease.chem.simple.datastructure;

import java.util.function.Predicate;

class CountedReagentsPredicate implements Predicate<Equation> {

	private final CountedReagent[] rs;
	private final boolean all, reactant;

	public CountedReagentsPredicate(CountedReagent[] rs, boolean all, boolean reactant) {
		super();
		this.rs = rs;
		this.all = all;
		this.reactant = reactant;
	}

	@Override
	public boolean test(Equation t) {
		return (reactant ? t.getReactants() : t.getResultants()).stream().;
	}

}
