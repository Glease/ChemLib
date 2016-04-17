package net.glease.chem.simple.util;

import java.util.UUID;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * An adaptor that binds UUID to xsd:string
 * @author glease
 *
 */
public final class UUIDAdaptor extends XmlAdapter<String, UUID> {

	@Override
	public UUID unmarshal(String v) throws Exception {
		if(v == null)
			return null;
		return UUID.fromString(v);
	}

	@Override
	public String marshal(UUID v) throws Exception {
		if(v == null)
			return null;
		return v.toString();
	}

}
