package net.glease.chem.simple.datastructure.impl;

import static net.glease.chem.simple.datastructure.impl.ScopeUtils.bindSub;
import static net.glease.chem.simple.datastructure.impl.ScopeUtils.orphan;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import net.glease.chem.simple.datastructure.Atom;
import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.CrystalType;
import net.glease.chem.simple.datastructure.Dissolve;
import net.glease.chem.simple.datastructure.Element;
import net.glease.chem.simple.datastructure.Substance;
import net.glease.chem.simple.datastructure.SubstanceContent;
import net.glease.chem.simple.scoping.IScoped;
import net.glease.chem.simple.scoping.ScopeException;

public class SubstanceImpl implements Substance, Serializable {

	private final static long serialVersionUID = 1L;

	public static Substance copyOf(final Substance o) {
		SubstanceImpl s = new SubstanceImpl();
		s.boilPoint = o.getBoilPoint();
		s.meltPoint = o.getMeltPoint();
		s.crystal = o.getCrystal();
		s.content = o.getContent().stream().map(SubstanceContentImpl::copyOf).collect(Collectors.toSet());
		s.dissolve = o.getDissolve().stream().map(DissolveImpl::copyOf).collect(Collectors.toSet());
		s.id = o.getId();
		s.name = o.getName();
		return s;
	}
	protected Set<SubstanceContent> content = new HashSet<>();

	protected Set<Dissolve> dissolve = new HashSet<>();
	protected String name;
	protected double meltPoint;
	protected double boilPoint;
	protected String id;

	protected CrystalType crystal = CrystalType.NONE;

	@Override
	public boolean bind(final ChemDatabase newScope) {
		if(newScope == scope())
			return false;
		Set<Atom> added = bindSub(content.stream()
				.map(SubstanceContent::getAtom)
				.map(a -> a.scope() == null ? a : AtomImpl.copyOf(a)), this, newScope);
		Set<? extends Element<ChemDatabase,?>> addedSubstance = bindSub(dissolve.stream()
				.map(Dissolve::getSolvent)
				.map(a -> a.scope() == null ? a : ReagentImpl.copyOf(a)), this, newScope, added);
		try {
			return Substance.super.bind(newScope);
		} catch(Exception e) {
			// revert
			orphan(added, addedSubstance);
			throw e;
		}
	}

	@Override
	public Substance copy() {
		return copyOf(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Substance)
			return false;
		Substance other = (Substance) obj;
		if (scope()== null || scope() != other.scope())
			return false;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public double getBoilPoint() {
		return boilPoint;
	}

	@Override
	public Set<SubstanceContent> getContent() {
		return Collections.unmodifiableSet(content);
	}

	@Override
	public CrystalType getCrystal() {
		return crystal;
	}

	@Override
	public Set<Dissolve> getDissolve() {
		return Collections.unmodifiableSet(dissolve);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public double getMeltPoint() {
		return meltPoint;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (scope() == null ? 0 : scope().hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}



	@Override
	public boolean onBind(final IScoped<Substance> o) {
		if (!Substance.super.onBind(o))
			return false;
		if (o instanceof Dissolve)
			dissolve.add((Dissolve) o);
		else if (o instanceof SubstanceContent)
			content.add((SubstanceContent) o);
		else
			throw new ScopeException("Element not identified.", this, o);
		return true;
	}

	@Override
	public void onUnbind(final IScoped<Substance> o) {
		if (o instanceof Dissolve) {
			if (!dissolve.remove(o))
				throw new ScopeException("Not binded to this scope", this, o);
		} else if (o instanceof SubstanceContent) {
			if (!content.remove(o))
				throw new ScopeException("Not binded to this scope", this, o);
		} else
			throw new ScopeException("Element not identified.", this, o);
		Substance.super.onUnbind(o);
	}

	@Override
	public void setBoilPoint(final double value) {
		boilPoint = value;
	}

	@Override
	public void setCrystal(final CrystalType value) {
		crystal = Objects.requireNonNull(value);
	}

	@Override
	public void setId(final String value) {
		if (Objects.requireNonNull(value).isEmpty())
			throw new IllegalArgumentException("empty id");
		id = value;
	}

	@Override
	public void setMeltPoint(final double value) {
		meltPoint = value;
	}

	@Override
	public void setName(final String value) {
		name = Objects.requireNonNull(value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SubstanceImpl [");
		builder.append("content=");
		builder.append(content);
		builder.append(", ");
		builder.append("dissolve=");
		builder.append(dissolve);
		builder.append(", ");
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		builder.append("meltPoint=");
		builder.append(meltPoint);
		builder.append(", boilPoint=");
		builder.append(boilPoint);
		builder.append(", ");
		builder.append("id=");
		builder.append(id);
		builder.append(", ");
		if (crystal != null) {
			builder.append("crystal=");
			builder.append(crystal);
		}
		builder.append("]");
		return builder.toString();
	}
}
