package net.glease.chem.simple.util;

import java.util.function.Function;

@FunctionalInterface
public interface SafeFunction<T, R> extends Function<T, R> {
	static <T, R> Function<T, R> safe(final SafeFunction<T, R> func) {
		return func;
	}

	@Override
	default R apply(final T t) {
		try {
			return applyWithRisk(t);
		} catch (Exception e) {
			throw new AssertionError("Unexpected exception", e);
		}
	}

	R applyWithRisk(T t) throws Exception;
}
