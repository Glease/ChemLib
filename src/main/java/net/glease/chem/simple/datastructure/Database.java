
package net.glease.chem.simple.datastructure;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class Database extends JAXBElement<ChemDatabase> {

	private static final long serialVersionUID = 1L;
	protected final static QName NAME = new QName("http://glease.net/chem/simple/DataStructure", "database");

	public Database() {
		super(NAME, ChemDatabase.class, null, null);
	}

	public Database(ChemDatabase value) {
		super(NAME, ChemDatabase.class, null, value);
	}

}
