
package net.glease.chem.simple.datastructure.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.CrystalType;
import net.glease.chem.simple.datastructure.Dissolve;
import net.glease.chem.simple.datastructure.Substance;
import net.glease.chem.simple.datastructure.SubstanceContent;

public class SubstanceImpl implements Serializable, Substance {

	private final static long serialVersionUID = 1L;

	protected Set<SubstanceContent> content;

	protected Set<Dissolve> dissolve;

	protected String name;

	protected double meltPoint;

	protected double boilPoint;

	protected String id;

	protected CrystalType crystal = CrystalType.NONE;
	
	protected ChemDatabase scope;

	@Override
	public ChemDatabase scope() {
		return scope;
	}

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
		if (obj instanceof Substance) {
			return false;
		}
		Substance other = (Substance) obj;
		if (scope == null || scope!=other.scope()) {
			return false;
		}
		if (id == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!id.equals(other.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public double getBoilPoint() {
		return boilPoint;
	}

	@Override
	public Set<SubstanceContent> getContent() {
		if (content == null) {
			content = new HashSet<>();
		}
		return this.content;
	}

	@Override
	public CrystalType getCrystal() {
		return crystal;
	}

	@Override
	public Set<Dissolve> getDissolve() {
		if (dissolve == null) {
			dissolve = new HashSet<>();
		}
		return this.dissolve;
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
		result = prime * result + ((scope() == null) ? 0 : scope().hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public void setBoilPoint(double value) {
		this.boilPoint = value;
	}

	@Override
	public void setCrystal(CrystalType value) {
		this.crystal = Objects.requireNonNull(value);
	}

	@Override
	public void setId(String value) {
		if(Objects.requireNonNull(value).isEmpty())
			throw new IllegalArgumentException("empty id");
		this.id = value;
	}

	@Override
	public void setMeltPoint(double value) {
		this.meltPoint = value;
	}

	@Override
	public void setName(String value) {
		this.name = Objects.requireNonNull(value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SubstanceImpl [");
		if (content != null) {
			builder.append("content=");
			builder.append(content);
			builder.append(", ");
		}
		if (dissolve != null) {
			builder.append("dissolve=");
			builder.append(dissolve);
			builder.append(", ");
		}
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
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (crystal != null) {
			builder.append("crystal=");
			builder.append(crystal);
		}
		builder.append("]");
		return builder.toString();
	}

}
