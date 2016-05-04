
package net.glease.chem.simple.datastructure.impl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.glease.chem.simple.datastructure.FloatAdapter;
import net.glease.chem.simple.datastructure.Reactant;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Reactant")
public class ReactantImpl extends EquationComponentImpl implements Serializable, Reactant {

	private final static long serialVersionUID = 1L;
	@XmlAttribute(name = "purity")
	@XmlJavaTypeAdapter(FloatAdapter.class)
	protected float purity;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ReactantImpl other = (ReactantImpl) obj;
		if (Float.floatToIntBits(purity) != Float.floatToIntBits(other.purity)) {
			return false;
		}
		return true;
	}

	@Override
	public float getPurity() {
		return purity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(purity);
		return result;
	}

	@Override
	public void setPurity(Float value) {
		this.purity = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReactantImpl [");
		builder.append("purity=");
		builder.append(purity);
		builder.append(", ");
		if (substance != null) {
			builder.append("substance=");
			builder.append(substance);
			builder.append(", ");
		}
		if (state != null) {
			builder.append("state=");
			builder.append(state);
			builder.append(", ");
		}
		builder.append("getMol()=");
		builder.append(getMol());
		builder.append("]");
		return builder.toString();
	}

}
