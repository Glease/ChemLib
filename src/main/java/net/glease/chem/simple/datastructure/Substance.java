package net.glease.chem.simple.datastructure;

import java.util.Set;

import net.glease.chem.simple.scoping.IScope;
import net.glease.chem.simple.scoping.IScoped;
import net.glease.chem.simple.scoping.ScopeException;

/**
 * <p>
 * The Java class of Substance. Substance is something you know on your
 * textbook, like you know there is something called Carbon Dioxide.
 *
 * @author glease
 * @since 0.1
 *
 */
public interface Substance extends Element<ChemDatabase>, IScope<ChemDatabase, Substance> {

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
	 * Get a set containing all {@link SubstanceContent content} in this
	 * {@link Substance}. The set is unmodifiable. Addition/removal to this set
	 * should be done with {@link IScoped#bind(IScope) bind(this)} or
	 * {@link IScoped#bind(IScope) bind(null)}.
	 * 
	 * @return possible object is {@link Set&lt;SubstanceContent> }
	 */
	Set<SubstanceContent> getContent();

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
	 * Get a set containing all {@link Dissolve dissolve data} in this
	 * {@link Substance}. The set is unmodifiable. Addition/removal to this set
	 * should be done with {@link IScoped#bind(IScope) bind(this)} or
	 * {@link IScoped#bind(IScope) bind(null)}.
	 * 
	 * @return possible object is {@link Set&lt;Dissolve> }
	 */
	Set<Dissolve> getDissolve();

	/**
	 * 
	 * Represents a temperature, measured in K, not Censils
	 * 
	 * 
	 * @return possible object is {@link double }
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

	@Override
	default void onBind(IScoped<Substance> o) {
		if (o instanceof Dissolve)
			getDissolve().add((Dissolve) o);
		else if (o instanceof SubstanceContent)
			getContent().add((SubstanceContent) o);
		else
			throw new ScopeException("Element not identified.", this, o);
		IScope.super.onBind(o);
	}

	@Override
	default void onUnbind(IScoped<Substance> o) {
		if (o instanceof Dissolve)
			if (!getDissolve().remove(o))
				throw new ScopeException("Not binded to this scope", this, o);
			else if (o instanceof SubstanceContent)
				if (!getContent().remove(o))
					throw new ScopeException("Not binded to this scope", this, o);
				else
					throw new ScopeException("Element not identified.", this, o);
		IScope.super.onUnbind(o);
	}

	/**
	 * Set the value of boilPoint.
	 * 
	 * @param value
	 *            allowed object is {@link double }
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
	 * @param value
	 *            allowed object is {@link String }
	 */
	void setId(String value);

	/**
	 * Set the value of meltPoint.
	 * 
	 * @param value
	 *            allowed object is {@link double }
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
