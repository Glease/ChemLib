package net.glease.chem.simple.normalizers;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.NormalizationException;

final class SimpleNormalizationPlugin implements NormalizationPlugin {
	private final String info;
	private final String name;
	private final String vendor;
	private final String version;
	private final NormalizationFunction func;

	SimpleNormalizationPlugin(String info, String name, String vendor, String version,
			NormalizationFunction func) {
		super();
		this.info = info;
		this.name = name;
		this.vendor = vendor;
		this.version = version;
		this.func = func;
	}

	@Override
	public String info() {
		return info;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public void normalize(ChemDatabase cdb) throws NormalizationException {
		func.normalize(cdb);
	}

	@Override
	public String vendor() {
		return vendor;
	}

	@Override
	public String version() {
		return version;
	}

}
