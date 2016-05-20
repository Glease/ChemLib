package net.glease.chem.simple.util;

import java.io.Reader;
import java.nio.CharBuffer;

public class WriterReader extends Reader {

	private ReaderWriter bind;
    private CharArrayRef buf;
    int pos = 0;
	int mark = -1;
	WriterReader(CharArrayRef buf) {
		this.buf = buf;
	}

	void bind(ReaderWriter bind) {
		this.bind = bind;
	}

	@Override
	public long skip(long n) {
		synchronized (this) {
			int skipped = Math.min((int) n, bind.pos - pos);
			pos += skipped;
			return skipped;
		}
	}
	
	@Override
	public void reset() {
		synchronized (this) {
			if(mark!=-1) {
				pos = mark;
				mark = -1;
			}
		}
	}
	
	/**
	 * {@inheritDoc}<p>
	 * As the contract stated, the {@code readAheadLimit} is not necessary 
	 * to be respected. So I don't respect that.
	 */
	@Override
	public void mark(int readAheadLimit) {
		synchronized (this) {
			mark = pos;
		}
	}
	
	@Override
	public boolean markSupported() {
		return true;
	}
	
	@Override
	public int read(){
		synchronized (this) {
			return buf.getValue()[pos++];
		}
	}
	
	@Override
	public int read(char[] cbuf, int off, int len) {
		synchronized (this) {
			int c = Math.min(len, bind.pos - pos);
			
			System.arraycopy(buf.getValue(), pos, cbuf, off, c);
			
			pos += c;
			
			return c;
		}
	}

	@Override
	public boolean ready() {
		return !(pos == bind.pos);
	}
	
	@Override
	public void close() {
		bind.flush();
	}

	@Override
	public int read(CharBuffer target) {
        int len = target.remaining();
        char[] cbuf = new char[len];
        int n = read(cbuf, 0, len);
        if (n > 0)
            target.put(cbuf, 0, n);
        return n;
	}

	@Override
	public int read(char[] cbuf) {
        return read(cbuf, 0, cbuf.length);
	}
	
	
}
