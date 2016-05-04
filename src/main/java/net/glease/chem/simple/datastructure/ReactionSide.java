package net.glease.chem.simple.datastructure;

import java.util.List;

public enum ReactionSide {
	REACTANT {
		@Override
		List<? extends EquationComponent> get(Equation e) {
			return e.getReactants();
		}
	},
	RESULTANT {
		@Override
		List<? extends EquationComponent> get(Equation e) {
			return e.getResultants();
		}
	},
	BOTH {
		@Override
		List<? extends EquationComponent> get(Equation e) {
			return e.getAllEquationComponents();
		}
	};
	static final ReactionSide[] values = values();

	abstract List<? extends EquationComponent> get(Equation e);
}