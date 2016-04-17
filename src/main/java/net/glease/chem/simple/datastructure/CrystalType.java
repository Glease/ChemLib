
package net.glease.chem.simple.datastructure;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * The Java class of CrystalType.
 * 
 * <p>The following XML Schema snipplet contains the expect content of this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CrystalType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="none"/>
 *     &lt;enumeration value="metal"/>
 *     &lt;enumeration value="ion"/>
 *     &lt;enumeration value="molecule"/>
 *     &lt;enumeration value="atom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CrystalType")
@XmlEnum
public enum CrystalType {

    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("metal")
    METAL("metal"),
    @XmlEnumValue("ion")
    ION("ion"),
    @XmlEnumValue("molecule")
    MOLECULE("molecule"),
    @XmlEnumValue("atom")
    ATOM("atom");
    private final String value;

    CrystalType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CrystalType fromValue(String v) {
        for (CrystalType c: CrystalType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
