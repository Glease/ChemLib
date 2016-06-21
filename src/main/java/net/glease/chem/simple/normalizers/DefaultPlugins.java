package net.glease.chem.simple.normalizers;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.glease.chem.simple.datastructure.Atom;
import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.IElement;
import net.glease.chem.simple.datastructure.NormalizationException;
import net.glease.chem.simple.util.LazyInitializer;

public final class DefaultPlugins {
	private DefaultPlugins() {
	}

	private static final LazyInitializer<NormalizationPlugin> atom = LazyInitializer
			.create(() -> new BuiltInNormalizationPlugin("AtomAttribute", DefaultPlugins::atomNormalize));
	private static final LazyInitializer<NormalizationPlugin> idConflict = LazyInitializer
			.create(() -> new BuiltInNormalizationPlugin("IDConflictVerifier", DefaultPlugins::idConflictVerifier));
	private static final LazyInitializer<NormalizationPlugin> idReserved = LazyInitializer
			.create(() -> new BuiltInNormalizationPlugin("IDReservedVerifier", DefaultPlugins::idConflictVerifier));

	public static NormalizationPlugin createSimple(final String info, final String name, final String vendor, final String version,
			final NormalizationFunction func) {
		return new SimpleNormalizationPlugin(info, name, vendor, version, func);
	}

	public static NormalizationPlugin getAtomMissingAttributeNormalizer() {
		return atom.get();
	}

	public static NormalizationPlugin getIDConflictVerifier() {
		return idConflict.get();
	}

	public static NormalizationPlugin getIDReservedVerifier() {
		return idReserved.get();
	}

	private static boolean hasContent(final String s) {
		return s != null && !s.isEmpty();
	}

	private static void atomNormalize(final ChemDatabase d) throws NormalizationException {

		Map<Integer, String> symbols = new HashMap<>();
		Map<Integer, String> names = new HashMap<>();
		Set<Atom> omitted = new HashSet<>();

		for (Atom atom : d.getAtoms().values()) {
			String s = atom.getSymbol(), temp;
			int index = atom.getIndex();
			if (hasContent(s)) {
				if (!s.equals(temp = symbols.put(index, s))) {
					throw new NormalizationException(
							String.format("Atom indexed %d Symbol conflict: %s, %s", index, s, temp));
				} else {
					omitted.add(atom);
				}
			}
			s = atom.getLocalizedName();
			if (hasContent(s)) {
				if (!s.equalsIgnoreCase(temp = names.put(index, s))) {
					throw new NormalizationException(
							String.format("Atom indexed %d Localized Name conflict: %s, %s", index, s, temp));
				} else {
					omitted.add(atom);
				}
			}
		}

		for (Atom atom : omitted) {
			String s;
			int index = atom.getIndex();
			if (!hasContent(atom.getSymbol())) {
				s = symbols.get(index);
				if (hasContent(s)) {
					atom.setSymbol(s);
				} else {
					throw new NormalizationException(String.format("Atom indexed %d has no declared Symbol!", index));
				}
			}
			if (!hasContent(atom.getLocalizedName())) {
				s = names.get(index);
				atom.setLocalizedName(hasContent(s) ? s : atom.getSymbol());
			}
		}
	}

	private static void idConflictVerifier(final ChemDatabase d) throws NormalizationException {
		Map<String, List<?>> conflicts = Stream
				.concat(parallelStream(d.getAtoms()),
						Stream.concat(parallelStream(d.getSubstances()),
								Stream.concat(parallelStream(d.getReagents()), d.getReactions().parallelStream())))
				.map(e -> new AbstractMap.SimpleEntry<>(e.getId(), e))
				.collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey,
						Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
				.entrySet().parallelStream().filter(e -> e.getValue().size() > 1)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		if (conflicts.isEmpty()) {
			return;
		}

		throw new IDConflictException(conflicts);
	}

	private static final LazyInitializer<Set<String>> reservedNames = LazyInitializer.create(() -> {
		Set<String> s = new HashSet<>();
		s.add("atom");
		return s;
	});

	static void reservedIdVerifier(final ChemDatabase d) throws NormalizationException {
		Map<String, Set<IElement<?, ?>>> reserved = new HashMap<>();

		for (String s : reservedNames.get()) {
			Set<IElement<?,?>> atom = reserved(d, s);
			if (!atom.isEmpty()) {
				reserved.put(s, atom);
			}
		}

		if (!reserved.isEmpty()) {
			throw new IDReservedException(reserved);
		}
	}

	private static HashSet<IElement<?,?>> reserved(final ChemDatabase d, final String n) {
		return Stream
				.concat(parallelStream(d.getSubstances()),
						Stream.concat(parallelStream(d.getReagents()), d.getReactions().parallelStream()))
				.collect(HashSet::new, (s, e) -> {
					if (e.getId().startsWith(n)) {
						s.add(e);
					}
				}, HashSet::addAll);
	}

	private static <V> Stream<V> parallelStream(final Map<?, V> m) {
		return m.values().parallelStream();
	}
}
