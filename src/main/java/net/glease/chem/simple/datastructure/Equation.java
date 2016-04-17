
package net.glease.chem.simple.datastructure;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

/**
 * <p>
 * The Java class of Equation complex type.
 *
 * <p>
 * The following XML Schema snipplet contains the expect content of this class.
 *
 * <pre>
 * &lt;complexType name="Equation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="condition" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="catalyst" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="reagent" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="reactant" type="{http://glease.net/chem/simple/DataStructure}CountedReagent" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="resultant" type="{http://glease.net/chem/simple/DataStructure}CountedReagent" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://glease.net/chem/simple/DataStructure}temp"/>
 *       &lt;attribute name="pressure" type="{http://glease.net/chem/simple/DataStructure}PositiveDouble" default="1.01e+5" />
 *       &lt;attribute name="K" type="{http://glease.net/chem/simple/DataStructure}PositiveDouble" default="INF" />
 *       &lt;attribute name="heat" type="{http://www.w3.org/2001/XMLSchema}double" default="0" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Equation", propOrder = { "conditionAndCatalystAndReactant" })
public class Equation {
	private static final QName CONDITON_QNAME = new QName(ObjectFactory.NAMESPACE, "condtion");
	private static final QName CATALYST_QNAME = new QName(ObjectFactory.NAMESPACE, "catalyst");
	private static final QName RESULTANT_QNAME = new QName(ObjectFactory.NAMESPACE, "resultant");
	private static final QName REACTANT_QNAME = new QName(ObjectFactory.NAMESPACE, "reactant");
	protected List<JAXBElement<?>> conditionAndCatalystAndReactant;

	protected Double temp;

	protected Double pressure;

	protected Double k;

	protected Double heat;

	private SoftReference<Set<String>> conditions;
	private SoftReference<Set<Reagent>> catalysts;
	private SoftReference<Set<CountedReagent>> reactants;
	private SoftReference<Set<CountedReagent>> resultants;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Equation)) {
			return false;
		}
		Equation other = (Equation) obj;
		if (conditionAndCatalystAndReactant == null) {
			if (other.conditionAndCatalystAndReactant != null) {
				return false;
			}
		} else if (!conditionAndCatalystAndReactant.equals(other.conditionAndCatalystAndReactant)) {
			return false;
		}
		if (heat == null) {
			if (other.heat != null) {
				return false;
			}
		} else if (!heat.equals(other.heat)) {
			return false;
		}
		if (k == null) {
			if (other.k != null) {
				return false;
			}
		} else if (!k.equals(other.k)) {
			return false;
		}
		if (pressure == null) {
			if (other.pressure != null) {
				return false;
			}
		} else if (!pressure.equals(other.pressure)) {
			return false;
		}
		if (temp == null) {
			if (other.temp != null) {
				return false;
			}
		} else if (!temp.equals(other.temp)) {
			return false;
		}
		return true;
	}
	/**
	 * Get a lazily-initialized, {@link SoftReference}-cached {@link Set} of
	 * catalysts.
	 * 
	 * @return catalysts
	 */
	@XmlTransient
	public final Set<Reagent> getCatalysts() {
		Set<Reagent> tmp = catalysts.get();
		if (tmp == null) {
			catalysts = new SoftReference<>(
					tmp = getConditionAndCatalystAndReactant().stream().filter(e -> CATALYST_QNAME.equals(e.getName()))
							.map(e -> (Reagent) e.getValue()).collect(Collectors.toSet()));
		}
		return tmp;
	}
	/**
	 * Gets the value of the conditionAndCatalystAndReactant property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the conditionAndCatalystAndReactant property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 *
	 * <pre>
	 * getConditionAndCatalystAndReactant().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link JAXBElement }{@code <}{@link CountedReagent }{@code >}
	 * {@link JAXBElement }{@code <}{@link Reagent }{@code >} {@link JAXBElement
	 * }{@code <}{@link String }{@code >} {@link JAXBElement }{@code <}
	 * {@link CountedReagent }{@code >}
	 *
	 * <p>
	 * Not recommended for hand use except for editing, which is really not a
	 * purpose of this <b>SIMPLE</b> ChemLib.
	 *
	 * @see #getCatalysts()
	 * @see #getConditions()
	 * @see #getReactants()
	 * @see #getResultants()
	 */
	@XmlElementRefs({
			@XmlElementRef(name = "catalyst", namespace = ObjectFactory.NAMESPACE, type = JAXBElement.class, required = false),
			@XmlElementRef(name = "condition", namespace = ObjectFactory.NAMESPACE, type = JAXBElement.class, required = false),
			@XmlElementRef(name = "resultant", namespace = ObjectFactory.NAMESPACE, type = JAXBElement.class, required = false),
			@XmlElementRef(name = "reactant", namespace = ObjectFactory.NAMESPACE, type = JAXBElement.class, required = false) })
	public List<JAXBElement<?>> getConditionAndCatalystAndReactant() {
		if (conditionAndCatalystAndReactant == null) {
			conditionAndCatalystAndReactant = new ArrayList<JAXBElement<?>>();
		}
		return this.conditionAndCatalystAndReactant;
	}
	/**
	 * Get a lazily-initialized, {@link SoftReference}-cached {@link Set} of
	 * conditions.
	 * 
	 * @return conditions
	 */
	@XmlTransient
	public final Set<String> getConditions() {
		Set<String> tmp = conditions.get();
		return tmp == null
				? getConditionAndCatalystAndReactant().stream().filter(e -> CONDITON_QNAME.equals(e.getName()))
						.map(e -> (String) e.getValue()).collect(Collectors.toSet())
				: tmp;
	}

	/**
	 * Get the value of heat.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	@XmlAttribute(name = "heat")
	public double getHeat() {
		if (heat == null) {
			return 0.0D;
		} else {
			return heat;
		}
	}

	/**
	 * Get the value of k.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	@XmlAttribute(name = "K")
	public double getK() {
		if (k == null) {
			return java.lang.Double.POSITIVE_INFINITY;
		} else {
			return k;
		}
	}

	/**
	 * Get the value of pressure.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	@XmlAttribute(name = "pressure")
	public double getPressure() {
		if (pressure == null) {
			return 101000.0D;
		} else {
			return pressure;
		}
	}

	/**
	 * Get a lazily-initialized, {@link SoftReference}-cached {@link Set} of
	 * reactants.
	 * 
	 * @return reactants
	 */
	@XmlTransient
	public final Set<CountedReagent> getReactants() {
		Set<CountedReagent> tmp = reactants.get();
		if (tmp == null) {
			reactants = new SoftReference<>(
					getConditionAndCatalystAndReactant().stream().filter(e -> REACTANT_QNAME.equals(e.getName()))
							.map(e -> (CountedReagent) e.getValue()).collect(Collectors.toSet()));
		}
		return tmp;
	}

	/**
	 * Get a lazily-initialized, {@link SoftReference}-cached {@link Set} of
	 * resultants.
	 * 
	 * @return resultants
	 */
	@XmlTransient
	public final Set<CountedReagent> getResultants() {
		Set<CountedReagent> tmp = resultants.get();
		resultants = new SoftReference<>(
				getConditionAndCatalystAndReactant().stream().filter(e -> RESULTANT_QNAME.equals(e.getName()))
						.map(e -> (CountedReagent) e.getValue()).collect(Collectors.toSet()));
		return tmp;
	}

	/**
	 * Get the value of temp.
	 *
	 * @return possible object is {@link Double }
	 *
	 */
	@XmlAttribute(name = "temp", namespace = ObjectFactory.NAMESPACE)
	public double getTemp() {
		if (temp == null) {
			return 298.15D;
		} else {
			return temp;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getCatalysts() == null ? 0 : getCatalysts().hashCode());
		result = prime * result + (getConditions() == null ? 0 : getConditions().hashCode());
		result = prime * result + (getReactants() == null ? 0 : getReactants().hashCode());
		result = prime * result + (getResultants() == null ? 0 : getResultants().hashCode());
		result = prime * result + (heat == null ? 0 : heat.hashCode());
		result = prime * result + (k == null ? 0 : k.hashCode());
		result = prime * result + (pressure == null ? 0 : pressure.hashCode());
		result = prime * result + (temp == null ? 0 : temp.hashCode());
		return result;
	}

	/**
	 * Set the value of heat.
	 *
	 * @param value
	 *            allowed object is {@link Double }
	 *
	 */
	public void setHeat(Double value) {
		this.heat = value;
	}

	/**
	 * Set the value of k.
	 *
	 * @param value
	 *            allowed object is {@link Double }
	 *
	 */
	public void setK(Double value) {
		this.k = value;
	}

	/**
	 * Set the value of pressure.
	 *
	 * @param value
	 *            allowed object is {@link Double }
	 *
	 */
	public void setPressure(Double value) {
		this.pressure = value;
	}

	/**
	 * Set the value of temp.
	 *
	 * @param value
	 *            allowed object is {@link Double }
	 *
	 */
	public void setTemp(Double value) {
		this.temp = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Equation [getConditions()=");
		builder.append(getConditions());
		builder.append(", getCatalysts()=");
		builder.append(getCatalysts());
		builder.append(", getResultants()=");
		builder.append(getResultants());
		builder.append(", getReactants()=");
		builder.append(getReactants());
		builder.append(", getTemp()=");
		builder.append(getTemp());
		builder.append(", getPressure()=");
		builder.append(getPressure());
		builder.append(", getK()=");
		builder.append(getK());
		builder.append(", getHeat()=");
		builder.append(getHeat());
		builder.append("]");
		return builder.toString();
	}

}
