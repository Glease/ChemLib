
package net.glease.chem.simple.datastructure;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DoubleAdapter extends XmlAdapter<String, Double> {

	@Override
	public String marshal(Double value) {
		if (value == null) {
			return null;
		}
		return (javax.xml.bind.DatatypeConverter.printDouble(value));
	}

	@Override
	public Double unmarshal(String value) {
		return ((double) javax.xml.bind.DatatypeConverter.parseDouble(value));
	}

}
