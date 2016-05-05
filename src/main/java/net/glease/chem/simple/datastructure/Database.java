
package net.glease.chem.simple.datastructure;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import net.glease.chem.simple.datastructure.impl.ChemDatabaseImpl;

public class Database extends JAXBElement<ChemDatabaseImpl> {

	private static final long serialVersionUID = 1L;
	protected final static QName NAME = new QName("http://glease.net/chem/simple/DataStructure", "database");

	public Database() {
		super(NAME, ChemDatabaseImpl.class, null, null);
	}

	public Database(ChemDatabaseImpl value) {
		super(NAME, ChemDatabaseImpl.class, null, value);
	}

}
