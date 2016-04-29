package net.glease.chem.simple.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class ListComparator<T> implements Comparator<List<T>>, Serializable {
	private static final long serialVersionUID = 1L;
	private int biggerBetter;
	private int greaterBetter;
	private Comparator<T> comp;

	@SuppressWarnings("unchecked")
	public ListComparator(boolean biggerBetter, boolean greaterBetter) {
		super();
		this.biggerBetter = biggerBetter ? 1 << Integer.SIZE - 1 : 0;
		this.greaterBetter = greaterBetter ? 1 << Integer.SIZE - 1 : 0;
		comp = (Comparator<T>) Comparator.naturalOrder();
	}

	public ListComparator(int biggerBetter, int greaterBetter, Comparator<T> comp) {
		super();
		this.biggerBetter = biggerBetter;
		this.greaterBetter = greaterBetter;
		this.comp = comp;
	}

	@Override
	public int compare(List<T> o1, List<T> o2) {
		int d = o1.size() - o2.size();
		if (d != 0)
			return d ^ biggerBetter;
		Iterator<T> i1 = o1.iterator();
		Iterator<T> i2 = o2.iterator();
		while (i1.hasNext()) {
			d = comp.compare(i1.next(), i2.next());
			if (d != 0)
				return d ^ greaterBetter;
		}
		return 0;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeBoolean(biggerBetter == 1);
		out.writeBoolean(greaterBetter == 1);
		out.writeObject(comp);
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		biggerBetter = in.readBoolean() ? 1 : -1;
		greaterBetter = in.readBoolean() ? 1 : -1;
		comp = (Comparator<T>) in.readObject();
	}

}
