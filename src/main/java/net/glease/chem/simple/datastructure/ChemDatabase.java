
package net.glease.chem.simple.datastructure;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.glease.chem.simple.datastructure.impl.AtomImpl;
import net.glease.chem.simple.datastructure.impl.EquationImpl;
import net.glease.chem.simple.datastructure.impl.ReagentImpl;
import net.glease.chem.simple.datastructure.impl.SubstanceImpl;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChemDatabase", propOrder = {

})
public class ChemDatabase implements Serializable {

	private final static long serialVersionUID = 1L;

	@XmlElementWrapper(name = "substances")
	@XmlElement(type = SubstanceImpl.class)
	protected List<Substance> substances;

	@XmlElementWrapper(name = "equations")
	@XmlElement(type = EquationImpl.class)
	protected List<Equation> equations;

	@XmlElementWrapper(name = "atoms")
	@XmlElement(name = "atom", type = AtomImpl.class)
	protected List<Atom> atoms;

	@XmlElementWrapper(name = "reagents")
	@XmlElement(type = ReagentImpl.class)
	protected List<Reagent> reagents;

	@XmlAttribute(name = "UUID", required = true)
	@XmlJavaTypeAdapter(UUIDAdapter.class)
	protected UUID uuid;

	@XmlAttribute(name = "version", required = true)
	protected String version;

	@XmlAttribute(name = "info")
	protected String info;

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
		ChemDatabase other = (ChemDatabase) obj;
		if (uuid == null) {
			if (other.uuid != null) {
				return false;
			}
		} else if (!uuid.equals(other.uuid)) {
			return false;
		}
		if (version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!version.equals(other.version)) {
			return false;
		}
		return true;
	}

	public List<Atom> getAtoms() {
		return atoms;
	}

	public List<Equation> getEquations() {
		return equations;
	}

	public String getInfo() {
		return info;
	}

	public List<Reagent> getReagents() {
		return reagents;
	}

	public List<Substance> getSubstances() {
		return substances;
	}

	public UUID getUUID() {
		return uuid;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	public void setAtoms(List<Atom> value) {
		this.atoms = value;
	}

	public void setEquations(List<Equation> value) {
		this.equations = value;
	}

	public void setInfo(String value) {
		this.info = value;
	}

	public void setReagents(List<Reagent> value) {
		this.reagents = value;
	}

	public void setSubstances(List<Substance> value) {
		this.substances = value;
	}

	public void setUUID(UUID value) {
		this.uuid = value;
	}

	public void setVersion(String value) {
		this.version = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChemDatabaseImpl [");
		if (substances != null) {
			builder.append("substances=");
			builder.append(substances);
			builder.append(", ");
		}
		if (equations != null) {
			builder.append("equations=");
			builder.append(equations);
			builder.append(", ");
		}
		if (atoms != null) {
			builder.append("atoms=");
			builder.append(atoms);
			builder.append(", ");
		}
		if (reagents != null) {
			builder.append("reagents=");
			builder.append(reagents);
			builder.append(", ");
		}
		if (uuid != null) {
			builder.append("uuid=");
			builder.append(uuid);
			builder.append(", ");
		}
		if (version != null) {
			builder.append("version=");
			builder.append(version);
			builder.append(", ");
		}
		if (info != null) {
			builder.append("info=");
			builder.append(info);
		}
		builder.append("]");
		return builder.toString();
	}

}
