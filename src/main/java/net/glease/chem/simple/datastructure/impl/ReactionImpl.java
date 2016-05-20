package net.glease.chem.simple.datastructure.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.Reactant;
import net.glease.chem.simple.datastructure.Reaction;
import net.glease.chem.simple.datastructure.Reagent;
import net.glease.chem.simple.datastructure.Resultant;

public class ReactionImpl implements Serializable, Reaction {

	private final static long serialVersionUID = 1L;

	protected Set<String> conditions = new HashSet<>();
	protected Set<Reagent> catalysts = new HashSet<>();
	protected Set<Reactant> reactants = new HashSet<>();
	protected Set<Resultant> resultants = new HashSet<>();

	protected String id;
	protected String name;
	protected double temp = 298.15d;
	protected double pressure = 1.01e+5d;
	protected double k = Double.POSITIVE_INFINITY;
	protected double heat = 0;
	protected double speed;
	protected Reagent solvent;

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
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Reaction)) {
			return false;
		}
		Reaction other = (Reaction) obj;
		if (scope() == null || scope() != other.scope()) {
			return false;
		}
		return !id.equals(other.getId());
	}

	@Override
	public Set<Reagent> getCatalysts() {
		return Collections.unmodifiableSet(catalysts);
	}

	@Override
	public Set<String> getConditions() {
		return Collections.unmodifiableSet(conditions);
	}

	@Override
	public double getHeat() {
		return heat;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public double getK() {
		return k;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getPressure() {
		return pressure;
	}

	@Override
	public Set<Reactant> getReactants() {
		return Collections.unmodifiableSet(reactants);
	}

	@Override
	public Set<Resultant> getResultants() {
		return Collections.unmodifiableSet(resultants);
	}

	@Override
	public Reagent getSolvent() {
		return solvent;
	}

	@Override
	public double getSpeed() {
		return speed;
	}

	@Override
	public double getTemp() {
		return temp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id.hashCode();
		ChemDatabase scope = scope();
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		return result;
	}

	@Override
	public ChemDatabase scope() {
		return scope;
	}

	@Override
	public void setHeat(double value) {
		this.heat = value;
	}

	@Override
	public void setId(String id) {
		if (Objects.requireNonNull(id).isEmpty())
			throw new IllegalArgumentException("empty id");
		this.id = id;
	}

	@Override
	public void setK(double value) {
		this.k = value;
	}

	@Override
	public void setName(String name) {
		this.name = Objects.requireNonNull(name);
	}

	@Override
	public void setPressure(double value) {
		this.pressure = value;
	}

	@Override
	public void setSolvent(Reagent value) {
		this.solvent = Objects.requireNonNull(value);
	}

	@Override
	public void setSpeed(double value) {
		this.speed = value;
	}

	@Override
	public void setTemp(double value) {
		this.temp = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReactionImpl [");
		builder.append("conditions=");
		builder.append(conditions);
		builder.append(", ");
		builder.append("catalysts=");
		builder.append(catalysts);
		builder.append(", ");
		builder.append("reactants=");
		builder.append(reactants);
		builder.append(", ");
		builder.append("resultants=");
		builder.append(resultants);
		builder.append(", ");
		builder.append("temp=");
		builder.append(temp);
		builder.append(", pressure=");
		builder.append(pressure);
		builder.append(", k=");
		builder.append(k);
		builder.append(", heat=");
		builder.append(heat);
		builder.append(", speed=");
		builder.append(speed);
		builder.append(", ");
		if (solvent != null) {
			builder.append("solvent=");
			builder.append(solvent);
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
		bind((ChemDatabase) in.readObject());
	}
}
