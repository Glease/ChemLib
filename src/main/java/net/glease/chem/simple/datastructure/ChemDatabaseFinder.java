package net.glease.chem.simple.datastructure;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

import java.util.Set;
import java.util.function.Predicate;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableSet;

public class ChemDatabaseFinder {
	private final ChemDatabase db;

	protected ChemDatabaseFinder(ChemDatabase db) {
		super();
		this.db = db;
	}

	private final LoadingCache<Predicate<Equation>, Set<Equation>> equationCache = CacheBuilder.newBuilder().weakKeys()
			.<Predicate<Equation>, Set<Equation>> weigher((k, v) -> v.size()).maximumWeight(2048).removalListener(e -> {
			}).build(new CacheLoader<Predicate<Equation>, Set<Equation>>() {
				@Override
				public Set<Equation> load(Predicate<Equation> key) throws Exception {
					// TODO Auto-generated method stub
					return db.getEquations().getEquation().parallelStream().filter(key)
							.collect(collectingAndThen(toSet(), ImmutableSet::copyOf));
				}
			});

	public Set<Equation> findEquation(Predicate<Equation> test) {
		return equationCache.getUnchecked(test);
	}

	public Set<Equation> findEquation(CountedReagent reagent, boolean reactant) {
		return findEquation(new CountedReagentPredicate(reagent, reactant));
	}

	/**
	 * 
	 * @param reactant
	 * @param all whether all given reagents should be part of reactant/resultant
	 * @param reagents
	 * @return
	 */
	public Set<Equation> findEquation(boolean reactant, boolean all, CountedReagent... reagents) {
		return findEquation(new CountedReagentsPredicate(reagents, reactant, all));
	}

	public Set<Equation> findEquation(Reagent reagent, boolean reactant) {
		return findEquation(new ReagentPredicate(reagent, reactant));
	}

	public Set<Equation> findEquation(boolean reactant, boolean all, Reagent... reagents) {
		return findEquation(new ReagentsPredicate(reagents, reactant, all));
	}
	
	void flushEquationCache() {
		equationCache.invalidateAll();
	}
}
