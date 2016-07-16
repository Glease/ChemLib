package net.glease.chem.simple.parsers;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;

public abstract class CDBParserFactory {
	static final class DefaultFactory extends CDBParserFactory {
		private static final Set<String> DEFAULT_SUPPORTED_LANGUAGE = Collections.singleton(LANGUAGE_XML);

		EntityResolver er;
		ErrorHandler eh = ReportingErrorHandler.I;

		Map<String, Boolean> fs = new HashMap<>();

		Map<String, Object> ps = new HashMap<>();

		@Override
		protected Object getProperty0(final String name) {
			switch (Objects.requireNonNull(name, "name")) {
				case XML_ENTITY_RESOLVER_NAME:
					return er;
				case XML_ERROR_HANDLER_NAME:
					return eh;
				default:
					if (name.startsWith(XML_PROPERTY_PREFIX))
						return ps.get(name);
					else if (name.startsWith(XML_FEATURE_PREFIX))
						return fs.get(name);
					else
						throw new Error();
			}
		}

		@Override
		public Set<String> getSupportedLanguage() {
			return DEFAULT_SUPPORTED_LANGUAGE;
		}

		@Override
		public boolean isPropertySupported(final String name) {
			return XML_ENTITY_RESOLVER_NAME.equals(Objects.requireNonNull(name))
					|| XML_FEATURE_PREFIX.equals(name)
					|| name.startsWith(XML_PROPERTY_PREFIX)
					|| name.startsWith(XML_FEATURE_PREFIX);
		}

		@Override
		protected Object newParser0(final String language) {
			if ("xml".equalsIgnoreCase(language))
				return new DefaultParser(this);
			throw new UnsupportedOperationException("default implementation only support xml");
		}

		@Override
		protected void setProperty0(final String name, final Object value) {
			switch (Objects.requireNonNull(name, "name")) {
				case XML_ENTITY_RESOLVER_NAME:
					er = (EntityResolver) value;
					break;
				case XML_ERROR_HANDLER_NAME:
					eh = (ErrorHandler) value;
					break;
				default:
					if (name.startsWith(XML_PROPERTY_PREFIX))
						ps.put(name, value);
					else if (name.startsWith(XML_FEATURE_PREFIX))
						fs.put(name, (Boolean) value);
					else
						throw new Error();
			}
		}
	}

	public static final String FACTORY_PROPERTY = "chemsimple.database.simple.factory";

	public static final String LANGUAGE_JSON = "json";
	public static final String LANGUAGE_XML = "xml";

	public static final String JSON_FEATURE_PREFIX = "json.feature:";
	public static final String JSON_PROPERTY_PREFIX = "json.property:";

	public static final String XML_ENTITY_RESOLVER_NAME = "xml.entityResolver";
	public static final String XML_ERROR_HANDLER_NAME = "xml.errorHandler";

	public static final String XML_FEATURE_PREFIX = "xml.feature:";
	public static final String XML_PROPERTY_PREFIX = "xml.property:";

	/**
	 * Implementations are recommended, but not required to use this as default
	 * name space prefix when unmarshaling to an XML document.
	 */
	public static final String XML_DEFAULT_NAMESPACE_PREFIX = "cdbs";
	public static final String XML_NAMESPACE = "http://glease.net/chem/simple/DataStructure/1.0";

	private static ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public static CDBParserFactory newInstance() {
		ClassLoader cl = getContextClassLoader();

		return new ServiceFinder<>(FACTORY_PROPERTY, null, null, cl, CDBParserFactory.class, DefaultFactory::new, false)
				.find(f -> true);
	}

	public static CDBParserFactory newInstance(ClassLoader cl) {
		if (cl == null)
			cl = getContextClassLoader();

		return new ServiceFinder<>(FACTORY_PROPERTY, null, null, cl, CDBParserFactory.class, DefaultFactory::new, false)
				.find(f -> true);
	}

	/**
	 * @param language
	 *            language which you want a factory supporting it
	 */
	public static CDBParserFactory newInstance(final ClassLoader cl, final String language) {
		if (language == null)
			throw new NullPointerException("language");
		String l = language.isEmpty() ? LANGUAGE_XML : language;

		return new ServiceFinder<>(FACTORY_PROPERTY, null, null, cl, CDBParserFactory.class, DefaultFactory::new, false)
				.find(f -> f.isLanguageSupported(l));
	}

	/**
	 * @param language
	 *            language which you want a factory supporting it
	 */
	public static CDBParserFactory newInstance(final String language) {
		if (language == null)
			throw new NullPointerException("language");
		String l = language.isEmpty() ? LANGUAGE_XML : language;
		ClassLoader cl = getContextClassLoader();

		return new ServiceFinder<>(FACTORY_PROPERTY, null, null, cl, CDBParserFactory.class, DefaultFactory::new, false)
				.find(f -> f.isLanguageSupported(l));
	}

	/**
	 *
	 * @param name
	 *            name of desired factory
	 */
	public static CDBParserFactory newInstance(final String name, final ClassLoader cl) {
		if (name == null || name.isEmpty())
			throw new IllegalArgumentException("name");
		Objects.requireNonNull(cl, "classloader");
		try {
			return (CDBParserFactory) Class.forName(name, false, cl).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new FactoryNotFoundException(
					String.format("Can't instantiate factory %s with classloader %s", name, cl), e);
		}
	}

	public final Object getProperty(final String name) {
		if (isPropertySupported(name))
			return getProperty0(name);
		else
			throw new IllegalArgumentException("Unsupported property: " + name);
	}

	protected abstract Object getProperty0(String name);

	/**
	 * Case insensitive. All non-null and non-empty. Value returned shouldn't be
	 * changed overtime.
	 *
	 * @return
	 */
	public abstract Set<String> getSupportedLanguage();

	protected final Set<ParserPlugin> plugins = new LinkedHashSet<>();

	/**
	 * Add a {@link ParserPlugin} to a internal set. The given plugin will be
	 * injected into the parsers created by this {@link CDBParserFactory}
	 * <i>afterwards</i>.
	 * <p>
	 * Default implementation just add given plugin to a internal
	 * {@link LinkedHashSet}.
	 *
	 * @param plugin
	 *            the plugin to be injected into this {@link CDBParserFactory}.
	 */
	public void inject(final ParserPlugin plugin) {
		plugins.add(plugin);
	}

	public final boolean isLanguageSupported(final String name) {
		return getSupportedLanguage().contains(name);
	}

	public abstract boolean isPropertySupported(String name);

	public final Object newParser(final String language) {
		if (getSupportedLanguage().contains(Objects.requireNonNull(language, "language")))
			return newParser0(language);
		throw new IllegalArgumentException("Unsupported language: " + language);
	}

	protected abstract Object newParser0(String language);

	public final XMLChemDatabaseParser newXMLParser() {
		return (XMLChemDatabaseParser) newParser(LANGUAGE_XML);
	}

	public final void setProperty(final String name, final Object value) {
		if (isPropertySupported(name))
			setProperty0(name, value);
		else
			throw new IllegalArgumentException("Unsupported property: " + name);
	}

	protected abstract void setProperty0(String name, Object value);
}
