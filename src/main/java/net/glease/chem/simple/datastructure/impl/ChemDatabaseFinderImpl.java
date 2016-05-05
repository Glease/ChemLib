package net.glease.chem.simple.datastructure.impl;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;
import static org.eclipse.jdt.annotation.DefaultLocation.PARAMETER;
import static org.eclipse.jdt.annotation.DefaultLocation.RETURN_TYPE;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

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

@XmlTransient
@NonNullByDefault({ PARAMETER, RETURN_TYPE })
public class ChemDatabaseFinderImpl implements ChemDatabaseFinder {

	static Map<String, ReactionSide> values;

	static {
		try {
			Class.forName(ReactionSide.class.getName());
		} catch (ClassNotFoundException e) {
			throw new Error();
		}
	}

	public static void setReactionSideValues(Map<String, ReactionSide> values) {
		if (ChemDatabaseFinderImpl.values != null)
			throw new IllegalStateException("Illegal state!");
		ChemDatabaseFinderImpl.values = values;
	}

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
			Stream<Atom> s = db.getAtoms().stream();
			if (index != -1)
				s.filter(e -> e.getIndex() == index);
			if (molMass != -1)
				s.filter(e -> e.getMolMass() == molMass);
			return s.filter(p).collect(immutableSetCollector());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.AtomFinder#unique()
		 */
		@Override
		@Nullable
		public Atom unique() {
			Set<Atom> find = find();
			switch (find.size()) {
			case 0:
				return null;
			case 1:
				return find.iterator().next();
			default:
				throw new IllegalStateException("multiple results found!");
			}
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
			return db.getReactions().parallelStream().filter(p == null ? e -> true : p)
					.collect(immutableSetCollector());
		}

		private ReactionFinder match(Predicate<Reaction> next) {
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
			return match(e -> e.getCatalysts().containsAll(l));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReactionFinder#
		 * withAllComponents(net.glease.chem.simple.datastructure.
		 * ReactionComponent)
		 */
		@Override
		public ReactionFinder withAllComponents(ReactionComponent... comp) {
			List<ReactionComponent> rs = Arrays.asList(comp);
			return match(t -> t.getAllEquationComponents().parallelStream().allMatch(rs::contains));
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
			return match(e -> e.getConditions().containsAll(l));
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

			return match(t -> {
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
			return match(e -> e.getCatalysts().stream().anyMatch(l::contains));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReactionFinder#
		 * withAnyComponents(net.glease.chem.simple.datastructure.
		 * ReactionComponent)
		 */
		@Override
		public ReactionFinder withAnyComponents(ReactionComponent... comp) {
			List<ReactionComponent> rs = Arrays.asList(comp);
			return match(t -> t.getAllEquationComponents().parallelStream().anyMatch(rs::contains));
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
			return match(e -> e.getConditions().stream().anyMatch(l::contains));
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

			return match(t -> {
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
			return match(e -> e.getCatalysts().contains(catalyst));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.ReactionFinder#
		 * withComponent(net.glease.chem.simple.datastructure.ReactionComponent)
		 */
		@Override
		public ReactionFinder withComponent(ReactionComponent comp) {
			return match(comp instanceof Resultant ? e -> e.getResultants().contains(comp)
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
			return match(e -> e.getConditions().contains(condition));
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
			return match(t -> side.get(t).stream().anyMatch(r -> s.equals(r.getSubstance())));
		}
	}

	public class ReagentFinderImpl implements ReagentFinder {

		private Substance s1;
		private Substance s2;
		private String name;
		private ReagentState state;

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
			Stream<Reagent> s = db.getReagents().stream();
			if (name != null)
				s.filter(r -> name.equals(r.getName()));
			if (s1 != null)
				s.filter(r -> s1.equals(r.getSubstance()));
			if (s2 != null)
				s.filter(r -> s2.equals(r.getSolvent()));
			if (state != null)
				s.filter(r -> state.equals(r.getState()));

			return s.collect(immutableSetCollector());
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
			return match(e -> e.getAtom().stream().anyMatch(Arrays.asList(contents)::contains));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.glease.chem.simple.datastructure.impl.SubstanceFinder#find()
		 */
		@Override
		public Set<Substance> find() {
			Stream<Substance> s = db.getSubstances().parallelStream();
			if (p != null)
				s.filter(p);
			if (scs != null)
				s.filter(a -> a.getAtom().stream().allMatch(scs::contains));
			return s.collect(immutableSetCollector());
		}

		private SubstanceFinder match(Predicate<Substance> next) {
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
			return match(s -> s.getName().equals(name));
		}
	}

	private static void checkState(int i, String name) {
		if (i != -1)
			throw new IllegalStateException(name + " is already set");
	}

	@SuppressWarnings("null")
	private static <T> Collector<T, ?, @NonNull Set<T>> immutableSetCollector() {
		return collectingAndThen(toSet(), Collections::unmodifiableSet);
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
	public ReactionFinder findEquation() {
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
