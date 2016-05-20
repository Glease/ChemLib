package net.glease.chem.simple.util;

public class ReaderWriterPair {
	private final ReaderWriter rw;
	private final WriterReader wr;
	ReaderWriterPair(ReaderWriter rw, WriterReader wr) {
		super();
		this.rw = rw;
		this.wr = wr;
	}
	public ReaderWriter getRw() {
		return rw;
	}
	public WriterReader getWr() {
		return wr;
	}
	public static ReaderWriterPair create() {
		CharArrayRef a = new CharArrayRef(new char[32]);
		WriterReader wr = new WriterReader(a);
		ReaderWriter rw = new ReaderWriter(a);
		wr.bind(rw);
		rw.bind(wr);
		return new ReaderWriterPair(rw, wr);
	}
}
