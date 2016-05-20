
package net.glease.chem.simple.datastructure.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import net.glease.chem.simple.datastructure.Reaction;
import net.glease.chem.simple.datastructure.Resultant;

public class ResultantImpl extends ReactionComponentImpl implements Serializable, Resultant {

	private final static long serialVersionUID = 1L;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Resultant)) {
			return false;
		}
		Resultant other = (Resultant) obj;
		if (scope() == null) {
			if (other.scope() != null) {
				return false;
			}
		} else if (!scope().equals(other.scope())) {
			return false;
		}
		if (mol != other.getMol()) {
			return false;
		}
		if (state != other.getState()) {
			return false;
		}
		if (substance == null) {
			if (other.getSubstance() != null) {
				return false;
			}
		} else if (!substance.equals(other.getSubstance())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + super.hashCode();
		return result;
	}

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

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeObject(scope());
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		bind((Reaction) in.readObject());
	}

}
