
package net.glease.chem.simple.datastructure;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * The Java class of ReagentState.
 *
 * @author glease
 * @since 0.1
 *
 */

public enum ReagentState {

	POWDER("powder"),

	NUGGET("nugget"),

	CHUNK("chunk"),

	LIQUID("liquid"),

	SOLUTION("solution"),

	GAS("gas");

	static Map<String, ReagentState> values = Arrays.stream(values())
			.collect(Collectors.toMap(ReagentState::value, Function.identity()));

	public static ReagentState fromValue(String v) {
		ReagentState s = values.get(v.toLowerCase());

		if (s != null)
			return s;

		throw new IllegalArgumentException(v);
	}

	private final String value;

	ReagentState(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

}
