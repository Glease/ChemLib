package net.glease.chem.simple;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;
import java.util.function.Function;

import net.glease.chem.simple.CDBConstants.Configurable.AcceptedType;
import net.glease.chem.simple.util.SafeFunction;

/**
 * Contains various constants, may or may not be configurable.
 * @author glease
 * @since 0.1
 */
public final class CDBConstants {
	/**
	 * Constants marked with this annotation may be configured via system properties or
	 * in the cdbconstants.properties file.
	 * @author glease
	 * @since 0.1
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@Documented
	public @interface Configurable {
		/**
		 * The default value. Maybe parsed into another type depending on the {@link #type()
		 * constant type}.
		 * @return
		 */
		String value();

		/**
		 * Indicate the type of
		 *
		 * @return
		 */
		AcceptedType type() default AcceptedType.STRING;

		public enum AcceptedType {
			STRING(Function.identity()),
			INT(Integer::decode),
			BOOLEAN(Boolean::parseBoolean);
			private final Function<String, ?> parser;

			private AcceptedType(final Function<String, ?> parser) {
				this.parser = parser;
			}
			Object parse(final String value) {
				return parser.apply(value);
			}
		}
	}

	private static final ConfigurableConstants CONFIURABLES = new ConfigurableConstants();

	public static final String VERSION = "0.1";

	public static final int LARGEST_ATOM_INDEX = CONFIURABLES.LARGEST_ATOM_INDEX;
	public static final int LARGEST_ATOM_MASS = CONFIURABLES.LARGEST_ATOM_MASS;

	private static class ConfigurableConstants {
		private final Properties p = new Properties();

		private static ClassLoader getClassLoader() {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			return cl == null ? CDBConstants.class.getClassLoader() : cl;
		}

		ConfigurableConstants() {
			InputStream s = getClassLoader().getResourceAsStream("cdbconstants.properties");
			if (s != null)
				try {
					p.load(s);
				} catch (IOException e) {
				}

			Class<ConfigurableConstants> clazz = ConfigurableConstants.class;
			Arrays.stream(clazz.getFields()).filter(f -> f.isAnnotationPresent(Configurable.class))
			.peek(f -> f.setAccessible(true)).forEach(this::set);
		}

		private void set(final Field f) {
			Configurable c = f.getAnnotation(Configurable.class);
			String name = process(f.getName());
			String value = System.getProperty(name);
			AcceptedType type = c.type();
			Object v;
			if(value == null)
				v = parse2(name, value, type);
			else
				try {
					v = type.parse(value);
				} catch (Exception e) {
					v = parse2(name, value, type);
				}

			try {
				f.set(this, v);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException("Unexpected exception during constants setup", e);
			}
		}

		private Object parse2(final String name, final String value, final AcceptedType type) {
			Object v;
			try {
				v = type.parse(p.getProperty(name));
			} catch (Exception e2) {
				v = type.parse(value);
			}
			return v;
		}

		@Configurable(value = "120", type = AcceptedType.INT)
		public int LARGEST_ATOM_INDEX = -1;
		@Configurable(value = "334", type = AcceptedType.INT)
		public int LARGEST_ATOM_MASS = -1;
	}

	public static void main(final String[] args) {
		Arrays.stream(CDBConstants.class.getFields())
		.map(SafeFunction.safe(f -> f.get(null)))
		.forEach(System.out::println);;
	}

	private static final String PROPERTY_PREFIX = "net.glease.chem.simple.constants.";

	private static String process(final String s) {
		StringBuilder sb = new StringBuilder(PROPERTY_PREFIX.length() + s.length());
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
