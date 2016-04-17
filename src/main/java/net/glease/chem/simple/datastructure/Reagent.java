
package net.glease.chem.simple.datastructure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Reagent is something you can obtain in the lab, or
 * 				somewhere else in the real world.
 * 
 * 				Reagents and substances differ in
 * 				that reagents have states like
 * 				temperature, solvent, state, etc.,
 * 				while substance never has such
 * 				kind of states.
 * 			
 * 
 * <p>
 * The Java class of Reagent complex type.
 *
 * <p>
 * The following XML Schema snipplet contains the expect content of this class.
 *
 * <pre>
 * &lt;complexType name="Reagent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="substance" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="solvent" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="concentration" type="{http://glease.net/chem/simple/DataStructure}Percentage" default="100" />
 *       &lt;attribute name="state" type="{http://glease.net/chem/simple/DataStructure}ReagentState" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Reagent")
public class Reagent {

	protected Substance substance;
	protected Substance solvent;
	protected Float concentration;
	protected ReagentState state;
	protected String name;
	protected String id;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Reagent)) {
			return false;
		}
		Reagent other = (Reagent) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	/**
	 * Get the value of concentration.
	 * 
	 * @return possible object is {@link Float }
	 * 
	 */
	@XmlAttribute(name = "concentration")
	public float getConcentration() {
		if (concentration == null) {
			return 100.0F;
		} else {
			return concentration;
		}
	}

	/**
	 * Get the value of id.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlAttribute(name = "id", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	public String getId() {
		return id;
	}

	/**
	 * Get the value of name.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	/**
	 * Get the value of solvent.
	 * 
	 * @return possible object is {@link Object }
	 * 
	 */
	@XmlAttribute(name = "solvent")
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	public Substance getSolvent() {
		return solvent;
	}

	/**
	 * Get the value of state.
	 * 
	 * @return possible object is {@link ReagentState }
	 * 
	 */
	@XmlAttribute(name = "state")
	public ReagentState getState() {
		return state;
	}

	/**
	 * Get the value of substance.
	 * 
	 * @return possible object is {@link Substance }
	 * 
	 */
	@XmlAttribute(name = "substance", required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	public Substance getSubstance() {
		return substance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Set the value of concentration.
	 * 
	 * @param value
	 *            allowed object is {@link Float }
	 * 
	 */
	public void setConcentration(Float value) {
		this.concentration = value;
	}

	/**
	 * Set the value of id.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setId(String value) {
		this.id = value;
	}

	/**
	 * Set the value of name.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Set the value of solvent.
	 * 
	 * @param value
	 *            allowed object is {@link Substance }
	 * 
	 */
	public void setSolvent(Substance value) {
		this.solvent = value;
	}

	/**
	 * Set the value of state.
	 * 
	 * @param value
	 *            allowed object is {@link ReagentState }
	 * 
	 */
	public void setState(ReagentState value) {
		this.state = value;
	}

	/**
	 * Set the value of substance.
	 * 
	 * @param value
	 *            allowed object is {@link Substance }
	 * 
	 */
	public void setSubstance(Substance value) {
		this.substance = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Reagent [getSubstance()=");
		builder.append(getSubstance());
		builder.append(", getSolvent()=");
		builder.append(getSolvent());
		builder.append(", getConcentration()=");
		builder.append(getConcentration());
		builder.append(", getState()=");
		builder.append(getState());
		builder.append(", getName()=");
		builder.append(getName());
		builder.append(", getId()=");
		builder.append(getId());
		builder.append("]");
		return builder.toString();
	}

}
