package net.glease.chem.simple.normalizers;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.NormalizationException;

/**
 * Define a function that normalize a {@link ChemDatabase}.
 * 
 * @author glease
 * @since 0.1
 */
@FunctionalInterface
public interface NormalizationFunction {
	/**
	 * Normalize a {@link ChemDatabase}.
	 * 
	 * @param cdb
	 * @throws NormalizationException
	 *             if normalization failed, or encounters some special situation
	 *             defined somewhere.
	 */
	void normalize(ChemDatabase cdb) throws NormalizationException;
}
