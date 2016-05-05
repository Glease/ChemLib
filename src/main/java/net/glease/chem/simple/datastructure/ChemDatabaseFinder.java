package net.glease.chem.simple.datastructure;

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

@XmlTransient
@NonNullByDefault({PARAMETER, RETURN_TYPE})
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
			return s.filter(p).collect(immutableSetCollector());
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
					.collect(immutableSetCollector());
		}

		private EquationFinder match(Predicate<Equation> next) {
			p = p == null ? next : p.and(next);
			return this;
		}

		public EquationFinder withCatalyst(Reagent catalyst) {
			return match(e -> e.getCatalysts().contains(catalyst));
		}

		public EquationFinder withAllCatalysts(Reagent... catalysts) {
			List<Reagent> l = Arrays.asList(catalysts);
			return match(e -> e.getCatalysts().containsAll(l));
		}

		public EquationFinder withAnyCatalysts(Reagent... catalysts) {
			List<Reagent> l = Arrays.asList(catalysts);
			return match(e -> e.getCatalysts().stream().anyMatch(l::contains));
		}

		public EquationFinder withComponent(EquationComponent comp) {
			return match(comp instanceof Resultant ? e -> e.getResultants().contains(comp)
					: e -> e.getReactants().contains(comp));
		}

		public EquationFinder withAllComponents(EquationComponent... comp) {
			List<EquationComponent> rs = Arrays.asList(comp);
			return match(t -> t.getAllEquationComponents().parallelStream().allMatch(rs::contains));
		}

		public EquationFinder withAnyComponents(EquationComponent... comp) {
			List<EquationComponent> rs = Arrays.asList(comp);
			return match(t -> t.getAllEquationComponents().parallelStream().anyMatch(rs::contains));
		}

		public EquationFinder withCondition(String condition) {
			return match(e -> e.getConditions().contains(condition));
		}

		public EquationFinder withAllConditions(String... conditions) {
			List<String> l = Arrays.asList(conditions);
			return match(e -> e.getConditions().containsAll(l));
		}

		public EquationFinder withAnyConditions(String... conditions) {
			List<String> l = Arrays.asList(conditions);
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
		 * @param ss
		 * @return
		 */
		public EquationFinder withAllSubstances(Map<ReactionSide, Set<Substance>> ss) {
			if (ss.size() == 0)
				return this;

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
		}

		/**
		 * I'd rather use multiple chained
		 * {@link #substance(ReactionSide, Substance)} calls than using this
		 * method if the <code>ss</code> is too small.
		 * 
		 * @param ss
		 * @return
		 */
		public EquationFinder withAnySubstances(Map<ReactionSide, Set<Substance>> ss) {
			if (ss.size() == 0)
				return this;

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
		}
	}

	public class ReagentFinder {

		private Substance s1;
		private Substance s2;
		private String name;
		private ReagentState state;

		private ReagentFinder() {
			super();
		}

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

		public ReagentFinder withSubstance(Substance s) {
			s1 = s;
			return this;
		}

		public ReagentFinder withSolvent(Substance s) {
			s2 = s;
			return this;
		}

		public ReagentFinder withName(String s) {
			name = s;
			return this;
		}

		public ReagentFinder withState(ReagentState s) {
			state = s;
			return this;
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
				scs = new HashSet<>();
			for (SubstanceContent content : contents) {
				scs.add(content);
			}
			return this;
		}

		public SubstanceFinder containsAny(SubstanceContent... contents) {
			return match(e -> e.getAtom().stream().anyMatch(Arrays.asList(contents)::contains));
		}

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

	public ReagentFinder findReagent() {
		return new ReagentFinder();
	}

	public SubstanceFinder findSusbtance() {
		return new SubstanceFinder();
	}

	@SuppressWarnings("null")
	private static <T> Collector<T, ?, @NonNull Set<T>> immutableSetCollector() {
		return collectingAndThen(toSet(), Collections::unmodifiableSet);
	}
}
