package net.glease.chem.simple.datastructure;

import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

public enum ReactionSide {
	REACTANT {
		@Override
		List<? extends EquationComponent> get0(Equation e) {
			return e.getReactants();
		}
	},
	RESULTANT {
		@Override
		List<? extends EquationComponent> get0(Equation e) {
			return e.getResultants();
		}
	},
	BOTH {
		@Override
		List<? extends EquationComponent> get0(Equation e) {
			return e.getAllEquationComponents();
		}
	};
	static final ReactionSide[] values = values();

	List<? extends EquationComponent> get(@Nullable Equation e) {
		if (e == null)
			throw new NullPointerException();
		return get0(e);
	}

	abstract List<? extends EquationComponent> get0(Equation e);
}