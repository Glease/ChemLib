
package net.glease.chem.simple.datastructure;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class FloatAdapter extends XmlAdapter<String, Float> {

	@Override
	public String marshal(Float value) {
		if (value == null) {
			return null;
		}
		return (javax.xml.bind.DatatypeConverter.printFloat(value));
	}

	@Override
	public Float unmarshal(String value) {
		return ((float) javax.xml.bind.DatatypeConverter.parseFloat(value));
	}

}
