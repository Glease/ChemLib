package net.glease.chem.simple.datastructure;

import static org.eclipse.jdt.annotation.DefaultLocation.PARAMETER;
import static org.eclipse.jdt.annotation.DefaultLocation.RETURN_TYPE;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

import net.glease.chem.simple.datastructure.ChemDatabaseFinder.ReactionSide;
import net.glease.chem.simple.datastructure.impl.ChemDatabaseFinderImpl;

@NonNullByDefault({ PARAMETER, RETURN_TYPE })
public interface ChemDatabaseFinder {
	interface AtomFinder {

		Set<Atom> find();

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
		Atom unique();

		AtomFinder withIndex(int index);

		AtomFinder withMolMass(int molMass);

	}
	interface ReactionFinder {

		Set<Reaction> find();

		ReactionFinder withAllCatalysts(Reagent... catalysts);

		ReactionFinder withAllComponents(ReactionComponent... comp);

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

		ReactionFinder withAnyComponents(ReactionComponent... comp);

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

		ReactionFinder withComponent(ReactionComponent comp);

		ReactionFinder withCondition(String condition);

		ReactionFinder withSubstance(ReactionSide side, Substance s);

	}

	@NonNullByDefault({ PARAMETER })
	enum ReactionSide {
		REACTANT {
			@Override
			List<? extends ReactionComponent> get0(Reaction e) {
				return e.getReactants();
			}
		},
		RESULTANT {
			@Override
			List<? extends ReactionComponent> get0(Reaction e) {
				return e.getResultants();
			}
		},
		BOTH {
			@Override
			List<? extends ReactionComponent> get0(Reaction e) {
				return e.getAllEquationComponents();
			}
		};
		@NonNull
		static final Map<String, ReactionSide> values;

		static {
			values = get();
			ChemDatabaseFinderImpl.setReactionSideValues(values);
		}

		public static ReactionSide fromValue(String v) {
			ReactionSide side = values.get(v);
			if (side == null)
				throw new IllegalArgumentException(v);
			return side;
		}

		@SuppressWarnings("null")
		private static @NonNull Map<String, ReactionSide> get() {
			return Arrays.stream(values()).collect(Collectors.toMap(Enum::name, Function.identity()));
		}

		@NonNull
		public List<? extends ReactionComponent> get(@Nullable Reaction e) {
			if (e == null)
				throw new NullPointerException();
			return get0(e);
		}

		@NonNull
		abstract List<? extends ReactionComponent> get0(Reaction e);
	}

	interface ReagentFinder {

		Set<Reagent> find();

		ReagentFinder withName(String s);

		ReagentFinder withSolvent(Substance s);

		ReagentFinder withState(ReagentState s);

		ReagentFinder withSubstance(Substance s);

	}

	interface SubstanceFinder {

		SubstanceFinder containsAll(@NonNull SubstanceContent... contents);

		SubstanceFinder containsAny(@NonNull SubstanceContent... contents);

		Set<Substance> find();

		SubstanceFinder withName(String name);

	}

	AtomFinder findAtom();

	ReactionFinder findEquation();

	ReagentFinder findReagent();

	SubstanceFinder findSusbtance();

}