
package net.glease.chem.simple.datastructure;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * The Java class of ReagentState.
 * 
 * <p>The following XML Schema snipplet contains the expect content of this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReagentState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="powder"/>
 *     &lt;enumeration value="nugget"/>
 *     &lt;enumeration value="chunk"/>
 *     &lt;enumeration value="liquid"/>
 *     &lt;enumeration value="solution"/>
 *     &lt;enumeration value="gas"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReagentState")
@XmlEnum
public enum ReagentState {

    @XmlEnumValue("powder")
    POWDER("powder"),
    @XmlEnumValue("nugget")
    NUGGET("nugget"),
    @XmlEnumValue("chunk")
    CHUNK("chunk"),
    @XmlEnumValue("liquid")
    LIQUID("liquid"),
    @XmlEnumValue("solution")
    SOLUTION("solution"),
    @XmlEnumValue("gas")
    GAS("gas");
    private final String value;

    ReagentState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReagentState fromValue(String v) {
        for (ReagentState c: ReagentState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
