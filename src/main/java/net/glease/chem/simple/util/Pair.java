package net.glease.chem.simple.util;

public interface Pair<L, R> {

	int NON_NULL = 1;
	int IMMUTABLE = 2;

	/**
	 * 
	 * @return get the left value of this pair.
	 */
	L getLeft();

	/**
	 * 
	 * @return get the right value of this pair.
	 */
	R getRight();

	/**
	 * 
	 * @return get the bit or result of all available characteristics that this pair has.
	 */
	int characteristics();

	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);

}