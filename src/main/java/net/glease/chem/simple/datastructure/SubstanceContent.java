
package net.glease.chem.simple.datastructure;

/**
 * <p>
 * The Java class of SubstanceContent.
 * @author glease
 * @since 0.1
 *
 *
 */
public interface SubstanceContent extends Element<Substance, SubstanceContent> {

	/**
	 * Get the value of atom.
	 *
	 * @return possible object is {@link Atom }
	 *
	 */
	Atom getAtom();

	/**
	 * Should be always effectively the same as {@code getAtom().getId()}.
	 */
	@Override
	default String getId() {
		return getAtom().getId();
	}

	/**
	 * Get the value of mol.
	 *
	 * @return possible object is {@link int }
	 *
	 */
	int getMol();

	@Override
	default boolean isBroken() {
		return Element.super.isBroken() || Utils.isBroken(this, getAtom()) || getMol() < 0;
	}

	@Override
	default ChemDatabase rootScope() {
		Substance s = scope();
		return s == null ? null : s.scope();
	}

	/**
	 * Set the value of atom.
	 *
	 * @param value
	 *            allowed object is {@link Atom }
	 *
	 */
	void setAtom(Atom value);

	/**
	 * Set the value of mol.
	 *
	 * @param value
	 *            allowed type is {@code int }
	 *
	 */
	void setMol(int value);

}
