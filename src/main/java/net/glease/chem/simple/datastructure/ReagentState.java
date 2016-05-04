
package net.glease.chem.simple.datastructure;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * ReagentState的 Java 类。
 *
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * <p>
 * 
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
	
	private static Map<String, ReagentState> values = Arrays.stream(values())
			.collect(Collectors.toMap(ReagentState::name, Function.identity()));
	
	public static ReagentState fromValue(String v) {
		ReagentState s = values.get(v);
		
		if(s!=null)
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
