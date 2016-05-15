package net.glease.chem.simple.datastructure;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.glease.chem.simple.scoping.IScope;
import net.glease.chem.simple.scoping.IScoped;
import net.glease.chem.simple.scoping.ScopeException;
import net.glease.chem.simple.util.NormalizationPlugin;

public interface ChemDatabase extends IScope<IScope.ROOT, ChemDatabase>, ChemDatabaseComponent {

	/**
	 * @return <code>null</code> only
	 */
	@Override
	default IScope.ROOT scope() {
		return null;
	}

	/**
	 * Do nothing.
	 */
	@Override
	default void bind(net.glease.chem.simple.scoping.IScope.ROOT cdb) {
	}

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
	default void onBind(IScoped<ChemDatabase> o) {
		IScope.super.onBind(o);
		if (o instanceof Substance) {
			Substance s = (Substance) o;
			getSubstances().put(s.getId(), s);
		} else if (o instanceof Reaction) {
			getReactions().add((Reaction) o);
		} else if (o instanceof Atom) {
			Atom s = (Atom) o;
			getAtoms().put(s.getId(), s);
		} else if (o instanceof Reagent) {
			Reagent s = (Reagent) o;
			getReagents().put(s.getId(), s);
		} else {
			throw new ScopeException("Element not identified.", this, o);
		}
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
	default void onUnbind(IScoped<ChemDatabase> o) {
		if (o instanceof Substance) {
			if (getSubstances().remove(o.getId()) == null)
				throw new ScopeException("Not binded to this scope", this, o);
		} else if (o instanceof Reaction) {
			if (!getReactions().remove(o))
				throw new ScopeException("Not binded to this scope", this, o);
		} else if (o instanceof Atom) {
			if (getAtoms().remove(o.getId()) == null)
				throw new ScopeException("Not binded to this scope", this, o);
		} else if (o instanceof Reagent) {
			if (getReagents().remove(o.getId()) == null)
				throw new ScopeException("Not binded to this scope", this, o);
		} else {
			throw new ScopeException("Element not identified.", this, o);
		}
		IScope.super.onUnbind(o);
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

	ChemDatabaseFinder find();

	/**
	 * Get a map containing all {@link Atom}s in this {@link ChemDatabase}
	 * indexed with {@link Atom#getId()}. The map is unmodifiable.
	 * 
	 * @return possible object is {@link Map<String, Reagent> }
	 */
	Map<String, Atom> getAtoms();

	/**
	 * Get a map containing all {@link Reagent}s in this {@link ChemDatabase}
	 * indexed with {@link Reagent#getId()}. The map is unmodifiable.
	 * 
	 * @return possible object is {@link Map<String, Reagent> }
	 */
	Set<Reaction> getReactions();

	/**
	 * 
	 * @return
	 * @see #setInfo(String)
	 */
	String getInfo();

	/**
	 * Get a map containing all {@link Reagent}s in this {@link ChemDatabase}
	 * indexed with {@link Reagent#getId()}. The map is unmodifiable.
	 * 
	 * @return possible object is {@link Map<String, Reagent> }
	 */
	Map<String, Reagent> getReagents();

	/**
	 * Get a map containing all {@link Substance}s in this {@link ChemDatabase}
	 * indexed with {@link Substance#getId()}. The map is unmodifiable.
	 * 
	 * @return possible object is {@link Map<String, Substance> }
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
	 * 
	 * Some info. Authors, site link, license and other metadata (if any) about
	 * this CDB goes in here, except the version.
	 * 
	 * @param value
	 */
	void setInfo(String value);

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

	/**
	 * Install a new {@link NormalizationPlugin} into this {@link ChemDatabase}.
	 * Multiple {@link NormalizationPlugin} of the same class (i.e.
	 * {@code a.getClass() == b.getClass()}) should be allowed, unless they are
	 * equal (i.e. {@code a == b}).
	 * 
	 * @param plugin
	 */
	void accept(NormalizationPlugin plugin);

}