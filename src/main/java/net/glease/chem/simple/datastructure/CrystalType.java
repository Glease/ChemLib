
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * The Java class of CrystalType.
 *
 * 
 * @author glease
 * @since 0.1
 *
 */
public enum CrystalType {

	NONE("none"), METAL("metal"), ION("ion"), MOLECULE("molecule"), ATOM("content");
	public static CrystalType fromValue(String v) {
		for (CrystalType c : CrystalType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

	private final String value;

	CrystalType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

}
