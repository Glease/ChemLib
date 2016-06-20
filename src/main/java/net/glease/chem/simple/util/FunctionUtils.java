package net.glease.chem.simple.util;

import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/**
 * Function utilities, these should be built in (and JDK size sky-rockets lol)
 * @author glease
 * @since 0.1
 */
public final class FunctionUtils {
	private FunctionUtils() {
	}

	public static <T,U> Predicate<T> and(final Function<T, U> func, final Predicate<U> ps) {
		return t -> {
			return ps.test(func.apply(t));
		};
	}

	public static <T> Predicate<T> and(final ToIntFunction<T> func, final IntPredicate... ps) {
		return t -> {
			int i = func.applyAsInt(t);
			for (IntPredicate p : ps)
				if(!p.test(i))
					return false;
			return true;
		};
	}

}
