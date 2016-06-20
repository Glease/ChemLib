package net.glease.chem.simple.broken;

public interface FixingRepairMethod extends RepairMethod {
	@Override
	default String getDescription() {
		return "Fixed by " + getMethod();
	}
	String getMethod();
}