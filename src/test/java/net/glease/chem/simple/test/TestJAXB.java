package net.glease.chem.simple.test;

import java.io.File;
import java.io.StringWriter;

import net.glease.chem.simple.datastructure.impl.ChemDatabaseImpl;
import net.glease.chem.simple.parsers.DefaultParser;

/**
 * This test is naive, silly, ugly and not automatic, but I'm lazy...
 * @author glease
 *
 */
public class TestJAXB {
	
	public static void main(String[] args) throws Throwable {
		DefaultParser p = new DefaultParser();
		
		p.init();
		
		ChemDatabaseImpl db = p.unmarshal(new File("bin/SampleCDB.xml"));
		
		StringWriter sw = new StringWriter();
		
		p.marshal(db, sw);
		
		System.out.println(sw.toString());
	}
	
}
