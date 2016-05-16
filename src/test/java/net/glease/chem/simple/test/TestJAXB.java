package net.glease.chem.simple.test;

import java.io.File;
import java.io.StringWriter;

import net.glease.chem.simple.datastructure.ChemDatabase;
import net.glease.chem.simple.parsers.CDBParserFactory;
import net.glease.chem.simple.parsers.XMLChemDatabaseParser;

/**
 * This test is naive, silly, ugly and not automatic, but I'm lazy...
 * 
 * @author glease
 *
 */
public class TestJAXB {

	public static void main(String[] args) throws Throwable {
		XMLChemDatabaseParser p = CDBParserFactory.newInstance(CDBParserFactory.LANGUAGE_XML).newXMLParser();

		File in = new File(System.getProperty("user.dir"), "./target/classes/SampleCDB.xml");

		System.out.println(in);

		ChemDatabase db = p.unmarshal(in);

		System.out.println(db);

		StringWriter sw = new StringWriter();

		p.marshal(db, sw);

		System.out.println(sw.toString());
	}

}
