package net.glease.chem.simple.util;

public class NonNullImmutablePair<L, R> implements Pair<L, R> {
	private final L left;
	private final R right;
	
	public NonNullImmutablePair(L left, R right) {
		super();
		this.left = left;
		this.right = right;
	}

	/* (non-Javadoc)
	 * @see net.glease.chem.simple.datastructure.Pair#getLeft()
	 */
	@Override
	public L getLeft() {
		return left;
	}

	/* (non-Javadoc)
	 * @see net.glease.chem.simple.datastructure.Pair#getRight()
	 */
	@Override
	public R getRight() {
		return right;
	}

	/* (non-Javadoc)
	 * @see net.glease.chem.simple.datastructure.Pair#characteristics()
	 */
	@Override
	public int characteristics() {
		return NON_NULL | IMMUTABLE;
	}

	/* (non-Javadoc)
	 * @see net.glease.chem.simple.datastructure.Pair#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see net.glease.chem.simple.datastructure.Pair#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		NonNullImmutablePair<?,?> other = (NonNullImmutablePair<?,?>) obj;
		if (left == null) {
			if (other.left != null) {
				return false;
			}
		} else if (!left.equals(other.left)) {
			return false;
		}
		if (right == null) {
			if (other.right != null) {
				return false;
			}
		} else if (!right.equals(other.right)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NonNullImmutablePair [left=");
		builder.append(left);
		builder.append(", right=");
		builder.append(right);
		builder.append("]");
		return builder.toString();
	}
}