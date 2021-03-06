package net.glease.chem.simple.datastructure;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.glease.chem.simple.datastructure.impl.ChemDatabaseFinderImpl;

/**
 * Provide a fluent API to find various item in a given {@link ChemDatabase}.
 *
 * @author glease
 * @since 0.1
 */
public interface ChemDatabaseFinder {
	interface AtomFinder extends Finder<Atom> {

		/**
		 * If you are confident that you are looking for only one {@link Atom},
		 * use this. Otherwise it will bug you with an
		 * {@link IllegalStateException}.
		 *
		 * @return the content you want, or <code>null</code> if not found.
		 * @throws IllegalStateException
		 *             if multiple results ar e found.
		 */

		Atom unique();

		@Override
		AtomFinder where(Predicate<Atom> filter);

		AtomFinder withIndex(int index);

		AtomFinder withMolMass(int molMass);
	}

	interface Finder<E> {
		Set<E> find();

		Set<E> parallelFind();

		Finder<E> where(Predicate<E> filter);
	}

	interface ReactionFinder extends Finder<Reaction> {

		@Override
		ReactionFinder where(Predicate<Reaction> filter);

		ReactionFinder withAllCatalysts(Reagent... catalysts);

		ReactionFinder withAllComponents(ReactionComponent<?>... comp);

		ReactionFinder withAllConditions(String... conditions);

		/**
		 * I'd rather use multiple chained
		 * {@link #substance(ReactionSide, Substance)} calls than using this
		 * method if the <code>ss</code> is too small.
		 *
		 * @param ss
		 * @return
		 */
		ReactionFinder withAllSubstances(Map<ReactionSide, Set<Substance>> ss);

		ReactionFinder withAnyCatalysts(Reagent... catalysts);

		ReactionFinder withAnyComponents(ReactionComponent<?>... comp);

		ReactionFinder withAnyConditions(String... conditions);

		/**
		 * I'd rather use multiple chained
		 * {@link #substance(ReactionSide, Substance)} calls than using this
		 * method if the <code>ss</code> is too small.
		 *
		 * @param ss
		 * @return
		 */
		ReactionFinder withAnySubstances(Map<ReactionSide, Set<Substance>> ss);

		ReactionFinder withCatalyst(Reagent catalyst);

		ReactionFinder withComponent(ReactionComponent<?> comp);

		ReactionFinder withCondition(String condition);

		ReactionFinder withSubstance(ReactionSide side, Substance s);
	}

	enum ReactionSide {
		REACTANT {
			@Override
			Set<? extends ReactionComponent<?>> get0(final Reaction e) {
				return e.getReactants();
			}
		},
		RESULTANT {
			@Override
			Set<? extends ReactionComponent<?>> get0(final Reaction e) {
				return e.getResultants();
			}
		},
		BOTH {
			@Override
			Set<? extends ReactionComponent<?>> get0(final Reaction e) {
				return e.getAllReactionComponents().collect(Collectors.toSet());
			}
		};

		static final Map<String, ReactionSide> values;

		static {
			values = get();
			ChemDatabaseFinderImpl.setReactionSideValues(values);
		}

		public static ReactionSide fromValue(final String v) {
			ReactionSide side = values.get(v);
			if (side == null) {
				throw new IllegalArgumentException(v);
			}
			return side;
		}

		private static Map<String, ReactionSide> get() {
			return Arrays.stream(values()).collect(Collectors.toMap(Enum::name, Function.identity()));
		}

		public Set<? extends ReactionComponent<?>> get(final Reaction e) {
			if (e == null) {
				throw new NullPointerException();
			}
			return get0(e);
		}

		abstract Set<? extends ReactionComponent<?>> get0(Reaction e);
	}

	interface ReagentFinder extends Finder<Reagent> {

		@Override
		ReagentFinder where(Predicate<Reagent> filter);

		ReagentFinder withName(String s);

		ReagentFinder withSolvent(Substance s);

		ReagentFinder withState(ReagentState s);

		ReagentFinder withSubstance(Substance s);
	}

	interface SubstanceFinder extends Finder<Substance> {

		SubstanceFinder containsAll(SubstanceContent... contents);

		SubstanceFinder containsAny(SubstanceContent... contents);

		@Override
		SubstanceFinder where(Predicate<Substance> filter);

		SubstanceFinder withName(String name);
	}

	AtomFinder findAtom();

	ReactionFinder findReaction();

	ReagentFinder findReagent();

	SubstanceFinder findSusbtance();

}