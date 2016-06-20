package net.glease.chem.simple;

import static net.glease.chem.simple.util.SafeFunction.safe;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

public final class CDBConstants {
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@Documented
	public @interface ConfigurableConstant {
	}
	public static final String VERSION = "0.1";
	@ConfigurableConstant
	public static final int LARGEST_ATOM_INDEX;
	@ConfigurableConstant
	public static final int LARGEST_ATOM_MASS;

	static {
		LARGEST_ATOM_INDEX = Integer.getInteger(process("LARGEST_ATOM_INDEX"), 120);
		LARGEST_ATOM_MASS = Integer.getInteger(process("LARGEST_ATOM_MASS"), 334);
	}

	public static void main(final String[] args) {
		Arrays.stream(CDBConstants.class.getFields())
				.map(safe(f -> String.format("Field %s: %s", f.getName(), f.get(null))))
				.forEach(System.out::println);
	}

	private static final String PROPERTY_PREFIX = "net.glease.chem.simple.constants.";

	private static String process(final String s) {
		StringBuilder sb = new StringBuilder(PROPERTY_PREFIX.length()+s.length());
		sb.append(PROPERTY_PREFIX);
		char[] cs = s.toCharArray();
		boolean isPrevBoundary = false;
		for (char c : cs)
			if (c == '_')
				isPrevBoundary = true;
			else {
				sb.append(isPrevBoundary ? c : Character.toLowerCase(c));
				isPrevBoundary = false;
			}
		return sb.toString();
	}

}
