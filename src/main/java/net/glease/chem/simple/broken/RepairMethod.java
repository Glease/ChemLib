package net.glease.chem.simple.broken;

import java.io.Serializable;

/**
 * RepairMethods are required to be singleton, but lazy initialization isn't.
 * @author glease
 *
 */
public interface RepairMethod extends Serializable {
	/**
	 *
	 * @return how this {@link BrokenEventHandler} repairs the broken elements
	 */
	String getDescription();
	/**
	 *
	 * @return the probability of fixing the broken state. 100.0f or higher means definitely done.
	 * 0.0f or lower means definitely not.
	 */
	float getDoneProbablility();
}
