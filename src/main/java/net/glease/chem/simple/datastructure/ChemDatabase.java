
package net.glease.chem.simple.datastructure;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.common.reflect.TypeToken;

import net.glease.chem.simple.util.UUIDAdaptor;

/**
 * The Java class of ChemDatabase complex type.
 *
 * <p>
 * The following XML Schema snipplet contains the expect content of this class.
 *
 * <pre>
 * &lt;complexType name="ChemDatabase">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all minOccurs="0">
 *         &lt;element name="substances" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="substance" type="{http://glease.net/chem/simple/DataStructure}Substance" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="equations" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="equation" type="{http://glease.net/chem/simple/DataStructure}Equation" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="atoms" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="atom" type="{http://glease.net/chem/simple/DataStructure}Atom" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="reagents" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="reagent" type="{http://glease.net/chem/simple/DataStructure}Reagent" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *       &lt;attribute name="UUID" use="required" type="{http://glease.net/chem/simple/DataStructure}UUID" />
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="info" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ChemDatabase", propOrder = {

})
public class ChemDatabase {

	/**
	 * The Java class of anonymous complex type.
	 * 
	 * <p>
	 * The following XML Schema snipplet contains the expect content of this
	 * class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="atom" type="{http://glease.net/chem/simple/DataStructure}Atom" maxOccurs="unbounded"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.PROPERTY)
	@XmlType(name = "", propOrder = { "atom" })
	public static class Atoms {

		protected List<Atom> atom;

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Atoms)) {
				return false;
			}
			Atoms other = (Atoms) obj;
			if (atom == null) {
				if (other.atom != null) {
					return false;
				}
			} else if (!atom.equals(other.atom)) {
				return false;
			}
			return true;
		}

		/**
		 * Gets the value of the atom property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a
		 * snapshot. Therefore any modification you make to the returned list
		 * will be present inside the JAXB object. This is why there is not a
		 * <CODE>set</CODE> method for the atom property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getAtom().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link Atom
		 * }
		 * 
		 * 
		 */
		@XmlElement(required = true)
		public List<Atom> getAtom() {
			if (atom == null) {
				atom = new ArrayList<Atom>();
			}
			return this.atom;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (atom == null ? 0 : atom.hashCode());
			return result;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Atoms [getAtom()=");
			builder.append(getAtom());
			builder.append("]");
			return builder.toString();
		}

	}

	/**
	 * The Java class of anonymous complex type.
	 * 
	 * <p>
	 * The following XML Schema snipplet contains the expect content of this
	 * class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="equation" type="{http://glease.net/chem/simple/DataStructure}Equation" maxOccurs="unbounded"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.PROPERTY)
	@XmlType(name = "", propOrder = { "equation" })
	public static class Equations {

		protected List<Equation> equation;

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Equations)) {
				return false;
			}
			Equations other = (Equations) obj;
			if (equation == null) {
				if (other.equation != null) {
					return false;
				}
			} else if (!equation.equals(other.equation)) {
				return false;
			}
			return true;
		}

		/**
		 * Gets the value of the equation property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a
		 * snapshot. Therefore any modification you make to the returned list
		 * will be present inside the JAXB object. This is why there is not a
		 * <CODE>set</CODE> method for the equation property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getEquation().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list
		 * {@link Equation }
		 * 
		 * 
		 */
		@XmlElement(required = true)
		public List<Equation> getEquation() {
			if (equation == null) {
				equation = new ArrayList<Equation>();
			}
			return this.equation;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (equation == null ? 0 : equation.hashCode());
			return result;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Equations [getEquation()=");
			builder.append(getEquation());
			builder.append("]");
			return builder.toString();
		}

	}

	/**
	 * The Java class of anonymous complex type.
	 * 
	 * <p>
	 * The following XML Schema snipplet contains the expect content of this
	 * class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="reagent" type="{http://glease.net/chem/simple/DataStructure}Reagent" maxOccurs="unbounded"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.PROPERTY)
	@XmlType(name = "", propOrder = { "reagent" })
	public static class Reagents {

		protected List<Reagent> reagent;

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Reagents)) {
				return false;
			}
			Reagents other = (Reagents) obj;
			if (reagent == null) {
				if (other.reagent != null) {
					return false;
				}
			} else if (!reagent.equals(other.reagent)) {
				return false;
			}
			return true;
		}

		/**
		 * Gets the value of the reagent property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a
		 * snapshot. Therefore any modification you make to the returned list
		 * will be present inside the JAXB object. This is why there is not a
		 * <CODE>set</CODE> method for the reagent property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getReagent().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list
		 * {@link Reagent }
		 * 
		 * 
		 */
		@XmlElement(required = true)
		public List<Reagent> getReagent() {
			if (reagent == null) {
				reagent = new ArrayList<Reagent>();
			}
			return this.reagent;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (reagent == null ? 0 : reagent.hashCode());
			return result;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Reagents [getReagent()=");
			builder.append(getReagent());
			builder.append("]");
			return builder.toString();
		}

	}

	/**
	 * The Java class of anonymous complex type.
	 * 
	 * <p>
	 * The following XML Schema snipplet contains the expect content of this
	 * class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="substance" type="{http://glease.net/chem/simple/DataStructure}Substance" maxOccurs="unbounded"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.PROPERTY)
	@XmlType(name = "", propOrder = { "substance" })
	public static class Substances {

		protected List<Substance> substance;

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Substances)) {
				return false;
			}
			Substances other = (Substances) obj;
			if (substance == null) {
				if (other.substance != null) {
					return false;
				}
			} else if (!substance.equals(other.substance)) {
				return false;
			}
			return true;
		}

		/**
		 * Gets the value of the substance property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a
		 * snapshot. Therefore any modification you make to the returned list
		 * will be present inside the JAXB object. This is why there is not a
		 * <CODE>set</CODE> method for the substance property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getSubstance().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list
		 * {@link Substance }
		 * 
		 * 
		 */
		@XmlElement(required = true)
		public List<Substance> getSubstance() {
			if (substance == null) {
				substance = new ArrayList<Substance>();
			}
			return this.substance;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (substance == null ? 0 : substance.hashCode());
			return result;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Substances [getSubstance()=");
			builder.append(getSubstance());
			builder.append("]");
			return builder.toString();
		}

	}

	protected ChemDatabase.Substances substances;
	protected ChemDatabase.Equations equations;
	protected ChemDatabase.Atoms atoms;
	protected ChemDatabase.Reagents reagents;

	protected UUID uuid;
	protected String version;
	protected String info;

	private ChemDatabaseFinder finder;

	public ChemDatabaseFinder finder() {
		if (finder == null)
			finder = new ChemDatabaseFinder(this);
		return finder;
	}

	/**
	 * <b>USING THIS METHOD REQUIRES YOU INCLUDE GENERIC INFO IN YOUR CLASS FILE! IF YOUR BUILDING 
	 * PROCESS WILL REMOVE IT FOR WHATEVER REASON, USE METHOD IN {@link #finder() FINDER}!</b><p>
	 * A convenient method to find various item.
	 * @param test a predicate to match items
	 * @return an immutable set of matching items.
	 */
	@SuppressWarnings("unchecked")
	public <T> Set<T> find(Predicate<T> test) {
		for (Type type : test.getClass().getGenericInterfaces()) {
			if (!(Predicate.class.isAssignableFrom(TypeToken.of(type).getRawType())))
				continue;
			Class<?> t = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];

			if (Equation.class.equals(t)) {
				return (Set<T>) finder().findEquation((Predicate<Equation>) test);
			} else if (Reagent.class.equals(t)) {
				return (Set<T>) finder().findEquation((Predicate<Equation>) test);
			} else if (Substance.class.equals(t)) {
				return (Set<T>) finder().findEquation((Predicate<Equation>) test);
			} else if (Atom.class.equals(t)) {
				return (Set<T>) finder().findEquation((Predicate<Equation>) test);
			} else {
				throw new IllegalArgumentException("No such type to find!");
			}
		}

		throw new IllegalArgumentException("No such type to find!");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ChemDatabase)) {
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

	/**
	 * Get the value of atoms.
	 * 
	 * @return possible object is {@link ChemDatabase.Atoms }
	 * 
	 */
	public ChemDatabase.Atoms getAtoms() {
		return atoms;
	}

	/**
	 * Get the value of equations.
	 * 
	 * @return possible object is {@link ChemDatabase.Equations }
	 * 
	 */
	public ChemDatabase.Equations getEquations() {
		return equations;
	}

	/**
	 * Get the value of info.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlAttribute(name = "info")
	public String getInfo() {
		return info;
	}

	/**
	 * Get the value of reagents.
	 * 
	 * @return possible object is {@link ChemDatabase.Reagents }
	 * 
	 */
	public ChemDatabase.Reagents getReagents() {
		return reagents;
	}

	/**
	 * Get the value of substances.
	 * 
	 * @return possible object is {@link ChemDatabase.Substances }
	 * 
	 */
	public ChemDatabase.Substances getSubstances() {
		return substances;
	}

	/**
	 * Get the value of uuid.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlJavaTypeAdapter(UUIDAdaptor.class)
	@XmlAttribute(name = "UUID", required = true)
	public UUID getUUID() {
		return uuid;
	}

	/**
	 * Get the value of version.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlAttribute(name = "version", required = true)
	public String getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (uuid == null ? 0 : uuid.hashCode());
		result = prime * result + (version == null ? 0 : version.hashCode());
		return result;
	}

	/**
	 * Set the value of atoms.
	 * 
	 * @param value
	 *            allowed object is {@link ChemDatabase.Atoms }
	 * 
	 */
	public void setAtoms(ChemDatabase.Atoms value) {
		this.atoms = value;
	}

	/**
	 * Set the value of equations.
	 * 
	 * @param value
	 *            allowed object is {@link ChemDatabase.Equations }
	 * 
	 */
	public void setEquations(ChemDatabase.Equations value) {
		this.equations = value;
	}

	/**
	 * Set the value of info.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setInfo(String value) {
		this.info = value;
	}

	/**
	 * Set the value of reagents.
	 * 
	 * @param value
	 *            allowed object is {@link ChemDatabase.Reagents }
	 * 
	 */
	public void setReagents(ChemDatabase.Reagents value) {
		this.reagents = value;
	}

	/**
	 * Set the value of substances.
	 * 
	 * @param value
	 *            allowed object is {@link ChemDatabase.Substances }
	 * 
	 */
	public void setSubstances(ChemDatabase.Substances value) {
		this.substances = value;
	}

	/**
	 * Set the value of uuid.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUUID(UUID value) {
		this.uuid = value;
	}

	/**
	 * Set the value of version.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setVersion(String value) {
		this.version = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChemDatabase [getSubstances()=");
		builder.append(getSubstances());
		builder.append(", getEquations()=");
		builder.append(getEquations());
		builder.append(", getAtoms()=");
		builder.append(getAtoms());
		builder.append(", getReagents()=");
		builder.append(getReagents());
		builder.append(", getUUID()=");
		builder.append(getUUID());
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append(", getInfo()=");
		builder.append(getInfo());
		builder.append("]");
		return builder.toString();
	}

}
