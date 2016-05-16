package net.glease.chem.simple.datastructure;

/**
 * <p>
 * Dissolve could be think of a row of data in a database.
 * 
 * @author glease
 * @since 1.0
 * 
 */
public interface Dissolve extends Element<Substance> {

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
	String getS2TFunction();

	/**
	 * Get the value of solvent.
	 * 
	 * @return possible object is {@link Reagent }
	 * 
	 */
	Reagent getSolvent();

	/**
	 * Set the value of s2TFunction.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
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