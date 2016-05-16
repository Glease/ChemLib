package net.glease.chem.simple.util;

import net.glease.chem.simple.datastructure.ChemDatabase;

/**
 * 
 * @author glease
 *
 */
public interface NormalizationPlugin {
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
	void normalize(ChemDatabase cdb);

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
}