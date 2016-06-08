package net.glease.chem.simple.normalizers;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.glease.chem.simple.datastructure.NormalizationException;

class IDConflictException extends NormalizationException {

	private static final long serialVersionUID = 1L;

	public IDConflictException(Map<String, List<?>> conflicts) {
		super(makeCause(conflicts));
	}

	private static String makeCause(Map<String, List<?>> conflicts) {
		StringBuilder sb = new StringBuilder("ID conflict detected during normalization. Here is a list of them:");
		for (Entry<String, List<?>> e : conflicts.entrySet()) {
			sb.append("\tID: ");
			sb.append(e.getKey());
			sb.append('\n');
			for (Object c : e.getValue()) {
				sb.append("\t\tElement: ");
				sb.append(c);
				sb.append('\n');
			}
		}
		return sb.toString();
	}
}
