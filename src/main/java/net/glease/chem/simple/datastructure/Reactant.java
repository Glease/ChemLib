
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * The Java class of Reactant.
 *
 * @author glease
 * @since 0.1
 *
 */
public interface Reactant extends ReactionComponent<Reactant> {

	/**
	 * Get the value of purity. valid range :{@code (0, 100]}
	 *
	 * @return possible object is {@link double }
	 *
	 */
	double getPurity();

	@Override
	default boolean isBroken() {
		return ReactionComponent.super.isBroken() || getPurity() <= 0 || getPurity() > 100;
	}

	/**
	 * Set the value of purity.
	 *
	 * @param value
	 *            allowed object is {@link double }
	 *
	 */
	void setPurity(double value);

}
