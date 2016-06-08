package net.glease.chem.simple.normalizers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.glease.chem.simple.datastructure.Atom;
import net.glease.chem.simple.datastructure.NormalizationException;
import net.glease.chem.simple.util.LazyInitializer;

public final class DefaultPlugins {
	private DefaultPlugins() {
	}

	private static final LazyInitializer<NormalizationPlugin> atom = LazyInitializer
			.create(() -> new BuiltInNormalizationPlugin("AtomAttribute", d -> {

				Map<Integer, String> symbols = new HashMap<>();
				Map<Integer, String> names = new HashMap<>();
				Set<Atom> omitted = new HashSet<>();

				for (Atom atom : d.getAtoms().values()) {
					String s = atom.getSymbol(), temp;
					int index = atom.getIndex();
					if (hasContent(s))
						if (!s.equals(temp = symbols.put(index, s)))
							throw new NormalizationException(
									String.format("Atom indexed %d Symbol conflict: %s, %s", index, s, temp));
						else
							omitted.add(atom);
					s = atom.getLocalizedName();
					if (hasContent(s))
						if (!s.equalsIgnoreCase(temp = names.put(index, s)))
							throw new NormalizationException(
									String.format("Atom indexed %d Localized Name conflict: %s, %s", index, s, temp));
						else
							omitted.add(atom);
				}

				for (Atom atom : omitted) {
					String s;
					int index = atom.getIndex();
					if (!hasContent(atom.getSymbol())) {
						s = symbols.get(index);
						if (hasContent(s))
							atom.setSymbol(s);
						else
							throw new NormalizationException(
									String.format("Atom indexed %d has no declared Symbol!", index));
					}
					if (!hasContent(atom.getLocalizedName())) {
						s = names.get(index);
						atom.setLocalizedName(hasContent(s) ? s : atom.getSymbol());
					}
				}
			}));

	public static NormalizationPlugin createSimple(String info, String name, String vendor, String version,
			NormalizationFunction func) {
		return new SimpleNormalizationPlugin(info, name, vendor, version, func);
	}

	private static boolean hasContent(String s) {
		return s != null && !s.isEmpty();
	}

	public static NormalizationPlugin getAtomMissingAttributeNormalizer() {
		return atom.get();
	}
}
