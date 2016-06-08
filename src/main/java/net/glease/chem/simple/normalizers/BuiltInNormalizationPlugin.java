package net.glease.chem.simple.normalizers;

import net.glease.chem.simple.CDBConstants;
import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.datastructure.NormalizationException;

final class BuiltInNormalizationPlugin implements NormalizationPlugin {
	private static final String VENDOR = "glease";
	private static final String INFO = "Built-in normalizer.";
	private final String name;
	private final String version;
	private final NormalizationFunction func;

	BuiltInNormalizationPlugin(String name, String version,
			NormalizationFunction func) {
		super();
		this.name = name;
		this.version = version;
		this.func = func;
	}

	BuiltInNormalizationPlugin(String name, NormalizationFunction func) {
		this(name, CDBConstants.VERSION, func);
	}

	@Override
	public String info() {
		return INFO;
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
		return VENDOR;
	}

	@Override
	public String version() {
		return version;
	}

}
