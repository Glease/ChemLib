package net.glease.chem.simple.datastructure;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

class EquationPredicateComp implements Predicate<Equation> {

	private final List<EquationComponent> rs;
	private final boolean all;

	public EquationPredicateComp(EquationComponent[] rs, boolean all) {
		super();
		this.rs = Arrays.asList(rs);
		this.all = all;
	}

	@Override
	public boolean test(Equation t) {
		Stream<EquationComponent> s = t.getAllEquationComponents().parallelStream();
		
		if(all)
			return s.allMatch(rs::contains);
		else
			return s.anyMatch(rs::contains);
	}

}
