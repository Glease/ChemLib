package net.glease.chem.simple.parsers;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Need refactor. TODO
 *
 * @param <S>
 */
final class ServiceFinder<S> implements Iterable<S> {
	private final String systemPropertyName;
	private final String configName;
	private final String configPropertyName;
	private final ClassLoader cl;
	private final Class<S> clazz;
	private final Supplier<S> defaultService;
	private final boolean endlessDefault;

	public ServiceFinder(String systemPropertyName, String configName, String configPropertyName, ClassLoader cl,
			Class<S> clazz, Supplier<S> defaultService, boolean endlessDefault) {
		super();
		this.systemPropertyName = systemPropertyName;
		this.configName = configName;
		this.configPropertyName = configPropertyName;
		this.cl = cl;
		this.clazz = clazz;
		sl = ServiceLoader.load(this.clazz, this.cl);
		this.defaultService = defaultService;
		this.endlessDefault = endlessDefault;
		reload();
	}

	private S system;
	private S config;
	private ServiceLoader<S> sl;
	private boolean[] valid = new boolean[2];

	private final class LazyIter implements Iterator<S> {
		private Iterator<S> backing;
		private Finding state;
		private Supplier<S> defaultService = ServiceFinder.this.defaultService;

		public LazyIter() {
			if (valid[0])
				state = Finding.SYSTEM_PROPERTY;
			else if (valid[1])
				state = Finding.CONFIG_PROPERTY;
			else {
				state = Finding.SERVICE;
				backing = sl.iterator();
			}
		}

		@Override
		public boolean hasNext() {
			return state != Finding.SERVICE 
					|| backing.hasNext() 
					|| defaultService != null;
		}

		@Override
		public S next() {
			switch (state) {
			case SYSTEM_PROPERTY:
				if (valid[1])
					state = Finding.CONFIG_PROPERTY;
				else {
					state = Finding.SERVICE;
					backing = sl.iterator();
				}
				return system;
			case CONFIG_PROPERTY:
				state = Finding.SERVICE;
				backing = sl.iterator();
				return config;
			case SERVICE:
				if (!backing.hasNext()) {
					state = Finding.DEFAULT;
					return next();
				}
				return backing.next();
			case DEFAULT:
				Supplier<S> dss = defaultService;
				if (!endlessDefault)
					defaultService = null;
				return dss.get();
			default:
				throw new Error();
			}
		}
	}

	private enum Finding {
		SYSTEM_PROPERTY, CONFIG_PROPERTY, SERVICE, DEFAULT;
	}

	void reload() {
		valid[0] = tryLoadSystem();
		valid[1] = tryLoadConfig();
		sl.reload();
	}

	private boolean tryLoadConfig() {
		if (configName == null)
			return false;
		config = load(cl.getResourceAsStream(configName), cl);
		return config != null;
	}

	private boolean tryLoadSystem() {
		if (systemPropertyName == null)
			return false;
		String s = System.getProperty(systemPropertyName);
		system = s == null ? null : load(s, cl);
		return system != null;
	}

	private S load(InputStream url, ClassLoader cl) {
		Properties p = new Properties();
		try {
			p.load(url);
		} catch (Exception e) {
			return null;
		}
		String e = p.getProperty(configPropertyName);
		if (e == null || e.isEmpty())
			return null;
		return load(e, cl);
	}

	private S load(String s, ClassLoader cl) {
		try {
			Class<?> c = Class.forName(s, false, cl);
			return clazz.cast(c.newInstance());
		} catch (Exception e) {
			return null;
		}
	}

	ClassLoader getClassLoader() {
		ClassLoader ccl = Thread.currentThread().getContextClassLoader();
		return ccl == null ? ClassLoader.getSystemClassLoader() : ccl;
	}

	S find(Predicate<S> filter) {
		LazyIter iter = iterator();
		while (iter.hasNext()) {
			S s = iter.next();
			if (filter.test(s))
				return s;
		}
		return null;
	}

	@Override
	public LazyIter iterator() {
		return new LazyIter();
	}
}