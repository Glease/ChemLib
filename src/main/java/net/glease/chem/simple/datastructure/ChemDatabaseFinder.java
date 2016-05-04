package net.glease.chem.simple.datastructure;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;

public class ChemDatabaseFinder {
	public class AtomFinder {

		private Predicate<Atom> p;

		private int index = -1, molMass = -1;

		private AtomFinder() {
			super();
		}

		public Set<Atom> find() {
			Stream<Atom> s = db.getAtoms().stream();
			if (index != -1)
				s.filter(e -> e.getIndex() == index);
			if (molMass != -1)
				s.filter(e -> e.getMolMass() == molMass);
			return s.filter(p).collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
		}

		public AtomFinder match(Predicate<Atom> next) {
			p = p == null ? next : p.and(next);
			return this;
		}

		/**
		 * If you are confident that you are looking for only one {@link Atom},
		 * use this. Otherwise it will bug you with an
		 * {@link IllegalStateException}.
		 * 
		 * @return the atom you want, or <code>null</code> if not found.
		 * @throws IllegalStateException
		 *             if multiple results are found.
		 */
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

		public AtomFinder withIndex(int index) {
			checkState(this.index, "index");
			this.index = validate(index, "index");
			return this;
		}

		public AtomFinder withMolMass(int molMass) {
			checkState(this.molMass, "molMass");
			this.index = validate(molMass, "molMass");
			return this;
		}
	}

	public class EquationFinder {
		private Predicate<Equation> p;
		private EquationFinder() {
			super();
		}

		public Set<Equation> find() {
			return db.getEquations().parallelStream().filter(p == null ? e -> true : p)
					.collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
		}

		public EquationFinder match(Predicate<Equation> next) {
			p = p == null ? next : p.and(next);
			return this;
		}

		public EquationFinder withCatalyst(Reagent catalyst) {
			return match(e -> e.getCatalysts().contains(catalyst));
		}

		public EquationFinder withCatalysts(boolean allOrAny, Reagent... catalysts) {
			List<Reagent> l = Arrays.asList(catalysts);
			if (allOrAny)
				return match(e -> e.getCatalysts().containsAll(l));
			else
				return match(e -> e.getCatalysts().stream().anyMatch(l::contains));
		}

		public EquationFinder withComponent(EquationComponent comp) {
			return match(comp instanceof Resultant ? e -> e.getResultants().contains(comp)
					: e -> e.getReactants().contains(comp));
		}

		public EquationFinder withComponents(boolean allOrAny, EquationComponent... comp) {
			List<EquationComponent> rs = Arrays.asList(comp);
			if (allOrAny)
				return match(t -> t.getAllEquationComponents().parallelStream().allMatch(rs::contains));
			else
				return match(t -> t.getAllEquationComponents().parallelStream().anyMatch(rs::contains));
		}

		public EquationFinder withCondition(String condition) {
			return match(e -> e.getConditions().contains(condition));
		}

		public EquationFinder withConditions(boolean allOrAny, String... conditions) {
			List<String> l = Arrays.asList(conditions);
			if (allOrAny)
				return match(e -> e.getConditions().containsAll(l));
			else
				return match(e -> e.getConditions().stream().anyMatch(l::contains));
		}

		public EquationFinder withSubstance(ReactionSide side, Substance s) {
			return match(t -> side.get(t).stream().anyMatch(r -> s.equals(r.getSubstance())));
		}

		/**
		 * I'd rather use multiple chained
		 * {@link #substance(ReactionSide, Substance)} calls than using this
		 * method if the <code>ss</code> is too small.
		 * 
		 * @param allOrAny
		 * @param ss
		 * @return
		 */
		public EquationFinder withSubstances(boolean allOrAny, SetMultimap<ReactionSide, Substance> ss) {
			if (ss.size() == 0)
				return this;

			if (allOrAny)
				return match(t -> {
					for (ReactionSide rs : ReactionSide.values) {
						Set<Substance> s = ss.get(rs);
						if (s.isEmpty())
							continue;
						if (!s.containsAll(rs.get(t)))
							return false;
					}
					return true;
				});
			else
				return match(t -> {
					for (ReactionSide rs : ReactionSide.values) {
						Set<Substance> s = ss.get(rs);
						if (s.isEmpty())
							continue;
						if (rs.get(t).stream().anyMatch(s::contains))
							return true;
					}
					return false;
				});
		}
	}

	public class SubstanceFinder {
		private Predicate<Substance> p;
		private Set<SubstanceContent> scs;

		private SubstanceFinder() {
			super();
		}

		public SubstanceFinder containsAll(SubstanceContent... contents) {
			if (scs == null)
				scs = Sets.newHashSet();
			for (SubstanceContent content : contents) {
				scs.add(content);
			}
			return this;
		}

		public Set<Substance> find() {
			Stream<Substance> s = db.getSubstances().parallelStream();
			if (p != null)
				s.filter(p);
			if (scs != null)
				s.filter(a -> a.getAtom().stream().allMatch(scs::contains));
			return s.collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
		}

		public SubstanceFinder match(Predicate<Substance> next) {
			p = p == null ? next : p.and(next);
			return this;
		}

		public SubstanceFinder withName(String name) {
			return match(s -> s.getName().equals(name));
		}
	}

	private static void checkState(int i, String name) {
		if (i != -1)
			throw new IllegalStateException(name + " is already set");
	}

	private static int validate(int i, String name) {
		if (i < 0)
			throw new IllegalArgumentException(name + " is negative");
		return i;
	}

	private final ChemDatabase db;

	ChemDatabaseFinder(ChemDatabase db) {
		super();
		this.db = db;
	}

	public AtomFinder findAtom() {
		return new AtomFinder();
	}
	
	public EquationFinder findEquation() {
		return new EquationFinder();
	}
	
	public SubstanceFinder findSusbtance() {
		return new SubstanceFinder();
	}
}
