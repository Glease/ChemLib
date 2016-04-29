package net.glease.chem.simple.datastructure;

import java.util.function.Predicate;

class ReagentsPredicate implements Predicate<Equation> {
	private final Reagent[] reagents;
	private final boolean reactant, all;

	private ReagentsPredicate(Reagent[] reagents, boolean reactant, boolean all) {
		super();
		this.reagents = reagents;
		this.reactant = reactant;
		this.all = all;
	}


	@Override
	public boolean test(Equation t) {
		// TODO Auto-generated method stub
		return false;
	}

}
