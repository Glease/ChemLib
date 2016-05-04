
package net.glease.chem.simple.datastructure;

import java.util.UUID;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class UUIDAdapter extends XmlAdapter<String, UUID> {

	@Override
	public String marshal(UUID value) {
		if (value == null) {
			return null;
		}
		return value.toString();
	}

	@Override
	public UUID unmarshal(String value) {
		return (java.util.UUID.fromString(value));
	}

}
