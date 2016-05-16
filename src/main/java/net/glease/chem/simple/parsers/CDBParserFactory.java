package net.glease.chem.simple.parsers;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;

import net.glease.chem.simple.util.ParserPlugin;

public abstract class CDBParserFactory {
	static final class DefaultFactory extends CDBParserFactory {
		private static final Set<String> DEFAULT_SUPPORTED_LANGUAGE = get();
		private static final Set<String> DEFAULT_SUPPORTED_PROPERTY = get2();

		private static Set<String> get() {
			return Collections.singleton(LANGUAGE_XML);
		}

		private static Set<String> get2() {
			Set<String> s = new HashSet<>();
			s.add(XML_ENTITY_RESOLVER_NAME);
			s.add(XML_ERROR_HANDLER_NAME);
			s.add(XML_FEATURE_PREFIX);
			s.add(XML_PROPERTY_PREFIX);
			return Collections.unmodifiableSet(s);
		}

		EntityResolver er;
		ErrorHandler eh = ReportingErrorHandler.I;

		private Map<String, Boolean> fs = new HashMap<>();

		private Map<String, Object> ps = new HashMap<>();

		@Override
		public Object getProperty0(String name) {
			switch (Objects.requireNonNull(name, "name")) {
			case XML_ENTITY_RESOLVER_NAME:
				return er;
			case XML_ERROR_HANDLER_NAME:
				return eh;
			case XML_PROPERTY_PREFIX:
				return ps.get(name.substring(XML_PROPERTY_PREFIX.length()));
			case XML_FEATURE_PREFIX:
				return fs.get(name.substring(XML_FEATURE_PREFIX.length()));
			default:
				throw new Error();
			}
		}

		@Override
		public Set<String> getSupportedLanguage() {
			return DEFAULT_SUPPORTED_LANGUAGE;
		}

		@Override
		public Set<String> getSupportedProperty() {
			return DEFAULT_SUPPORTED_PROPERTY;
		}

		@Override
		public Object newParser0(String language) {
			if ("xml".equalsIgnoreCase(language))
				return new DefaultParser(this);
			throw new UnsupportedOperationException("default implementation only support xml");
		}

		@Override
		public void setProperty0(String name, Object value) {
			switch (Objects.requireNonNull(name, "name")) {
			case XML_ENTITY_RESOLVER_NAME:
				er = (EntityResolver) value;
				break;
			case XML_ERROR_HANDLER_NAME:
				eh = (ErrorHandler) value;
				break;
			case XML_PROPERTY_PREFIX:
				ps.put(name, value);
				break;
			case XML_FEATURE_PREFIX:
				fs.put(name, (Boolean) value);
				break;
			default:
				throw new Error();
			}
		}
	}

	public static final String FACTORY_PROPERTY = "chemsimple.database.simple.factory";

	public static final String LANGUAGE_JSON = "json";
	public static final String LANGUAGE_XML = "xml";

	public static final String XML_ENTITY_RESOLVER_NAME = "xml.entityResolver";
	public static final String XML_ERROR_HANDLER_NAME = "xml.errorHandler";

	public static final String XML_FEATURE_PREFIX = "xml.feature:";
	public static final String XML_PROPERTY_PREFIX = "xml.property:";

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
	public static CDBParserFactory newInstance(ClassLoader cl, String language) {
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
	public static CDBParserFactory newInstance(String language) {
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
	public static CDBParserFactory newInstance(String name, ClassLoader cl) {
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

	public final Object getProperty(String name) {
		if (getSupportedProperty().contains(Objects.requireNonNull(name, "name")))
			return getProperty0(name);
		else
			throw new IllegalArgumentException("Unsupported property: " + name);
	}

	protected abstract Object getProperty0(String name);

	/**
	 * Case insensitive. All non-null and non-empty.
	 * 
	 * @return
	 */
	public abstract Set<String> getSupportedLanguage();

	/**
	 * All non-null and non-empty.
	 * 
	 * @return
	 */
	public abstract Set<String> getSupportedProperty();

	/**
	 * @param plugin
	 *            the plugin to be injected into this {@link CDBParserFactory}.
	 */
	public void inject(ParserPlugin plugin) {
		throw new UnsupportedOperationException("this implementation supports no plugin");
	}

	public final boolean isLanguageSupported(String name) {
		return getSupportedLanguage().contains(name);
	}

	public final boolean isPropertySupported(String name) {
		return getSupportedProperty().contains(name);
	}

	public final Object newParser(String language) {
		if (getSupportedLanguage().contains(Objects.requireNonNull(language, "language")))
			return newParser0(language);
		throw new IllegalArgumentException("Unsupported language: " + language);
	}

	protected abstract Object newParser0(String language);

	public final XMLChemDatabaseParser newXMLParser() {
		return (XMLChemDatabaseParser) newParser(LANGUAGE_XML);
	}

	public final void setProperty(String name, Object value) {
		if (getSupportedProperty().contains(Objects.requireNonNull(name, "name")))
			setProperty0(name, value);
		else
			throw new IllegalArgumentException("Unsupported property: " + name);
	}

	protected abstract void setProperty0(String name, Object value);
}
