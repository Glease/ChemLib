package net.glease.chem.simple.util;

class CharArrayRef {
	private char[] c;
	public CharArrayRef(char[] ds) {
		c = ds;
	}
	public char[] getValue() {
		return c;
	}
	public void setValue(char[] c) {
		this.c = c;
	}

}
