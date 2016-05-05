package net.glease.chem.simple.datastructure;

import java.util.List;
import java.util.UUID;

import net.glease.chem.simple.datastructure.impl.NormalizationException;

public interface ChemDatabase {
 
	@Override
	boolean equals(Object obj);

	List<Atom> getAtoms();

	List<Reaction> getReactions();

	String getInfo();

	List<Reagent> getReagents();

	List<Substance> getSubstances();

	UUID getUUID();

	String getVersion();

	@Override
	int hashCode();

	/**
	 * Perform various tasks that normalize this database into a standard,
	 * nothing omitted one. It will return <code>false</code> immediately if an
	 * value is omitted but can't be substituted, leaving everything previously
	 * done just there, as there won't be any damage theoretically.
	 * <p>
	 * Currently, it will
	 * <ul>
	 * <li>Add all atoms omitted symbols, localizedNames.
	 * </ul>
	 * 
	 * @return <code>true</code> if normalization completed normally,
	 *         <code>false</code> if normalization found some errors
	 * @throws NormalizationException 
	 */
	void normalize() throws NormalizationException;

	void setInfo(String value);

	void setUUID(UUID value);

	void setVersion(String value);

}