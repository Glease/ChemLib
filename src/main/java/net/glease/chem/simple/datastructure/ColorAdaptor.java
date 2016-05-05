package net.glease.chem.simple.datastructure;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ColorAdaptor extends XmlAdapter<Color, String> {

	private static final Map<String, Color> PREDEFINED_COLORS = get();
	private static final Map<Color, String> PREDEFINED_COLORS_REVERSED = getReversed();
	
	private static Map<String, Color> get() {
		Map<String, Color> holder = new HashMap<>();
		
		for (Field f : Color.class.getFields()) {
			if(f.getType()==Color.class) {
				Color e;
				try {
					e = (Color) f.get(null);
				} catch (IllegalArgumentException | IllegalAccessException e1) {
					throw new Error(e1);
				}
				holder.put(f.getName(), e);
			}
		}
		
		return Collections.unmodifiableMap(holder);
	}
	
	private static Map<Color, String> getReversed() {
		Map<Color, String> holder = new HashMap<>();
		
		for (Field f : Color.class.getFields()) {
			if(f.getType()==Color.class) {
				Color e;
				try {
					e = (Color) f.get(null);
				} catch (IllegalArgumentException | IllegalAccessException e1) {
					throw new Error(e1);
				}
				holder.put(e, f.getName());
			}
		}
		
		return Collections.unmodifiableMap(holder);
	}

	@Override
	public String unmarshal(Color v) throws Exception {
		if(v==null)
			return null;
		String s = PREDEFINED_COLORS_REVERSED.get(v);
		return s == null ? Integer.toHexString(v.getRGB()) : s;
	}

	@Override
	public Color marshal(String v) throws Exception {
		if(v==null)
			return null;
		return PREDEFINED_COLORS.containsKey(v) ? PREDEFINED_COLORS.get(v) : Color.decode(v);
	}

}
