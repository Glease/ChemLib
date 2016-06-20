package net.glease.chem.simple.datastructure;

import net.glease.chem.simple.util.Utilities;

/**
 * <p>
 * Dissolve could be think of a row of data in a database.
 * <h1><B>ALERT: THIS INTERFACE IS SUBJECT TO CHANGE IN THE NEXT RELEASES</B>
 * </h1>
 * <p>
 * Currently the field {@code s2TFunction} isn't working as we expected. We are
 * going to change it into a more realistic one.
 *
 * @author glease
 * @since 0.1
 */
public interface Dissolve extends Element<Substance, Dissolve> {

	/**
	 * Should be always effectively the same as {@code getSolvent().getId()}.
	 */
	@Override
	default String getId() {
		return getSolvent().getId();
	}

	/**
	 * Get the value of s2TFunction.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	@Deprecated
	String getS2TFunction();

	/**
	 * Get the value of solvent.
	 *
	 * @return possible object is {@link Reagent }
	 *
	 */
	Reagent getSolvent();

	@Override
	default boolean isBroken() {
		return Element.super.isBroken() || Utils.isBroken(this, getSolvent())
				|| Utilities.isNullOrEmpty(getS2TFunction());
	}

	@Override
	default ChemDatabase rootScope() {
		Substance s = scope();
		return s == null ? null : s.scope();
	}

	/**
	 * Set the value of s2TFunction.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 *
	 */
	@Deprecated
	void setS2TFunction(String value);

	/**
	 * Set the value of solvent.
	 *
	 * @param value
	 *            allowed object is {@link Reagent }
	 *
	 */
	void setSolvent(Reagent value);

}