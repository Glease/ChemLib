package net.glease.chem.simple.util;

import net.glease.chem.simple.parsers.CDBParserFactory;

/**
 * This interface roughly designed plugin for {@link CDBParserFactory}s.
 * Implementations of {@link CDBParserFactory} should refine this specification
 * if they support plugin.
 * 
 * @author glease
 *
 */
public interface ParserPlugin {
	/**
	 * Get the version info of this {@link NormalizationPlugin}
	 * 
	 * @return the version info
	 */
	String info();

	/**
	 * Get target language.
	 * 
	 * @return
	 */
	String language();

	/**
	 * Get the name of this {@link NormalizationPlugin}
	 * 
	 * @return the name
	 */
	String name();

	/**
	 * Get the vendor info of this {@link NormalizationPlugin}
	 * 
	 * @return the vendor info
	 */
	String vendor();

	/**
	 * Get the version info of this {@link NormalizationPlugin}
	 * 
	 * @return the version info
	 */
	String version();

}
