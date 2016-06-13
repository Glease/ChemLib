
package net.glease.chem.simple.datastructure.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import net.glease.chem.simple.datastructure.Atom;
import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.ChemDatabaseComponent;
import net.glease.chem.simple.datastructure.ChemDatabaseFinder;
import net.glease.chem.simple.datastructure.Element;
import net.glease.chem.simple.datastructure.NormalizationException;
import net.glease.chem.simple.datastructure.Reaction;
import net.glease.chem.simple.datastructure.Reagent;
import net.glease.chem.simple.datastructure.Substance;
import net.glease.chem.simple.normalizers.NormalizationPlugin;

public class ChemDatabaseImpl implements Serializable, ChemDatabase {

	private final static long serialVersionUID = 1L;

	@SafeVarargs
	private static void bindCopyTo(ChemDatabase scope, Collection<? extends Element<ChemDatabase, ?>>... cs) {
		for (Collection<? extends Element<ChemDatabase, ?>> c : cs) {
			c.stream().map(ChemDatabaseComponent::copy).forEach(a->a.bind(scope));
		}
	}
	public static ChemDatabase copyOf(ChemDatabase db) {
		ChemDatabaseImpl c = new ChemDatabaseImpl();
		bindCopyTo(c, db.getAtoms().values(), db.getSubstances().values(), db.getReagents().values(), db.getReactions());
		c.info = db.getInfo();
		c.locale = db.getLocale();
		c.uuid = db.getUUID();
		c.version = db.getVersion();
		return c;
	}
	protected Map<String, Substance> substances = new HashMap<>();
	protected Set<Reaction> reactions = new HashSet<>();

	protected Map<String, Atom> atoms = new HashMap<>();

	protected Map<String, Reagent> reagents = new HashMap<>();
	protected transient Set<NormalizationPlugin> plugins;
	protected UUID uuid;
	protected String version;

	protected String info;
	protected Locale locale;
	protected transient Map<String, Atom> unmodifiableAtoms = Collections.unmodifiableMap(atoms);
	protected transient Set<Reaction> unmodifiableReactions = Collections.unmodifiableSet(reactions);

	protected transient Map<String, Reagent> unmodifiableReagents = Collections.unmodifiableMap(reagents);

	protected transient Map<String, Substance> unmodifiableSubstances = Collections.unmodifiableMap(substances);

	public ChemDatabaseImpl() {
		plugins = Collections.newSetFromMap(new IdentityHashMap<>());
	}

	@Override
	public ChemDatabase copy() {
		return copyOf(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ChemDatabase)) {
			return false;
		}
		ChemDatabase other = (ChemDatabase) obj;
		if (!uuid.equals(other.getUUID())) {
			return false;
		}
		return version.equals(other.getVersion());
	}

	@Override
	public ChemDatabaseFinder find() {
		return new ChemDatabaseFinderImpl(this);
	}

	@Override
	public Map<String, Atom> getAtoms() {
		return unmodifiableAtoms;
	}

	@Override
	public String getInfo() {
		return info;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public Set<Reaction> getReactions() {
		return unmodifiableReactions;
	}

	@Override
	public Map<String, Reagent> getReagents() {
		return unmodifiableReagents;
	}

	@Override
	public Map<String, Substance> getSubstances() {
		return unmodifiableSubstances;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public void install(NormalizationPlugin plugin) {
		if (!plugins.add(plugin))
			throw new IllegalArgumentException("duplicate plugin: " + plugin);
	}

	@Override
	public final void normalize() throws NormalizationException {
		for (NormalizationPlugin plugin : plugins) {
			plugin.normalize(this);
		}
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		unmodifiableAtoms = Collections.unmodifiableMap(atoms);
		unmodifiableReactions = Collections.unmodifiableSet(reactions);
		unmodifiableReagents = Collections.unmodifiableMap(reagents);
		unmodifiableSubstances = Collections.unmodifiableMap(substances);
	}

	@Override
	public void setInfo(String value) {
		this.info = value;
	}

	@Override
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public void setUUID(UUID value) {
		this.uuid = Objects.requireNonNull(value);
	}

	@Override
	public void setVersion(String value) {
		if (Objects.requireNonNull(value).isEmpty())
			throw new IllegalArgumentException("empty version");
		this.version = value;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChemDatabaseImpl [");
		if (substances != null) {
			builder.append("substances=");
			builder.append(substances);
			builder.append(", ");
		}
		if (reactions != null) {
			builder.append("reactions=");
			builder.append(reactions);
			builder.append(", ");
		}
		if (atoms != null) {
			builder.append("atoms=");
			builder.append(atoms);
			builder.append(", ");
		}
		if (reagents != null) {
			builder.append("reagents=");
			builder.append(reagents);
			builder.append(", ");
		}
		builder.append("uuid=");
		builder.append(uuid);
		builder.append(", ");
		builder.append("version=");
		builder.append(version);
		builder.append(", ");
		if (info != null) {
			builder.append("info=");
			builder.append(info);
		}
		builder.append("]");
		return builder.toString();
	}

}
