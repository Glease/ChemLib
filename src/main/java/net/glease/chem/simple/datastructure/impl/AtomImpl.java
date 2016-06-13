
package net.glease.chem.simple.datastructure.impl;

import java.io.Serializable;
import java.util.Objects;

import net.glease.chem.simple.datastructure.Atom;

public class AtomImpl implements Atom, Serializable {

	private final static long serialVersionUID = 1L;

	/**
	 * Copy an {@link Atom}. The new atom will retain all info of origin except
	 * its scope.
	 * @param o
	 * @return
	 */
	public static Atom copyOf(final Atom o) {
		AtomImpl a = new AtomImpl();
		a.averageMolMass = o.getAverageMolMass();
		a.index = o.getIndex();
		a.localizedName = o.getLocalizedName();
		a.molMass = o.getMolMass();
		a.symbol = o.getSymbol();
		return a;
	}
	protected String localizedName;
	protected String symbol;
	protected int molMass;
	protected int index;

	protected double averageMolMass;

	@Override
	public Atom copy() {
		return copyOf(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Atom))
			return false;
		Atom other = (Atom) obj;
		if (scope() == null || scope() != other.scope())
			return false;
		if (getIndex() != other.getIndex())
			return false;
		return getMolMass() == other.getMolMass();
	}

	@Override
	public double getAverageMolMass() {
		return averageMolMass;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public String getLocalizedName() {
		return localizedName;
	}

	@Override
	public int getMolMass() {
		return molMass;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (scope() == null ? 0 : scope().hashCode());
		result = prime * result + getId().hashCode();
		return result;
	}

	@Override
	public void setAverageMolMass(final double averageMolMass) {
		this.averageMolMass = averageMolMass;
	}

	@Override
	public void setIndex(final int value) {
		if (value < 1)
			throw new IllegalArgumentException(Integer.toString(value));
		index = value;
	}

	@Override
	public void setLocalizedName(final String value) {
		if (Objects.requireNonNull(value).isEmpty())
			throw new IllegalArgumentException("name empty");
		localizedName = value;
	}

	@Override
	public void setMolMass(final int value) {
		if (value < 1)
			throw new IllegalArgumentException(Integer.toString(value));
		molMass = value;
	}

	@Override
	public void setSymbol(final String value) {
		if (Objects.requireNonNull(value).isEmpty() || !value.matches("^[A-Z][a-z]?$"))
			throw new IllegalArgumentException(value);
		symbol = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AtomImpl [");
		if (localizedName != null) {
			builder.append("localizedName=");
			builder.append(localizedName);
			builder.append(", ");
		}
		if (symbol != null) {
			builder.append("symbol=");
			builder.append(symbol);
			builder.append(", ");
		}
		builder.append("molMass=");
		builder.append(molMass);
		builder.append(", ");
		builder.append("index=");
		builder.append(index);
		builder.append(", ");
		builder.append("scope=");
		builder.append(scope().getId());
		builder.append("]");
		return builder.toString();
	}
}
