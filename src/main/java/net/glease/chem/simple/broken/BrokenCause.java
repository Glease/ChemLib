package net.glease.chem.simple.broken;

public enum BrokenCause {
	/**
	 * Break down source's scope changed to <code>null</code>.
	 */
	REMOVED,
	/**
	 * Break down source's scope changed, but not to <code>null</code>.
	 */
	MOVED,
	/**
	 * Break down source's state is wrong, e.g. a -1K temperature is set
	 */
	WRONG,

	/**
	 * Break down source is missing some critical state.
	 * <p>
	 * Note: newly created instance won't complain about this event, but
	 * they are still considered as broken.
	 */
	MISSING,
	/**
	 * RESERVED
	 */
	ERROR;
}