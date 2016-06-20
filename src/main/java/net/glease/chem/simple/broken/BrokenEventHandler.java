package net.glease.chem.simple.broken;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.ChemDatabaseComponent;

public interface BrokenEventHandler {
	/**
	 *
	 * @param site
	 *            where the break down took place
	 * @param source
	 * @param affected
	 * @return some repair method that this {@link BrokenEventHandler} advertises it will use,
	 * or null if it just don't want to do anything.
	 */
	RepairFuture onBroken(ChemDatabase site, ChemDatabaseComponent<?> source, ChemDatabaseComponent<?>[] affected);
}
