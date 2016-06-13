package net.glease.chem.simple.datastructure.impl;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

import net.glease.chem.simple.datastructure.Atom;
import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.ChemDatabaseFinder;
import net.glease.chem.simple.datastructure.Reaction;
import net.glease.chem.simple.datastructure.ReactionComponent;
import net.glease.chem.simple.datastructure.Reagent;
import net.glease.chem.simple.datastructure.ReagentState;
import net.glease.chem.simple.datastructure.Resultant;
import net.glease.chem.simple.datastructure.Substance;
import net.glease.chem.simple.datastructure.SubstanceContent;

public class ChemDatabaseFinderImpl implements ChemDatabaseFinder {

	public class AtomFinderImpl implements AtomFinder {

		private Predicate<Atom> p;

		private int index = -1, molMass = -1;

		private AtomFinderImpl() {
			super();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.AtomFinder#find()
		 */
		@Override
		public Set<Atom> find() {
			return find(db.getAtoms().values().stream());
		}

		private Set<Atom> find(Stream<Atom> s) {
			if (index != -1)
				s.filter(e -> e.getIndex() == index);
			if (molMass != -1)
				s.filter(e -> e.getMolMass() == molMass);
			if (p != null)
				s.filter(p);
			return s.collect(immutableSetCollector());
		}

		@Override
		public Set<Atom> parallelFind() {
			return find(db.getAtoms().values().parallelStream());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.AtomFinder#unique()
		 */
		@Override

		public Atom unique() {
			return unwrap(find());
		}

		@Override
		public AtomFinder where(Predicate<Atom> filter) {
			p = p.and(filter);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.glease.chem.simple.datastructure.impl.AtomFinder#withIndex(int)
		 */
		@Override
		public AtomFinder withIndex(int index) {
			checkState(this.index, "index");
			this.index = validate(index, "index");
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.glease.chem.simple.datastructure.impl.AtomFinder#withMolMass(int)
		 */
		@Override
		public AtomFinder withMolMass(int molMass) {
			checkState(this.molMass, "molMass");
			this.index = validate(molMass, "molMass");
			return this;
		}
	}

	public class EquationFinderImpl implements ReactionFinder {
		private Predicate<Reaction> p;

		private EquationFinderImpl() {
			super();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReactionFinder#find()
		 */
		@Override
		public Set<Reaction> find() {
			return find(db.getReactions().stream());
		}

		private Set<Reaction> find(Stream<Reaction> s) {
			return s.filter(p == null ? e -> true : p).collect(immutableSetCollector());
		}

		@Override
		public Set<Reaction> parallelFind() {
			return find(db.getReactions().parallelStream());
		}

		@Override
		public ReactionFinder where(Predicate<Reaction> next) {
			p = p == null ? next : p.and(next);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReactionFinder#
		 * withAllCatalysts(net.glease.chem.simple.datastructure.Reagent)
		 */
		@Override
		public ReactionFinder withAllCatalysts(Reagent... catalysts) {
			List<Reagent> l = Arrays.asList(catalysts);
			return where(e -> e.getCatalysts().containsAll(l));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReactionFinder#
		 * withAllComponents(net.glease.chem.simple.datastructure.
		 * ReactionComponent<?>)
		 */
		@Override
		public ReactionFinder withAllComponents(ReactionComponent<?>... comp) {
			List<ReactionComponent<?>> rs = Arrays.asList(comp);
			return where(t -> t.getAllReactionComponents().parallel().allMatch(rs::contains));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReactionFinder#
		 * withAllConditions(java.lang.String)
		 */
		@Override
		public ReactionFinder withAllConditions(String... conditions) {
			List<String> l = Arrays.asList(conditions);
			return where(e -> e.getConditions().containsAll(l));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReactionFinder#
		 * withAllSubstances(java.util.Map)
		 */
		@Override
		public ReactionFinder withAllSubstances(Map<ReactionSide, Set<Substance>> ss) {
			if (ss.size() == 0)
				return this;

			return where(t -> {
				for (ReactionSide rs : values.values()) {
					Set<Substance> s = ss.get(rs);
					if (s.isEmpty())
						continue;
					if (!s.containsAll(rs.get(t)))
						return false;
				}
				return true;
			});
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReactionFinder#
		 * withAnyCatalysts(net.glease.chem.simple.datastructure.Reagent)
		 */
		@Override
		public ReactionFinder withAnyCatalysts(Reagent... catalysts) {
			List<Reagent> l = Arrays.asList(catalysts);
			return where(e -> e.getCatalysts().stream().anyMatch(l::contains));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReactionFinder#
		 * withAnyComponents(net.glease.chem.simple.datastructure.
		 * ReactionComponent<?>)
		 */
		@Override
		public ReactionFinder withAnyComponents(ReactionComponent<?>... comp) {
			List<ReactionComponent<?>> rs = Arrays.asList(comp);
			return where(t -> t.getAllReactionComponents().parallel().anyMatch(rs::contains));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReactionFinder#
		 * withAnyConditions(java.lang.String)
		 */
		@Override
		public ReactionFinder withAnyConditions(String... conditions) {
			List<String> l = Arrays.asList(conditions);
			return where(e -> e.getConditions().stream().anyMatch(l::contains));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReactionFinder#
		 * withAnySubstances(java.util.Map)
		 */
		@Override
		public ReactionFinder withAnySubstances(Map<ReactionSide, Set<Substance>> ss) {
			if (ss.size() == 0)
				return this;

			return where(t -> {
				for (ReactionSide rs : values.values()) {
					Set<Substance> s = ss.get(rs);
					if (s.isEmpty())
						continue;
					if (!s.containsAll(rs.get(t)))
						return false;
				}
				return true;
			});
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.glease.chem.simple.datastructure.impl.ReactionFinder#withCatalyst
		 * (net.glease.chem.simple.datastructure.Reagent)
		 */
		@Override
		public ReactionFinder withCatalyst(Reagent catalyst) {
			return where(e -> e.getCatalysts().contains(catalyst));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReactionFinder#
		 * withComponent(net.glease.chem.simple.datastructure.ReactionComponent<?>)
		 */
		@Override
		public ReactionFinder withComponent(ReactionComponent<?> comp) {
			return where(comp instanceof Resultant ? e -> e.getResultants().contains(comp)
					: e -> e.getReactants().contains(comp));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReactionFinder#
		 * withCondition(java.lang.String)
		 */
		@Override
		public ReactionFinder withCondition(String condition) {
			return where(e -> e.getConditions().contains(condition));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReactionFinder#
		 * withSubstance(net.glease.chem.simple.datastructure.impl.
		 * ChemDatabaseFinderImpl.ReactionSide,
		 * net.glease.chem.simple.datastructure.Substance)
		 */
		@Override
		public ReactionFinder withSubstance(ReactionSide side, Substance s) {
			return where(t -> side.get(t).stream().anyMatch(r -> s.equals(r.getSubstance())));
		}
	}

	public class ReagentFinderImpl implements ReagentFinder {

		private Substance s1;
		private Substance s2;
		private String name;
		private ReagentState state;

		Predicate<Reagent> p;

		private ReagentFinderImpl() {
			super();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReagentFinder#find()
		 */
		@Override
		public Set<Reagent> find() {
			return find(db.getReagents().values().stream());
		}

		private Set<Reagent> find(Stream<Reagent> s) {
			if (name != null)
				s.filter(r -> name.equals(r.getName()));
			if (s1 != null)
				s.filter(r -> s1.equals(r.getSubstance()));
			if (s2 != null)
				s.filter(r -> s2.equals(r.getSolvent()));
			if (state != null)
				s.filter(r -> state.equals(r.getState()));
			if (p != null)
				s.filter(p);
			return s.collect(immutableSetCollector());
		}

		@Override
		public Set<Reagent> parallelFind() {
			return find(db.getReagents().values().parallelStream());
		}

		@Override
		public ReagentFinder where(Predicate<Reagent> filter) {
			p = p == null ? filter : p.and(filter);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.glease.chem.simple.datastructure.impl.ReagentFinder#withName(java
		 * .lang.String)
		 */
		@Override
		public ReagentFinder withName(String s) {
			name = s;
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.glease.chem.simple.datastructure.impl.ReagentFinder#withSolvent(
		 * net.glease.chem.simple.datastructure.Substance)
		 */
		@Override
		public ReagentFinder withSolvent(Substance s) {
			s2 = s;
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.glease.chem.simple.datastructure.impl.ReagentFinder#withState(net
		 * .glease.chem.simple.datastructure.ReagentState)
		 */
		@Override
		public ReagentFinder withState(ReagentState s) {
			state = s;
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.glease.chem.simple.datastructure.impl.ReagentFinder#withSubstance
		 * (net.glease.chem.simple.datastructure.Substance)
		 */
		@Override
		public ReagentFinder withSubstance(Substance s) {
			s1 = s;
			return this;
		}

	}

	public class SubstanceFinderImpl implements SubstanceFinder {
		private Predicate<Substance> p;
		private Set<SubstanceContent> scs;

		private SubstanceFinderImpl() {
			super();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.glease.chem.simple.datastructure.impl.SubstanceFinder#containsAll
		 * (net.glease.chem.simple.datastructure.SubstanceContent)
		 */
		@Override
		public SubstanceFinder containsAll(SubstanceContent... contents) {
			if (scs == null)
				scs = new HashSet<>();
			for (SubstanceContent content : contents) {
				scs.add(content);
			}
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.glease.chem.simple.datastructure.impl.SubstanceFinder#containsAny
		 * (net.glease.chem.simple.datastructure.SubstanceContent)
		 */
		@Override
		public SubstanceFinder containsAny(SubstanceContent... contents) {
			return where(e -> e.getContent().stream().anyMatch(Arrays.asList(contents)::contains));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.SubstanceFinder#find()
		 */
		@Override
		public Set<Substance> find() {
			return find(db.getSubstances().values().stream());
		}

		private Set<Substance> find(Stream<Substance> s) {
			if (p != null)
				s.filter(p);
			if (scs != null)
				s.filter(a -> a.getContent().stream().allMatch(scs::contains));
			return s.collect(immutableSetCollector());
		}

		@Override
		public Set<Substance> parallelFind() {
			return find(db.getSubstances().values().parallelStream());
		}

		@Override
		public SubstanceFinder where(Predicate<Substance> next) {
			p = p == null ? next : p.and(next);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.glease.chem.simple.datastructure.impl.SubstanceFinder#withName(
		 * java.lang.String)
		 */
		@Override
		public SubstanceFinder withName(String name) {
			return where(s -> s.getName().equals(name));
		}
	}

	static Map<String, ReactionSide> values;

	static {
		try {
			Class.forName(ReactionSide.class.getName());
		} catch (ClassNotFoundException e) {
			throw new Error();
		}
	}

	private static void checkState(int i, String name) {
		if (i != -1)
			throw new IllegalStateException(name + " is already set");
	}

	private static <T> Collector<T, ?, Set<T>> immutableSetCollector() {
		return collectingAndThen(toSet(), Collections::unmodifiableSet);
	}

	public static void setReactionSideValues(Map<String, ReactionSide> values) {
		if (ChemDatabaseFinderImpl.values != null)
			throw new IllegalStateException("Illegal state!");
		ChemDatabaseFinderImpl.values = values;
	}

	private static <T> T unwrap(Set<T> set) {
		switch (set.size()) {
		case 0:
			return null;
		case 1:
			return set.iterator().next();
		default:
			throw new IllegalStateException("multiple results found!");
		}
	}

	private static int validate(int i, String name) {
		if (i < 0)
			throw new IllegalArgumentException(name + " is negative");
		return i;
	}

	private final ChemDatabase db;

	ChemDatabaseFinderImpl(ChemDatabase db) {
		super();
		this.db = db;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.glease.chem.simple.datastructure.impl.ChemDatabaseFinder#findAtom()
	 */
	@Override
	public AtomFinder findAtom() {
		return new AtomFinderImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.glease.chem.simple.datastructure.impl.ChemDatabaseFinder#findEquation
	 * ()
	 */
	@Override
	public ReactionFinder findReaction() {
		return new EquationFinderImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.glease.chem.simple.datastructure.impl.ChemDatabaseFinder#findReagent(
	 * )
	 */
	@Override
	public ReagentFinder findReagent() {
		return new ReagentFinderImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.glease.chem.simple.datastructure.impl.ChemDatabaseFinder#
	 * findSusbtance()
	 */
	@Override
	public SubstanceFinder findSusbtance() {
		return new SubstanceFinderImpl();
	}
}
