
package net.glease.chem.simple.datastructure.impl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.glease.chem.simple.datastructure.Resultant;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Resultant")
public class ResultantImpl extends ReactionComponentImpl implements Serializable, Resultant {

	private final static long serialVersionUID = 1L;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResultantImpl [getMol()=");
		builder.append(getMol());
		builder.append(", ");
		if (getState() != null) {
			builder.append("getState()=");
			builder.append(getState());
			builder.append(", ");
		}
		if (getSubstance() != null) {
			builder.append("getSubstance()=");
			builder.append(getSubstance());
		}
		builder.append("]");
		return builder.toString();
	}

}
