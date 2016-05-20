package net.glease.chem.simple.util;

import java.io.Writer;
import java.util.Arrays;

public class ReaderWriter extends Writer {
	static final int DEFAULT_CHAR_ARRAY_SIZE = 32;
	private WriterReader bind;
    private CharArrayRef buf;
	int pos = 0;

	ReaderWriter(CharArrayRef buf) {
		this.buf = buf;
	}
	
	void bind(WriterReader bind) {
		this.lock = this.bind = bind;
	}

	@Override
	public void write(int c) {
		synchronized (lock) {
			buf.getValue()[move(1)] = (char) c;
		}
	}
	
	@Override
	public void write(char[] cbuf) {
		write(cbuf, 0, cbuf.length);
	}

	@Override
	public void write(String str) {
		write(str,0,str.length());
	}

	@Override
	public void write(String str, int off, int len) {
        write(str.toCharArray(),off,len);
	}

	@Override
	public Writer append(CharSequence csq) {
        if (csq == null)
            write("null");
        else
            write(csq.toString());
        return this;
	}

	@Override
	public Writer append(CharSequence csq, int start, int end) {
        CharSequence cs = (csq == null ? "null" : csq);
        write(cs.subSequence(start, end).toString());
        return this;
	}

	@Override
	public Writer append(char c) {
		write(c);
		return this;
	}

	private int move(int length) {
		int old = pos;
		pos += length;
		if(buf.getValue().length<=pos) {
			int start = bind.mark == -1 ? bind.pos : bind.mark;
			pos -= start;
			int size = Math.max(DEFAULT_CHAR_ARRAY_SIZE, pos * 2 + 2);
			resize(start, size);
			old -= bind.pos;
			if(bind.mark ==-1)
				bind.pos =  0;
			else {
				bind.pos -= bind.mark; 
				bind.mark = 0;
			}
		}
		return old;
	}
	
	@Override
	public void write(char[] cbuf, int off, int len) {
		synchronized (lock) {
			int old = move(len);
			System.arraycopy(cbuf, off, buf.getValue(), old, len);
		}
	}
	
	/**
	 * Wipe the read part immediately.
	 */
	@Override
	public void flush() {
		synchronized (lock) {
			int start = bind.mark == -1 ? bind.pos : bind.mark;
			pos -= start;
			int size = Math.max(DEFAULT_CHAR_ARRAY_SIZE, pos*2);
			resize(bind.pos, size);
			if(bind.mark ==-1)
				bind.pos =  0;
			else {
				bind.pos -= bind.mark; 
				bind.mark = 0;
			}
		}
	}

	private void resize(int from, int length) {
		buf.setValue(Arrays.copyOfRange(buf.getValue(), from, length));
	}
	
	/**
	 * Wipe the read part immediately.
	 */
	@Override
	public void close() {
		flush();
	}
}
