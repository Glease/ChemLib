
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * The Java class of Reactant.
 * @author glease
 * @since 0.1
 *
 */
public interface Reactant extends ReactionComponent<Reactant> {

	/**
	 * Get the value of purity.
	 * 
	 * @return possible object is {@link double }
	 * 
	 */
	double getPurity();

	/**
	 * Set the value of purity.
	 * 
	 * @param value
	 *            allowed object is {@link double }
	 * 
	 */
	void setPurity(double value);

}
