package net.glease.chem.simple.util;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Various adaptors that should be used by all implementations to convert
 * certain types.
 * 
 * @author glease
 * @since 0.1
 */
public final class Adaptors {
	private static final Map<String, Color> PREDEFINED_COLORS = get();
	private static final Map<Color, String> PREDEFINED_COLORS_REVERSED = getReversed();

	private static Map<String, Color> get() {
		Map<String, Color> holder = new HashMap<>();

		for (Field f : Color.class.getFields()) {
			if (f.getType() == Color.class) {
				Color e;
				try {
					e = (Color) f.get(null);
				} catch (IllegalArgumentException | IllegalAccessException e1) {
					throw new Error(e1);
				}
				holder.put(f.getName().toLowerCase(), e);
			}
		}

		return Collections.unmodifiableMap(holder);
	}

	private static Map<Color, String> getReversed() {
		Map<Color, String> holder = new HashMap<>();

		for (Field f : Color.class.getFields()) {
			if (f.getType() == Color.class) {
				Color e;
				try {
					e = (Color) f.get(null);
				} catch (IllegalArgumentException | IllegalAccessException e1) {
					throw new Error(e1);
				}
				holder.put(e, f.getName().toLowerCase());
			}
		}

		return Collections.unmodifiableMap(holder);
	}

	public static Color readColor(String v) {
		if (v == null)
			return null;

		Color c = PREDEFINED_COLORS.get(v.toLowerCase());
		return c == null ? Color.decode(v) : c;
	}

	public static String writeColor(Color v) {
		if (v == null)
			return null;

		String s = PREDEFINED_COLORS_REVERSED.get(v);
		return s == null ? Integer.toHexString(v.getRGB()) : s;
	}
	
	public static Locale readLocale(String v) {
		if(v == null)
			return null;
		
		return Locale.forLanguageTag(v);	
	}
	
	public static String writeLocale(Locale v) {
		if(v == null)
			return null;
		return v.toLanguageTag();
	}

	private Adaptors() {
	}
}
