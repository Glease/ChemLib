package net.glease.chem.simple.datastructure.impl;

import static net.glease.chem.simple.datastructure.impl.ScopeUtils.bindSub;
import static net.glease.chem.simple.datastructure.impl.ScopeUtils.orphan;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.Reactant;
import net.glease.chem.simple.datastructure.Reaction;
import net.glease.chem.simple.datastructure.ReactionComponent;
import net.glease.chem.simple.datastructure.Reagent;
import net.glease.chem.simple.datastructure.Resultant;
import net.glease.chem.simple.datastructure.Substance;

public class ReactionImpl implements Serializable, Reaction {
	private final static long serialVersionUID = 1L;

	public static Reaction copyOf(final Reaction o) {
		ReactionImpl r = new ReactionImpl();
		r.catalysts = o.getCatalysts().stream().map(ReagentImpl::copyOf).collect(Collectors.toSet());
		r.conditions = new HashSet<>(o.getConditions());
		r.heat = o.getHeat();
		r.id = o.getId();
		r.k = o.getK();
		r.name = o.getName();
		r.pressure = o.getPressure();
		r.reactants = o.getReactants().stream().map(ReactantImpl::copyOf).collect(Collectors.toSet());
		r.resultants = o.getResultants().stream().map(ResultantImpl::copyOf).collect(Collectors.toSet());
		r.solvent = ReagentImpl.copyOf(o.getSolvent());
		r.speed = o.getSpeed();
		r.temp = o.getTemp();
		return r;
	}

	protected Set<String> conditions = new HashSet<>();
	protected Set<Reagent> catalysts = new HashSet<>();
	protected Set<Reactant> reactants = new HashSet<>();

	protected Set<Resultant> resultants = new HashSet<>();
	protected transient Set<Reagent> unmodifiableCatalysts = Collections.unmodifiableSet(catalysts);
	protected transient Set<String> unmodifiableConditions = Collections.unmodifiableSet(conditions);
	protected transient Set<Reactant> unmodifiableReactants = Collections.unmodifiableSet(reactants);

	protected transient Set<Resultant> unmodifiableResultants = Collections.unmodifiableSet(resultants);
	protected String id;
	protected String name;
	protected double temp = 298.15d;
	protected double pressure = 1.01e+5d;
	protected double k = Double.POSITIVE_INFINITY;
	protected double heat = 0;
	protected double speed;

	protected Reagent solvent;

	@Override
	public boolean addCatalyst(final Reagent catalyst) {
		return catalysts.add(catalyst);
	}

	@Override
	public boolean addCondition(final String condition) {
		return conditions.add(condition);
	}

	@Override
	public boolean bind(final ChemDatabase newScope) {
		if (newScope == scope())
			return false;
		Set<Substance> a = bindSub(getAllReactionComponents().map(ReactionComponent::getSubstance), this, newScope);
		Set<Reagent> b = new HashSet<>();
		if (solvent != null)
			b.addAll(bindSub(Stream.of(solvent.scope() == null ? solvent : ReagentImpl.copyOf(solvent)), this,
					newScope));
		b.addAll(bindSub(catalysts.stream().map(r -> r.scope() == null ? r : ReagentImpl.copyOf(r)), this, newScope));

		try {
			return Reaction.super.bind(newScope);
		} catch (Exception e) {
			orphan(a, b);
			throw e;
		}
	}

	@Override
	public Reaction copy() {
		return copyOf(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Reaction))
			return false;
		Reaction other = (Reaction) obj;
		if (scope() == null || scope() != other.scope())
			return false;
		return !id.equals(other.getId());
	}

	@Override
	public Set<Reagent> getCatalysts() {
		return unmodifiableCatalysts;
	}

	@Override
	public Set<String> getConditions() {
		return unmodifiableConditions;
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
		return unmodifiableReactants;
	}

	@Override
	public Set<Resultant> getResultants() {
		return unmodifiableResultants;
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
		result = prime * result + (scope == null ? 0 : scope.hashCode());
		return result;
	}

	private void readObject(final ObjectInputStream in) throws ClassNotFoundException, IOException {
		in.defaultReadObject();
		unmodifiableCatalysts = Collections.unmodifiableSet(catalysts);
		unmodifiableConditions = Collections.unmodifiableSet(conditions);
		unmodifiableReactants = Collections.unmodifiableSet(reactants);
		unmodifiableResultants = Collections.unmodifiableSet(resultants);
	}

	@Override
	public boolean removeCatalyst(final Reagent catalyst) {
		return catalysts.remove(catalyst);
	}

	@Override
	public boolean removeCondition(final String condition) {
		return conditions.remove(condition);
	}

	@Override
	public void setHeat(final double value) {
		heat = value;
	}

	@Override
	public void setId(final String id) {
		if (Objects.requireNonNull(id).isEmpty())
			throw new IllegalArgumentException("empty id");
		this.id = id;
	}

	@Override
	public void setK(final double value) {
		k = value;
	}

	@Override
	public void setName(final String name) {
		this.name = Objects.requireNonNull(name);
	}

	@Override
	public void setPressure(final double value) {
		pressure = value;
	}

	@Override
	public void setSolvent(final Reagent value) {
		solvent = Objects.requireNonNull(value);
	}

	@Override
	public void setSpeed(final double value) {
		speed = value;
	}

	@Override
	public void setTemp(final double value) {
		temp = value;
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
}
