
package net.glease.chem.simple.datastructure;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class IntAdapter extends XmlAdapter<String, Integer> {

	@Override
	public String marshal(Integer value) {
		if (value == null) {
			return null;
		}
		return (javax.xml.bind.DatatypeConverter.printDouble(value));
	}

	@Override
	public Integer unmarshal(String value) {
		return ((int) javax.xml.bind.DatatypeConverter.parseDouble(value));
	}

}
