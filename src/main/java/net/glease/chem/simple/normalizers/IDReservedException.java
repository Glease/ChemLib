package net.glease.chem.simple.normalizers;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.glease.chem.simple.datastructure.Element;
import net.glease.chem.simple.datastructure.NormalizationException;

class IDReservedException extends NormalizationException {

	private static final long serialVersionUID = 1L;

	public IDReservedException(Map<String, Set<Element<?>>> reserved) {
		super(makeCause(reserved));
	}

	private static String makeCause(Map<String, Set<Element<?>>> reserved) {
		StringBuilder sb = new StringBuilder("Reserved ID detected during normalization. Here is a list of them:");
		for (Entry<String, Set<Element<?>>> e : reserved.entrySet()) {
			sb.append("\tReserved ID prefix: ");
			sb.append(e.getKey());
			sb.append('\n');
			for (Element<?> c : e.getValue()) {
				sb.append("\t\tID: ");
				sb.append(c.getId());
				sb.append("\n\t\tElement: ");
				sb.append(c);
				sb.append('\n');
			}
		}
		return sb.toString();
	}

}
