
package net.glease.chem.simple.datastructure.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.jdt.annotation.NonNull;

import net.glease.chem.simple.datastructure.Reactant;
import net.glease.chem.simple.datastructure.Reaction;
import net.glease.chem.simple.datastructure.Reagent;
import net.glease.chem.simple.datastructure.Resultant;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Equation", propOrder = { "conditionAndCatalystAndReactant" })
public class ReactionImpl implements Serializable, Reaction {

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	public static class CatalystImpl implements Serializable, Reaction.Catalyst {

		private final static long serialVersionUID = 1L;
		@XmlAttribute(name = "reagent")
		@XmlIDREF
		@XmlSchemaType(name = "IDREF")
		protected Reagent reagent;

		@Override
		public Reagent getReagent() {
			return reagent;
		}

		@Override
		public void setReagent(Reagent value) {
			this.reagent = value;
		}

	}

	private final static long serialVersionUID = 1L;
	@XmlElement(name = "condition", required = true, type = String.class)
	@NonNull
	protected List<String> conditions = new ArrayList<>();
	@XmlElement(name = "catalyst", required = true, type = ReactionImpl.CatalystImpl.class)
	@NonNull
	protected List<Reaction.Catalyst> catalysts = new ArrayList<>();
	@XmlElement(name = "reactant", required = true, type = ReactantImpl.class)
	@NonNull
	protected List<Reactant> reactants = new ArrayList<>();
	@XmlElement(name = "resultant", required = true, type = ResultantImpl.class)
	@NonNull
	protected List<Resultant> resultants = new ArrayList<>();
	@XmlAttribute(name = "temp", namespace = "http://glease.net/chem/simple/DataStructure")
	protected double temp = 298.15d;
	@XmlAttribute(name = "pressure")
	protected double pressure = 1.01e+5d;
	@XmlAttribute(name = "K")
	protected double k = Double.POSITIVE_INFINITY;
	@XmlAttribute(name = "heat")
	protected double heat = 0;
	@XmlAttribute(name = "speed", required = true)
	protected double speed;

	@XmlAttribute(name = "solvent")
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Reagent solvent;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ReactionImpl other = (ReactionImpl) obj;
		if (!catalysts.equals(other.catalysts)) {
			return false;
		}
		if (!conditions.equals(other.conditions)) {
			return false;
		}
		if (Double.doubleToLongBits(heat) != Double.doubleToLongBits(other.heat)) {
			return false;
		}
		if (Double.doubleToLongBits(k) != Double.doubleToLongBits(other.k)) {
			return false;
		}
		if (Double.doubleToLongBits(pressure) != Double.doubleToLongBits(other.pressure)) {
			return false;
		}
		if (!reactants.equals(other.reactants)) {
			return false;
		}
		if (!resultants.equals(other.resultants)) {
			return false;
		}
		if (solvent == null) {
			if (other.solvent != null) {
				return false;
			}
		} else if (!solvent.equals(other.solvent)) {
			return false;
		}
		if (Double.doubleToLongBits(speed) != Double.doubleToLongBits(other.speed)) {
			return false;
		}
		if (Double.doubleToLongBits(temp) != Double.doubleToLongBits(other.temp)) {
			return false;
		}
		return true;
	}

	@Override
	public List<Reaction.Catalyst> getCatalysts() {
		return catalysts;
	}

	@Override
	public List<String> getConditions() {
		return conditions;
	}

	@Override
	public double getHeat() {
		return heat;
	}

	@Override
	public double getK() {
		return k;
	}

	@Override
	public double getPressure() {
		return pressure;
	}

	@Override
	public List<Reactant> getReactants() {
		return reactants;
	}

	@Override
	public List<Resultant> getResultants() {
		return resultants;
	}

	@Override
	public Reagent getSolvent() {
		return solvent;
	}

	@Override
	public Double getSpeed() {
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
		result = prime * result + ((catalysts == null) ? 0 : catalysts.hashCode());
		result = prime * result + ((conditions == null) ? 0 : conditions.hashCode());
		long temp;
		temp = Double.doubleToLongBits(heat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(k);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(pressure);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((reactants == null) ? 0 : reactants.hashCode());
		result = prime * result + ((resultants == null) ? 0 : resultants.hashCode());
		result = prime * result + ((solvent == null) ? 0 : solvent.hashCode());
		temp = Double.doubleToLongBits(speed);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.temp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public void setHeat(Double value) {
		this.heat = value;
	}

	@Override
	public void setK(Double value) {
		this.k = value;
	}

	@Override
	public void setPressure(Double value) {
		this.pressure = value;
	}

	@Override
	public void setSolvent(Reagent value) {
		this.solvent = value;
	}

	@Override
	public void setSpeed(Double value) {
		this.speed = value;
	}

	@Override
	public void setTemp(Double value) {
		this.temp = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReactionImpl [");
		if (conditions != null) {
			builder.append("conditions=");
			builder.append(conditions);
			builder.append(", ");
		}
		if (catalysts != null) {
			builder.append("catalysts=");
			builder.append(catalysts);
			builder.append(", ");
		}
		if (reactants != null) {
			builder.append("reactants=");
			builder.append(reactants);
			builder.append(", ");
		}
		if (resultants != null) {
			builder.append("resultants=");
			builder.append(resultants);
			builder.append(", ");
		}
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
