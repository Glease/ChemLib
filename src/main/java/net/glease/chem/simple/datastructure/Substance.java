
package net.glease.chem.simple.datastructure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.builder.CompareToBuilder;

import net.glease.chem.simple.util.ListComparator;

/**
 * Substance is something you know on your textbook, like you know there is
 * something called Carbon Dioxide.
 * <p>
 * The Java class of Substance complex type.
 *
 * <p>
 * The following XML Schema snipplet contains the expect content of this class.
 *
 * <pre>
 * &lt;complexType name="Substance">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="atom" type="{http://glease.net/chem/simple/DataStructure}SubstanceContent" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="dissovle" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="solvent" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *                 &lt;attribute name="s2TFunction" use="required" type="{http://glease.net/chem/simple/DataStructure}Expression" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="meltPoint" type="{http://glease.net/chem/simple/DataStructure}Temperature" />
 *       &lt;attribute name="boilPoint" type="{http://glease.net/chem/simple/DataStructure}Temperature" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="crystal" type="{http://glease.net/chem/simple/DataStructure}CrystalType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Substance", propOrder = { "atom", "dissovle" })
public class Substance {

	public enum SubstanceComparator implements Comparator<Substance> {
		INSTANCE;
		private static final ListComparator<SubstanceContent> LIST_COMP_CONTENT = new ListComparator<>(true, true);

		@Override
		public int compare(Substance o1, Substance o2) {
			CompareToBuilder b = new CompareToBuilder();
			b.append(o1.getAtom().size(), o2.getAtom().size());
			b.appendSuper(LIST_COMP_CONTENT.compare(o1.getAtom(), o2.getAtom()));
			b.append(o1.getName(), o2.getName());
			b.append(o1.getId(), o2.getId());
			return b.toComparison();
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
	 *       &lt;attribute name="solvent" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
	 *       &lt;attribute name="s2TFunction" use="required" type="{http://glease.net/chem/simple/DataStructure}Expression" />
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.PROPERTY)
	@XmlType(name = "")
	public static class Dissovle {
		public enum DissovleComparator implements Comparator<Dissovle> {
			INSTANCE;

			@Override
			public int compare(Dissovle o1, Dissovle o2) {
				CompareToBuilder b = new CompareToBuilder();
				b.appendSuper(SubstanceComparator.INSTANCE.compare(o1.getSolvent(), o2.getSolvent()));
				b.append(o1.getS2TFunction(), o2.getS2TFunction());
				return b.toComparison();
			}
		}

		protected Substance solvent;
		protected String s2TFunction;

		/**
		 * Get the value of s2TFunction.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		@XmlAttribute(name = "s2TFunction", required = true)
		public String getS2TFunction() {
			return s2TFunction;
		}

		/**
		 * Get the value of solvent.
		 * 
		 * @return possible object is {@link Substance }
		 * 
		 */
		@XmlAttribute(name = "solvent")
		@XmlIDREF
		@XmlSchemaType(name = "IDREF")
		public Substance getSolvent() {
			return solvent;
		}

		/**
		 * Set the value of s2TFunction.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setS2TFunction(String value) {
			this.s2TFunction = value;
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

	}

	protected List<SubstanceContent> atom;
	protected List<Substance.Dissovle> dissovle;
	protected String name;
	protected Double meltPoint;
	protected Double boilPoint;
	protected String id;

	protected CrystalType crystal;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Substance)) {
			return false;
		}
		Substance other = (Substance) obj;
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
	 * Gets the value of the atom property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
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
	 * Objects of the following type(s) are allowed in the list
	 * {@link SubstanceContent }
	 * 
	 * 
	 */
	@XmlElement(name = "atom")
	public List<SubstanceContent> getAtom() {
		if (atom == null) {
			atom = new ArrayList<SubstanceContent>();
		}
		return this.atom;
	}

	/**
	 * Get the value of boilPoint.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	@XmlAttribute(name = "boilPoint")
	public Double getBoilPoint() {
		return boilPoint;
	}

	/**
	 * Get the value of crystal.
	 * 
	 * @return possible object is {@link CrystalType }
	 * 
	 */
	@XmlAttribute(name = "crystal")
	public CrystalType getCrystal() {
		return crystal;
	}

	/**
	 * Gets the value of the dissovle property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the dissovle property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getDissovle().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link Substance.Dissovle }
	 * 
	 * 
	 */
	@XmlElement(name = "dissolve")
	public List<Substance.Dissovle> getDissovle() {
		if (dissovle == null) {
			dissovle = new ArrayList<Substance.Dissovle>();
		}
		return this.dissovle;
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
	 * Get the value of meltPoint.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	@XmlAttribute(name = "meltPoint")
	public Double getMeltPoint() {
		return meltPoint;
	}

	/**
	 * Get the value of name.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlAttribute(name = "name", required = true)
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Set the value of boilPoint.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setBoilPoint(Double value) {
		this.boilPoint = value;
	}

	/**
	 * Set the value of crystal.
	 * 
	 * @param value
	 *            allowed object is {@link CrystalType }
	 * 
	 */
	public void setCrystal(CrystalType value) {
		this.crystal = value;
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
	 * Set the value of meltPoint.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setMeltPoint(Double value) {
		this.meltPoint = value;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Substance [getAtom()=");
		builder.append(getAtom());
		builder.append(", getDissovle()=");
		builder.append(getDissovle());
		builder.append(", getName()=");
		builder.append(getName());
		builder.append(", getMeltPoint()=");
		builder.append(getMeltPoint());
		builder.append(", getBoilPoint()=");
		builder.append(getBoilPoint());
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getCrystal()=");
		builder.append(getCrystal());
		builder.append("]");
		return builder.toString();
	}

}
