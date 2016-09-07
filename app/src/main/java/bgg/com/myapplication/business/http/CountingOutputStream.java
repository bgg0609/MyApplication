package bgg.com.myapplication.business.http;

import com.android.volley.listener.ProgressEventListener;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 输出流派生类
 * 
 */
public class CountingOutputStream extends FilterOutputStream {
	private ProgressEventListener progListener;
	private long transferred;
	private long fileLength;

	public ProgressEventListener getProgListener() {
		return progListener;
	}

	public void setProgListener(ProgressEventListener progListener) {
		this.progListener = progListener;
	}

	public CountingOutputStream(OutputStream out, long fileLength, ProgressEventListener listener) {
		super(out);
		this.fileLength = fileLength;
		this.progListener = listener;
		this.transferred = 0;
	}

	public void write(byte[] b, int off, int len) throws IOException {
		if (progListener != null) {

			this.transferred += len;

			int prog = (int) (transferred * 100 / fileLength);

			System.err.println("===1==transferred:"+transferred+"    progress:"+prog);
			this.progListener.onProgress(null,this.transferred, prog);
		}
		
		out.write(b, off, len);
	}

	public void write(int b) throws IOException {

		if (progListener != null) {
			this.transferred++;

			int prog = (int) (transferred * 100 / fileLength);
			this.progListener.onProgress(null,this.transferred, prog);
			System.err.println("==2===transferred:"+transferred+"    progress:"+prog);
		}

		out.write(b);
	}

}