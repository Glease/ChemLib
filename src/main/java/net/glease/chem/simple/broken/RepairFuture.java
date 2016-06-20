package net.glease.chem.simple.broken;

import net.glease.chem.simple.datastructure.ChemDatabase;

public interface RepairFuture {
	/**
	 * Commit the repair.
	 * @param hasMoreRepairmen
	 */
	void commit(boolean hasMoreRepairmen);
	/**
	 * This repair is abandoned by the plugin manager, give up any pre-computed
	 * results.
	 * @param hasOtherRepairmen
	 */
	void abandon(boolean hasOtherRepairmen);
	/**
	 * Get the repair method this {@link RepairFuture} will use.
	 * @return
	 */
	RepairMethod getRepairMethod();
	/**
	 * If this {@link RepairFuture} will make the {@link ChemDatabase} good.
	 * @return
	 */
	default boolean isAllRepaired() {
		float f = getRepairMethod().getDoneProbablility();
		if(f>=100.0f)
			return true;
		else if(f<=0.0f)
			return false;
		throw new Error("default implementation can't infer allRepaired!");
	}
}
