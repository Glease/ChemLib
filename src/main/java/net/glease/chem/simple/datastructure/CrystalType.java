
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * The Java class of CrystalType.
 *
 * <p>
 * The following XML Schema snippet contains the expect content of this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="CrystalType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="none"/>
 *     &lt;enumeration value="metal"/>
 *     &lt;enumeration value="ion"/>
 *     &lt;enumeration value="molecule"/>
 *     &lt;enumeration value="content"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
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
