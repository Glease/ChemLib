
package net.glease.chem.simple.datastructure.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.glease.chem.simple.datastructure.Reactant;
import net.glease.chem.simple.datastructure.Reaction;

public class ReactantImpl extends ReactionComponentImpl implements Reactant {

	private final static long serialVersionUID = 1L;

	protected double purity = 100d;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (obj instanceof Reactant) {
			return false;
		}
		Reactant other = (Reactant) obj;
		if (Double.doubleToLongBits(purity) != Double.doubleToLongBits(other.getPurity())) {
			return false;
		}
		return true;
	}

	@Override
	public double getPurity() {
		return purity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(purity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public void setPurity(double value) {
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

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeObject(scope());
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		bind((Reaction) in.readObject());
	}
}
