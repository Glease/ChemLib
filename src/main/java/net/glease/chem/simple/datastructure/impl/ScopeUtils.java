package net.glease.chem.simple.datastructure.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import net.glease.chem.simple.datastructure.ChemDatabaseComponent.ComponentType;
import net.glease.chem.simple.datastructure.IElement;
import net.glease.chem.simple.scoping.IScope;
import net.glease.chem.simple.scoping.IScoped;
import net.glease.chem.simple.scoping.ScopeException;

class ScopeUtils {
	/**
	 *
	 * @param s tolerate <code>null</code> values in the stream
	 */
	public static <T extends IElement<T_SCOPE, T>, T_SCOPE extends IScope<?, T_SCOPE>> Set<T> bindSub(final Stream<T> s,
			final IElement<T_SCOPE, ?> master, final T_SCOPE newScope) {
		Iterator<T> iter = s.iterator();
		Set<T> added = new HashSet<>();
		while (iter.hasNext()) {
			T t = iter.next();
			try {
				// if already an element, don't add it to the added set
				if (t.bind(newScope))
					added.add(t);
			} catch (ScopeException e) {
				// revert
				added.stream().forEach(at -> at.bind(null));
				// and complain
				throw ScopeUtils.bindSubFailed(t, newScope, master, e);
			}
		}
		return added;
	}

	public static <T extends IElement<T_SCOPE, T>, T_SCOPE extends IScope<?, T_SCOPE>> Set<T> bindSub(final Stream<T> s,
			final IElement<T_SCOPE, ?> master, final T_SCOPE newScope, final Set<? extends IScoped<T_SCOPE>> addedOld) {
		Iterator<T> iter = s.iterator();
		Set<T> added = new HashSet<>();
		while (iter.hasNext()) {
			T t = iter.next();
			if(t == null)
				continue;
			try {
				// if already an element, don't add it to the added set
				if (t.bind(newScope))
					added.add(t);
			} catch (ScopeException e) {
				// revert
				added.stream().forEach(at -> at.bind(null));
				if (addedOld != null)
					addedOld.stream().forEach(at -> at.bind(null));
				// and complain
				throw ScopeUtils.bindSubFailed(t, newScope, master, e);
			}
		}
		return added;
	}

	public static <T extends IScope<?, T>> ScopeException bindSubFailed(final IElement<?, ?> sub, final T scope, final IElement<T, ?> issuer,
			final Throwable cause) {
		return new ScopeException(String.format("Can't bind %s because a referenced %s refused to be bind to given %s",
				ComponentType.get(issuer).getName(), ComponentType.get(sub).getName(),
				ComponentType.get(scope.getClass()).getName()), cause, scope, issuer);
	}

	public static void orphan(final Collection<? extends IScoped<?>> c) {
		c.stream().forEach(i->i.bind(null));
	}

	@SafeVarargs
	public static void orphan(final Collection<? extends IScoped<?>>... cs) {
		for (Collection<? extends IScoped<?>> c : cs)
			c.stream().forEach(i->i.bind(null));
	}

	private ScopeUtils() {

	}
}
