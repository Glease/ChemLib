package net.glease.chem.simple.scoping;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Modified version of {@link WeakHashMap} from OpenJDK 1.8.0_77.
 * 
 * @author glease
 *
 */
class WeakMapping<K, V> {

	/**
	 * The entries in this hash table extend WeakReference, using its main ref
	 * field as the key.
	 */
	private static class Entry<K, V> extends WeakReference<Object> implements Map.Entry<K, V> {
		V value;
		final int hash;
		Entry<K, V> next;

		/**
		 * Creates new entry.
		 */
		Entry(Object key, V value, ReferenceQueue<Object> queue, int hash, Entry<K, V> next) {
			super(key, queue);
			this.value = value;
			this.hash = hash;
			this.next = next;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
			K k1 = getKey();
			Object k2 = e.getKey();
			if (k1 == k2 || (k1 != null && k1.equals(k2))) {
				V v1 = getValue();
				Object v2 = e.getValue();
				if (v1 == v2 || (v1 != null && v1.equals(v2)))
					return true;
			}
			return false;
		}

		@Override
		@SuppressWarnings("unchecked")
		public K getKey() {
			return (K) get();
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public int hashCode() {
			K k = getKey();
			V v = getValue();
			return System.identityHashCode(k) ^ System.identityHashCode(v);
		}

		@Override
		public V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}

		@Override
		public String toString() {
			return getKey() + "=" + getValue();
		}
	}

	/**
	 * The default initial capacity -- MUST be a power of two.
	 */
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	/**
	 * The maximum capacity, used if a higher value is implicitly specified by
	 * either of the constructors with arguments. MUST be a power of two <=
	 * 1<<30.
	 */
	private static final int MAXIMUM_CAPACITY = 1 << 30;

	/**
	 * The load factor used when none specified in constructor.
	 */
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	/**
	 * Checks for reference-equality of x and y.
	 */
	private static boolean eq(Object x, Object y) {
		return x == y;
	}

	/**
	 * Returns index for hash code h.
	 */
	private static int indexFor(int h, int length) {
		return h & (length - 1);
	}

	/**
	 * The table, resized as necessary. Length MUST Always be a power of two.
	 */
	Entry<K, V>[] table;

	/**
	 * The number of key-value mappings contained in this weak hash map.
	 */
	private int size;

	/**
	 * The next size value at which to resize (capacity * load factor).
	 */
	private int threshold;

	/**
	 * The load factor for the hash table.
	 */
	private final float loadFactor;

	/**
	 * Reference queue for cleared WeakEntries. Exposed to package.
	 */
	private final ReferenceQueue<Object> queue = new ReferenceQueue<>();

	private final Consumer<? super V> removalListener;

	/**
	 * Constructs a new, empty <tt>WeakMapping</tt> with the default initial
	 * capacity (16) and load factor (0.75).
	 */
	public WeakMapping() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, null);
	}

	public WeakMapping(Consumer<? super V> removalListener) {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, removalListener);
	}

	/**
	 * Constructs a new, empty <tt>WeakMapping</tt> with the given initial
	 * capacity and the default load factor (0.75).
	 *
	 * @param initialCapacity
	 *            The initial capacity of the <tt>WeakMapping</tt>
	 * @throws IllegalArgumentException
	 *             if the initial capacity is negative
	 */
	public WeakMapping(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR, null);
	}

	// internal utilities

	/**
	 * Constructs a new, empty <tt>WeakMapping</tt> with the given initial
	 * capacity and the given load factor.
	 *
	 * @param initialCapacity
	 *            The initial capacity of the <tt>WeakMapping</tt>
	 * @param loadFactor
	 *            The load factor of the <tt>WeakMapping</tt>
	 * @throws IllegalArgumentException
	 *             if the initial capacity is negative, or if the load factor is
	 *             nonpositive.
	 */
	public WeakMapping(int initialCapacity, float loadFactor) {
		this(initialCapacity, loadFactor, null);
	}

	public WeakMapping(int initialCapacity, float loadFactor, Consumer<? super V> removalListener) {
		this.removalListener = removalListener;
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Initial Capacity: " + initialCapacity);
		if (initialCapacity > MAXIMUM_CAPACITY)
			initialCapacity = MAXIMUM_CAPACITY;

		if (loadFactor <= 0 || Float.isNaN(loadFactor))
			throw new IllegalArgumentException("Illegal Load factor: " + loadFactor);
		int capacity = 1;
		while (capacity < initialCapacity)
			capacity <<= 1;
		table = newTable(capacity);
		this.loadFactor = loadFactor;
		threshold = (int) (capacity * loadFactor);
	}

	/**
	 * Removes all of the mappings from this map. The map will be empty after
	 * this call returns.
	 */
	public void clear() {
		Object x;
		while ((x = queue.poll()) != null) {
			@SuppressWarnings("unchecked")
			Entry<K, V> e = (Entry<K, V>) x;
			removalListener.accept(e.value);
		}

		for (int i = 0; i < table.length; i++) {
			removalListener.accept(table[i].value);
			table[i] = null;
		}

		size = 0;

		while ((x = queue.poll()) != null) {
			@SuppressWarnings("unchecked")
			Entry<K, V> e = (Entry<K, V>) x;
			removalListener.accept(e.value);
		}
	}

	/**
	 * Returns <tt>true</tt> if this map contains a mapping for the specified
	 * key.
	 *
	 * @param key
	 *            The key whose presence in this map is to be tested
	 * @return <tt>true</tt> if there is a mapping for <tt>key</tt>;
	 *         <tt>false</tt> otherwise
	 */
	public boolean containsKey(Object key) {
		return getEntry(key) != null;
	}

	/**
	 * Expunges stale entries from the table.
	 */
	private void expungeStaleEntries() {
		for (Object x; (x = queue.poll()) != null;) {
			synchronized (queue) {
				@SuppressWarnings("unchecked")
				Entry<K, V> polled = (Entry<K, V>) x;
				int i = indexFor(polled.hash, table.length);

				Entry<K, V> prev = table[i];
				Entry<K, V> working = prev;
				while (working != null) {
					Entry<K, V> next = working.next;
					if (working == polled) {
						if (prev == polled)
							table[i] = next;
						else
							prev.next = next;
						removalListener.accept(polled.value);
						polled.next = null;
						polled.value = null; // Help GC
						size--;
						break;
					}
					prev = working;
					working = next;
				}
			}
		}
	}

	/**
	 * Returns the value to which the specified key is mapped, or {@code null}
	 * if this map contains no mapping for the key.
	 *
	 * <p>
	 * More formally, if this map contains a mapping from a key {@code k} to a
	 * value {@code v} such that {@code (key==null ? k==null :
	 * key.equals(k))}, then this method returns {@code v}; otherwise it returns
	 * {@code null}. (There can be at most one such mapping.)
	 *
	 * <p>
	 * A return value of {@code null} does not <i>necessarily</i> indicate that
	 * the map contains no mapping for the key; it's also possible that the map
	 * explicitly maps the key to {@code null}. The {@link #containsKey
	 * containsKey} operation may be used to distinguish these two cases.
	 *
	 * @see #put(Object, Object)
	 */
	public synchronized V get(K key, Function<? super K, ? extends V> mapper) {
		int h = hash(key);
		Entry<K, V>[] tab = getTable();
		int index = indexFor(h, tab.length);
		Entry<K, V> e = tab[index];
		while (e != null) {
			if (e.hash == h && eq(key, e.get()))
				return e.value;
			e = e.next;
		}
		V v = mapper.apply(key);
		put(key, v);
		return v;
	}

	/**
	 * Returns the entry associated with the specified key in this map. Returns
	 * null if the map contains no mapping for this key.
	 */
	private Entry<K, V> getEntry(Object key) {
		Object k = key;
		int h = hash(k);
		Entry<K, V>[] tab = getTable();
		int index = indexFor(h, tab.length);
		Entry<K, V> e = tab[index];
		while (e != null && !(e.hash == h && eq(k, e.get())))
			e = e.next;
		return e;
	}

	/**
	 * Returns the table after first expunging stale entries.
	 */
	private Entry<K, V>[] getTable() {
		expungeStaleEntries();
		return table;
	}

	/**
	 * Retrieve object hash code and applies a supplemental hash function to the
	 * result hash, which defends against poor quality hash functions. This is
	 * critical because HashMap uses power-of-two length hash tables, that
	 * otherwise encounter collisions for hashCodes that do not differ in lower
	 * bits.
	 */
	private final int hash(Object k) {
		int h = System.identityHashCode(k);

		// This function ensures that hashCodes that differ only by
		// constant multiples at each bit position have a bounded
		// number of collisions (approximately 8 at default load factor).
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	@SuppressWarnings("unchecked")
	private Entry<K, V>[] newTable(int n) {
		return (Entry<K, V>[]) new Entry<?, ?>[n];
	}

	/**
	 * Associates the specified value with the specified key in this map. If the
	 * map previously contained a mapping for this key, the old value is
	 * replaced.
	 *
	 * @param key
	 *            key with which the specified value is to be associated.
	 * @param value
	 *            value to be associated with the specified key.
	 * @return the previous value associated with <tt>key</tt>, or <tt>null</tt>
	 *         if there was no mapping for <tt>key</tt>. (A <tt>null</tt> return
	 *         can also indicate that the map previously associated
	 *         <tt>null</tt> with <tt>key</tt>.)
	 */
	private V put(K key, V value) {
		Object k = key;
		int h = hash(k);
		Entry<K, V>[] tab = getTable();
		int i = indexFor(h, tab.length);

		for (Entry<K, V> e = tab[i]; e != null; e = e.next) {
			if (h == e.hash && eq(k, e.get())) {
				V oldValue = e.value;
				if (value != oldValue)
					e.value = value;
				return oldValue;
			}
		}

		Entry<K, V> e = tab[i];
		tab[i] = new Entry<>(k, value, queue, h, e);
		if (++size >= threshold)
			resize(tab.length << 1);
		return null;
	}

	/**
	 * Rehashes the contents of this map into a new array with a larger
	 * capacity. This method is called automatically when the number of keys in
	 * this map reaches its threshold.
	 *
	 * If current capacity is MAXIMUM_CAPACITY, this method does not resize the
	 * map, but sets threshold to Integer.MAX_VALUE. This has the effect of
	 * preventing future calls.
	 *
	 * @param newCapacity
	 *            the new capacity, MUST be a power of two; must be greater than
	 *            current capacity unless current capacity is MAXIMUM_CAPACITY
	 *            (in which case value is irrelevant).
	 */
	private void resize(int newCapacity) {
		Entry<K, V>[] oldTable = getTable();
		int oldCapacity = oldTable.length;
		if (oldCapacity == MAXIMUM_CAPACITY) {
			threshold = Integer.MAX_VALUE;
			return;
		}

		Entry<K, V>[] newTable = newTable(newCapacity);
		transfer(oldTable, newTable);
		table = newTable;

		/*
		 * If ignoring null elements and processing ref queue caused massive
		 * shrinkage, then restore old table. This should be rare, but avoids
		 * unbounded expansion of garbage-filled tables.
		 */
		if (size >= threshold / 2) {
			threshold = (int) (newCapacity * loadFactor);
		} else {
			expungeStaleEntries();
			transfer(newTable, oldTable);
			table = oldTable;
		}
	}

	/** Transfers all entries from src to dest tables */
	private void transfer(Entry<K, V>[] src, Entry<K, V>[] dest) {
		for (int j = 0; j < src.length; ++j) {
			Entry<K, V> e = src[j];
			src[j] = null;
			while (e != null) {
				Entry<K, V> next = e.next;
				Object key = e.get();
				if (key == null) {
					e.next = null; // Help GC
					e.value = null; // " "
					size--;
				} else {
					int i = indexFor(e.hash, dest.length);
					e.next = dest[i];
					dest[i] = e;
				}
				e = next;
			}
		}
	}

}
