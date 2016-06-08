package net.glease.chem.simple.normalizers;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.NormalizationException;

@FunctionalInterface
public interface NormalizationFunction {
	void normalize(ChemDatabase cdb) throws NormalizationException;
}
