package net.glease.chem.simple.datastructure;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.glease.chem.simple.normalizers.NormalizationPlugin;
import net.glease.chem.simple.scoping.IScope;
import net.glease.chem.simple.scoping.IScoped;
import net.glease.chem.simple.scoping.ScopeException;

/**
 * ChemDatabase represents a set of chemical data. Clients should invoke
 * {@link #normalize()} after getting a fresh {@link ChemDatabase} unmarshaled
 * and before marshaling a {@link ChemDatabase}
 *
 * @author glease
 * @since 0.1
 */
public interface ChemDatabase extends IScope<IScope.ROOT, ChemDatabase>, ChemDatabaseComponent<ChemDatabase> {

	@Override
	default boolean bind(final IScope.ROOT cdb) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Two {@link ChemDatabase}s are considered <i>value-equal</i> if all their
	 * properties are <i>value-equal</i>.
	 * <p>
	 * {@inheritDoc}
	 *
	 * @param obj
	 *            the reference object with which to compare.
	 * @return <code>true</code> if this object is the same as the obj argument;
	 *         <code>false</code> otherwise.
	 * @see #hashCode()
	 */
	@Override
	boolean equals(Object obj);

	/**
	 * Create a new {@link ChemDatabaseFinder} to find various component in this
	 * {@link ChemDatabase}. Not synchronized.
	 *
	 * @return
	 */
	ChemDatabaseFinder find();

	/**
	 * Get a map containing all {@link Atom}s in this {@link ChemDatabase}
	 * indexed with {@link Atom#getId()}. The map is unmodifiable.
	 * Addition/removal to this map should be done with
	 * {@link IScoped#bind(IScope) bind(this)} or {@link IScoped#bind(IScope)
	 * bind(null)}.
	 *
	 * @return possible object is {@link Map&lt;String, Reagent> }
	 */
	Map<String, Atom> getAtoms();

	/**
	 * All implementation should act identical to this:
	 *
	 * <pre>
	 * {@code '[' + getUUID().toString() + "]:" + getVersion()}
	 * </pre>
	 */
	@Override
	default String getId() {
		return new StringBuilder().append('[').append(getUUID()).append("]:").append(getVersion()).toString();
	}

	/**
	 *
	 * @return
	 * @see #setInfo(String)
	 */
	String getInfo();

	Locale getLocale();

	/**
	 * Get a set containing all {@link Reagent}s in this {@link ChemDatabase}.
	 * The set is unmodifiable. It is a set because the reaction id doesn't
	 * matters. Addition/removal to this set should be done with
	 * {@link IScoped#bind(IScope) bind(this)} or {@link IScoped#bind(IScope)
	 * bind(null)}.
	 *
	 * @return possible object is {@link Set&lt;Reaction> }
	 */
	Set<Reaction> getReactions();

	/**
	 * Get a map containing all {@link Reagent}s in this {@link ChemDatabase}
	 * indexed with {@link Reagent#getId()}. The map is unmodifiable.
	 * Addition/removal to this map should be done with
	 * {@link IScoped#bind(IScope) bind(this)} or {@link IScoped#bind(IScope)
	 * bind(null)}.
	 *
	 * @return possible object is {@link Map&lt;String, Reagent> }
	 */
	Map<String, Reagent> getReagents();

	/**
	 * Get a map containing all {@link Substance}s in this {@link ChemDatabase}
	 * indexed with {@link Substance#getId()}. The map is unmodifiable.
	 * Addition/removal to this map should be done with
	 * {@link IScoped#bind(IScope) bind(this)} or {@link IScoped#bind(IScope)
	 * bind(null)}.
	 *
	 * @return possible object is {@link Map&lt;String, Substance> }
	 */
	Map<String, Substance> getSubstances();

	/**
	 * Get the field UUID of type UUID.
	 *
	 * @return possible object is {@link UUID }
	 * @see
	 */
	UUID getUUID();

	/**
	 *
	 * @return
	 * @see #setVersion(String)
	 */
	String getVersion();

	/**
	 * Two {@link ChemDatabase}s are considered <i>value-equal</i> if their
	 * {@link #getUUID()} and {@link #getVersion()} both return identical value.
	 * <p>
	 * {@inheritDoc}
	 *
	 * @return a hash code value for this object.
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @see java.lang.System#identityHashCode
	 */
	@Override
	int hashCode();

	/**
	 * Install a new {@link NormalizationPlugin} into this {@link ChemDatabase}.
	 * Multiple {@link NormalizationPlugin} of the same class (i.e.
	 * {@code a.getClass() == b.getClass()}) should be allowed, unless they are
	 * equal (i.e. {@code a == b}).
	 *
	 * @param plugin
	 */
	void install(NormalizationPlugin plugin);

	/**
	 * A {@link ChemDatabase} is in broken state if any of these conditions is
	 * met:
	 * <ul>
	 * <li>{@link #getUUID()} returns <code>null</code>
	 * <li>{@link #getVersion()} returns null or an empty string.
	 * <li>Any of its component is broken.
	 * </ul>
	 * Implementation may choose to cache the broken state and let components
	 * change that cache when their states of broken are changed instead of
	 * computing it every on every invocation, but they must also consider the
	 * case that part of their components may come from another implementation
	 * and is unaware of that cache, thus not notifying the {@link ChemDatabase}.
	 * Such cache may need to be implemented along with some plugin.
	 */
	@Override
	default boolean isBroken() {
		return getUUID() == null || getVersion() == null || getVersion().isEmpty() || Utils
				.isBroken(getAtoms().values(), getSubstances().values(), getReagents().values(), getReactions());
	}

	/**
	 * Perform various tasks that normalize this database into a standard,
	 * nothing omitted one. It will return <code>false</code> immediately if an
	 * value is omitted but can't be substituted, leaving everything previously
	 * done just there, as there won't be any damage theoretically.
	 * <p>
	 * Currently, it will
	 * <ul>
	 * <li>Add all atoms omitted symbols, localizedNames.
	 * </ul>
	 *
	 * @return <code>true</code> if normalization completed normally,
	 *         <code>false</code> if normalization found some errors
	 * @throws NormalizationException
	 */
	void normalize() throws NormalizationException;

	/**
	 * Implementations should properly store the given {@code o} into
	 * {@link #getAtoms()}, {@link #getSubstances()}, {@link #getReagents()} or
	 * {@link #getReactions()}. If {@code o} is of unknown type, a
	 * properly-constructed {@link ScopeException} with message
	 * {@code "Element not identified"} should be thrown. Any other changes not
	 * observable are allowed.
	 * <p>
	 * {@inheritDoc}
	 */
	@Override
	default boolean onBind(final IScoped<ChemDatabase> o) {
		return IScope.super.onBind(o);
	}

	/**
	 * Implementations should check if {@code o} is binded to this
	 * {@link ChemDatabase} and throw a properly-constructed
	 * {@link ScopeException} with message {@code "Not binded to this scope"} on
	 * failure If {@code o} is of unknown type, a properly-constructed
	 * {@link ScopeException} with message {@code "Element not identified"}
	 * should be thrown. Any other changes not observable are allowed.
	 * <p>
	 * {@inheritDoc}
	 */
	@Override
	default void onUnbind(final IScoped<ChemDatabase> o) {
		IScope.super.onUnbind(o);
	}

	/**
	 * @return <code>null</code> only
	 */
	@Override
	default IScope.ROOT scope() {
		return null;
	}

	/**
	 *
	 * Some info. Authors, site link, license and other metadata (if any) about
	 * this CDB goes in here, except the version.
	 *
	 * @param value
	 */
	void setInfo(String value);

	void setLocale(Locale locale);

	/**
	 * The UUID this database binds to. A UUID of version 1/4 is prohibited for
	 * the sake of uniqueness.
	 *
	 * @param value
	 */
	void setUUID(UUID value);

	/**
	 *
	 * A version string. Databases with same UUID but different version should
	 * be considered not equal.
	 *
	 * @param value
	 */
	void setVersion(String value);

}