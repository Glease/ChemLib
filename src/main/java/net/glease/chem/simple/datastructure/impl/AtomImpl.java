
package net.glease.chem.simple.datastructure.impl;

import java.io.Serializable;
import java.util.Objects;

import net.glease.chem.simple.datastructure.Atom;
import net.glease.chem.simple.datastructure.ChemDatabase;

public class AtomImpl implements Serializable, Atom {

	private final static long serialVersionUID = 1L;
	
	protected String localizedName;
	protected String symbol;
	protected int molMass;
	protected int index;
	protected double averageMolMass;

	protected ChemDatabase scope;

	@Override
	public void bind(ChemDatabase scope) {
		if (this.scope != null)
			this.scope.onUnbind(this);
		this.scope = scope;
		if (scope != null)
			scope.onBind(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Atom)) {
			return false;
		}
		Atom other = (Atom) obj;
		if (scope == null || scope != other.scope()) {
			return false;
		}
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
		result = prime * result + ((scope() == null) ? 0 : scope().hashCode());
		result = prime * result + getId().hashCode();
		return result;
	}

	@Override
	public ChemDatabase scope() {
		return scope;
	}

	@Override
	public void setAverageMolMass(double averageMolMass) {
		this.averageMolMass = averageMolMass;
	}

	@Override
	public void setIndex(int value) {
		if (value < 1)
			throw new IllegalArgumentException(Integer.toString(value));
		this.index = value;
	}

	@Override
	public void setLocalizedName(String value) {
		if (Objects.requireNonNull(value).isEmpty())
			throw new IllegalArgumentException("name empty");
		this.localizedName = value;
	}

	@Override
	public void setMolMass(int value) {
		if (value < 1)
			throw new IllegalArgumentException(Integer.toString(value));
		this.molMass = value;
	}

	@Override
	public void setSymbol(String value) {
		if (Objects.requireNonNull(value).isEmpty() || !value.matches("^[A-Z][a-z]?$"))
			throw new IllegalArgumentException(value);
		this.symbol = value;
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
