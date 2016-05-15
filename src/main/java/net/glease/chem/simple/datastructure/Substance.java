
package net.glease.chem.simple.datastructure;

import java.util.Set;

import net.glease.chem.simple.scoping.IScope;
import net.glease.chem.simple.scoping.IScoped;
import net.glease.chem.simple.scoping.ScopeException;



/**
 *
 * Substance is something you know on your textbook, like you know there is
 * something called Carbon Dioxide.
 * 
 *
 * <p>
 * The Java class of Substance.
 *
 * <p>
 * The following XML Schema snippet contains the expect content of this class.
 *
 * <pre>
 * &lt;complexType name="Substance">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="content" type="{http://glease.net/chem/simple/DataStructure}SubstanceContent" maxOccurs="unbounded"/>
 *         &lt;element name="dissolve" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute ref="{http://glease.net/chem/simple/DataStructure}solvent use="required""/>
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
public interface Substance extends Element<ChemDatabase>, IScope<ChemDatabase, Substance> {

	/**
	 * Gets the value of the content property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the content property.
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
	Set<SubstanceContent> getContent();

	@Override
	default void onBind(IScoped<Substance> o) {
		if(o instanceof Dissolve)
			getDissolve().add((Dissolve) o);
		else if(o instanceof SubstanceContent)
			getContent().add((SubstanceContent) o);
		else 
			throw new ScopeException("Element not identified.", this, o);
		IScope.super.onBind(o);
	}

	@Override
	default void onUnbind(IScoped<Substance> o) {
		if(o instanceof Dissolve)
			if(!getDissolve().remove(o))
				throw new ScopeException("Not binded to this scope", this, o);
		else if(o instanceof SubstanceContent)
			if(!getContent().remove(o))
				throw new ScopeException("Not binded to this scope", this, o);
		else
			throw new ScopeException("Element not identified.", this, o);
		IScope.super.onUnbind(o);
	}

	/**
	 * 
	 * Represents a temperature, measured in K, not Censils
	 * 
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	double getBoilPoint();

	/**
	 * 
	 * For mixed crystal, use none.
	 * 
	 * 
	 * @return possible object is {@link CrystalType }
	 * 
	 */
	CrystalType getCrystal();

	/**
	 * Gets the value of the dissolve property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the dissolve property.
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
	 * {@link Dissolve }
	 * 
	 * 
	 */
	Set<Dissolve> getDissolve();

	/**
	 * 
	 * Represents a temperature, measured in K, not Censils
	 * 
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	double getMeltPoint();

	/**
	 * Get the value of name.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getName();

	/**
	 * Set the value of boilPoint.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setBoilPoint(double value);

	/**
	 * Set the value of crystal.
	 * 
	 * @param value
	 *            allowed object is {@link CrystalType }
	 * 
	 */
	void setCrystal(CrystalType value);
	
	/**
	 * Set the value of id.
	 * 
	 */
	void setId(String value);

	/**
	 * Set the value of meltPoint.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setMeltPoint(double value);

	/**
	 * Set the value of name.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setName(String value);

}
