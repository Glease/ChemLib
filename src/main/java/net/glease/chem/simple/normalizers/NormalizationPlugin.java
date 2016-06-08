package net.glease.chem.simple.normalizers;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.NormalizationException;

/**
 * 
 * @author glease
 *
 */
public interface NormalizationPlugin {
	/**
	 * Get the version info of this {@link NormalizationPlugin}
	 * 
	 * @return the version info
	 */
	String info();

	/**
	 * Get the name of this {@link NormalizationPlugin}
	 * 
	 * @return the name
	 */
	String name();

	/**
	 * Normalize a {@link ChemDatabase}. Invoked after all built-in
	 * normalization is done. If there are multiple {@link NormalizationPlugin}
	 * installed on a single {@link ChemDatabase} instance, the invocation order
	 * of these plugins are undefined. Clients should synchronize externally if
	 * a instance of {@link NormalizationPlugin} has to be shared among multiple
	 * threads.
	 * {@link #normalize(ChemDatabase)} method.
	 * 
	 * 
	 * @param cdb
	 */
	void normalize(ChemDatabase cdb) throws NormalizationException;

	/**
	 * Get the vendor info of this {@link NormalizationPlugin}
	 * 
	 * @return the vendor info
	 */
	String vendor();

	/**
	 * Get the version info of this {@link NormalizationPlugin}
	 * 
	 * @return the version info
	 */
	String version();
}
