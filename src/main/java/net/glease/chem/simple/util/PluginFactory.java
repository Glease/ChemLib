package net.glease.chem.simple.util;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import net.glease.chem.simple.normalizers.NormalizationPlugin;

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
