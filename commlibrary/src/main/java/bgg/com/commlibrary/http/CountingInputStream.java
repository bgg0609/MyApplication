package bgg.com.commlibrary.http;

import com.android.volley.listener.ProgressEventListener;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 输入流派生类
 * 
 */
public class CountingInputStream extends FilterInputStream {

	private final ProgressEventListener listener;
	private long transferred;
	private long contentLength;

	protected CountingInputStream(final InputStream in, long contentLength, final ProgressEventListener listener) {
		super(in);
		this.listener = listener;
		this.contentLength = contentLength;
		this.transferred = 0;
	}

	@Override
	public int read() throws IOException {
		int read = in.read();
		readCount(read);
		return read;
	}

	@Override
	public int read(byte[] buffer) throws IOException {
		int read = in.read(buffer);
		readCount(read);
		return read;
	}

	@Override
	public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
		int read = in.read(buffer, byteOffset, byteCount);
		readCount(read);
		return read;
	}

	@Override
	public long skip(long byteCount) throws IOException {
		long skip = in.skip(byteCount);
		readCount(skip);
		return skip;
	}

	private void readCount(long read) {

		if (read > 0) {
			this.transferred += read;
			int prog = (int) (transferred * 100 / contentLength);
			this.listener.onProgress(null,transferred, prog);
		}

	}
}
