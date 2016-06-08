package net.glease.chem.simple.parsers;

import java.util.Set;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.ChemDatabaseComponent;
import net.glease.chem.simple.normalizers.NormalizationPlugin;
import net.glease.chem.simple.scoping.BindingPlugin;

/**
 * This interface roughly designed plugin for {@link CDBParserFactory}s.
 * Implementations of {@link CDBParserFactory} should refine this specification
 * if they support plugin.
 * <p>
 * As of version 0.1, the {@link ParserPlugin} only define two meaningful
 * method: {@link #injectedBindingPlugin()} and
 * {@link #injectedNormalizationPlugin()}.
 * 
 * @author glease
 * @since 0.1
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

	/**
	 * Get a set of {@link BindingPlugin BindingPlugins} that should be
	 * installed on desired newly-created {@link ChemDatabaseComponent
	 * ChemDatabaseComponents}.
	 * 
	 * @return a set of {@link BindingPlugin BindingPlugins}
	 */
	Set<BindingPlugin<?>> injectedBindingPlugin();

	/**
	 * Get a set of {@link NormalizationPlugin NormalizationPlugins} that should
	 * be installed on newly-created {@link ChemDatabase ChemDatabases}.
	 * 
	 * @return a set of {@link NormalizationPlugin NormalizationPlugins}
	 */
	Set<NormalizationPlugin> injectedNormalizationPlugin();
}
