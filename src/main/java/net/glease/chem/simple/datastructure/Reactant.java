
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * The Java class of Reactant.
 *
 * <p>
 * The following XML Schema snippet contains the expect content of this class.
 *
 * <pre>
 * &lt;complexType name="Reactant">
 *   &lt;complexContent>
 *     &lt;extension base="{http://glease.net/chem/simple/DataStructure}EquationComponent">
 *       &lt;attribute name="purity" type="{http://glease.net/chem/simple/DataStructure}Percentage" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
public interface Reactant extends ReactionComponent {

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
