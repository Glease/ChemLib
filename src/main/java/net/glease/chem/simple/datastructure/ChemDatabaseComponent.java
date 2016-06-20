package net.glease.chem.simple.datastructure;

/**
 * Marker interface indicating a type is part of a {@link ChemDatabase} data
 * structure if it has attributes. If it's a part of the data structure but has
 * no attribute, it won't have such marker.
 *
 * @author glease
 * @since 0.1
 *
 */
public interface ChemDatabaseComponent<T_THIS extends ChemDatabaseComponent<T_THIS>> {
	/**
	 * List interfaces that implements {@link ChemDatabaseComponent}.
	 * {@link ReactionComponent} is not included because it's actually the
	 * intersection of {@link Reactant} and {@link Resultant}. The enum constant
	 * order guarantees the later constant's dependencies are before it.
	 *
	 * @author glease
	 * @since 0.1
	 *
	 */
	enum ComponentType {
		ATOM(Atom.class),
		DISSOLVE(Dissolve.class),
		SUBSTANCE_CONTENT(SubstanceContent.class),
		SUBSTANCE(Substance.class),
		REAGENT(Reagent.class),
		REACTANT(Reactant.class),
		RESULTANT(Resultant.class),
		REACTION(Reaction.class),
		CHEM_DATABASE(ChemDatabase.class);

		private static final ComponentType[] values = values();

		public static ComponentType get(final ChemDatabaseComponent<?> c) {
			for (ComponentType type : values)
				if(type.type.isInstance(c))
					return type;
			throw new IllegalArgumentException("Not a component.");
		}
		public static ComponentType get(final Class<?> c) {
			for (ComponentType type : values)
				if(type.type.isAssignableFrom(c))
					return type;
			throw new IllegalArgumentException("Not a component.");
		}

		private final Class<?> type;

		private final String name;

		private ComponentType(final Class<?> type) {
			this.type = type;
			name = name().toLowerCase().replace("_", " ");
		}

		public String getName() {
			return name;
		}

		public Class<?> getType() {
			return type;
		}
	}
	T_THIS copy();

	/**
	 * Test if this component is broken.
	 * @return
	 */
	boolean isBroken();
}
