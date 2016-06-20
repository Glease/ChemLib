package net.glease.chem.simple.broken;

import static net.glease.chem.simple.util.SafeFunction.safe;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.util.FunctionUtils;

public class RemovingRepairMethod implements RepairMethod {
	private static final long serialVersionUID = 1L;
	/**
	 *
	 * All affected elements passed in are removed, except {@link ChemDatabase}.
	 */
	public static final RemovingRepairMethod REMOVED_ALL = new RemovingRepairMethod(2000, "All", 100.0f);
	/**
	 * Form a tree of affected elements with the source as its root, and
	 * remove elements from root to the leaves until all remaining elements
	 * report them to be good. The exact number removed elements are
	 */
	public static final RemovingRepairMethod REMOVE_NECESSARY = new RemovingRepairMethod(1400, "Necessary", 100.0f);
	/**
	 * The broken element, and the elements that directly referenced it,
	 * will be removed. Affected elements that only reference the broken
	 * element indirectly, will be retained, even if this in turn leaves
	 * some of them broken.
	 */
	public static final RemovingRepairMethod REMOVED_DIRECT = new RemovingRepairMethod(800, "Direct", 80.0f);
	/**
	 * Only the broken element are removed, other elements are ignored, even
	 * if this in turn leaves some of them broken.
	 */
	public static final RemovingRepairMethod REMOVED_MINIMUM = new RemovingRepairMethod(400, "Minimum", 20.0f);

	static {
		putPredefinedIntoPool();
	}

	public static final Map<RemovingRepairMethod, RemovingRepairMethod> POOL = new ConcurrentHashMap<>();
	public static RemovingRepairMethod get(final int level, final String name, final float fixedAll) {
		return get(new RemovingRepairMethod(level, name, fixedAll));
	}

	private static RemovingRepairMethod get(final RemovingRepairMethod key) {
		return RemovingRepairMethod.POOL.computeIfAbsent(key, Function.identity());
	}

	private static void putPredefinedIntoPool() {
		Class<RemovingRepairMethod> c = RemovingRepairMethod.class;
		Arrays.stream(c.getFields())
				.filter(FunctionUtils.and(Field::getModifiers, Modifier::isStatic, Modifier::isPublic))
				.filter(FunctionUtils.and(Field::getType, Predicate.isEqual(c)))
				.map(safe(f -> (RemovingRepairMethod) f.get(null))).forEach(m -> POOL.put(m, m));
	}

	private final int level;
	private final String name;
	private final float fixedAll;

	private RemovingRepairMethod(final int level, final String name, final float fixedAll) {
		this.level = level;
		this.name = name;
		this.fixedAll = fixedAll;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RemovingRepairMethod))
			return false;
		RemovingRepairMethod other = (RemovingRepairMethod) obj;
		if (level != other.level)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public final String getDescription() {
		return "Removed " + name;
	}

	@Override
	public final float getDoneProbablility() {
		return fixedAll;
	}

	public int getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + level;
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	private Object readResolve() {
		return get(this);
	}

}