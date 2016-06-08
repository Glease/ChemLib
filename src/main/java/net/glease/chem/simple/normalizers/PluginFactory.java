package net.glease.chem.simple.normalizers;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/**
 * Helper methods to find certain {@link NormalizationPlugin}.
 * @author glease
 * @since 0.1
 */
public final class PluginFactory {
	public static NormalizationPlugin newInstance(String name) {
		return StreamSupport.stream(ServiceLoader.load(NormalizationPlugin.class).spliterator(), false)
				.filter(np -> name.equals(np.name())).findFirst().get();
	}

	public static NormalizationPlugin newInstance(String name, ClassLoader cl) {
		return StreamSupport.stream(ServiceLoader.load(NormalizationPlugin.class, cl).spliterator(), false)
				.filter(np -> name.equals(np.name())).findFirst().get();
	}
}
