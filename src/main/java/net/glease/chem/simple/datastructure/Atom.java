
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * The Java class of Atom. Atom is just a certain kind of real atom. Most of the
 * properties of this class must be filled before coming out of the factory,
 * except those could be inferred from other existing atoms. If inference
 * failed, an exception should be thrown (like {@link NormalizationException}
 * during the {@link ChemDatabase#normalize()}).
 *
 * @author glease
 * @since 0.1
 */
public interface Atom extends Element<ChemDatabase> {

	/**
	 * Get the value of average mol mass. Can be inferred.
	 * 
	 */
	double getAverageMolMass();

	/**
	 * Automatically generate an ID for the atom in the scope. It should
	 * effectively be {@code "atom" + getIndex() + "-" + getMolMass()} in all
	 * cases.
	 */
	@Override
	default String getId() {
		return "atom" + getIndex() + "-" + getMolMass();
	}

	/**
	 * Get the value of index.
	 * 
	 */
	int getIndex();

	/**
	 * Get the value of localizedName. Can be inferred.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getLocalizedName();

	/**
	 * Get the value of molMass.
	 * 
	 * @return possible object is {@link int }
	 * 
	 */
	int getMolMass();

	/**
	 * Get the value of symbol. Can be inferred.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	String getSymbol();

	/**
	 * Set the value of average mol mass.
	 * 
	 */
	void setAverageMolMass(double value);

	/**
	 * Set the value of index.
	 * 
	 */
	void setIndex(int value);

	/**
	 * Set the value of localizedName.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setLocalizedName(String value);

	/**
	 * Set the value of molMass.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setMolMass(int value);

	/**
	 * Set the value of symbol.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	void setSymbol(String value);

}
