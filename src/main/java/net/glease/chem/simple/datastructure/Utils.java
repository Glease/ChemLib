package net.glease.chem.simple.datastructure;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

class Utils {
	@SafeVarargs
	public static boolean isBroken(final Collection<? extends ChemDatabaseComponent<?>>... cs) {
		return Arrays.stream(cs).flatMap(Collection::stream).anyMatch(ChemDatabaseComponent::isBroken);
	}

	public static boolean isBroken(final Element<?, ?> t, final Element<?, ?> e) {
		return isBroken(t.rootScope(), e);
	}

	public static boolean isBroken(final ChemDatabase root, final Element<?, ?> e) {
		return e == null || e.isBroken() || e.rootScope() != root;
	}

	public static boolean isBroken(final Element<?, ?> t, final Element<?, ?>... es) {
		ChemDatabase root = t.rootScope();
		for (Element<?, ?> e : es)
			if( e == null || e.isBroken() || e.rootScope() != root)
				return true;
		return false;
	}

	public static boolean isBroken(final double... temps) {
		for (double temp : temps)
			if(temp<0)
				return true;
		return false;
	}

	@SafeVarargs
	public static boolean isBroken(final Element<?, ?> t, final Stream<? extends Element<?, ?>>... es) {
		ChemDatabase root = t.rootScope();
		return Arrays.stream(es)
				.flatMap(Function.identity())
				.anyMatch(e -> isBroken(root, e));
	}
}
