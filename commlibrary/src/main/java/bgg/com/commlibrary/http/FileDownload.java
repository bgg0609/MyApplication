package bgg.com.commlibrary.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.listener.ProgressEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件下载类
 * 
 */
public class FileDownload implements Runnable {
	private static final int SUCCESSFUL = 0;
	private static final int FAILED = 1;
	private static final int PROGRESS = 2;

	private int currentPartSize;
	private RandomAccessFile currentPart;
	private String fileUrl;
	private String saveFile;
	private final FileDownloadListener listener;
	private HttpURLConnection conn;
	private CountingInputStream cis;
	private MyHandler handler;

	private static class MyHandler extends Handler {
		private final WeakReference<FileDownload> weakReference;

		public MyHandler(FileDownload uploadListener) {
			weakReference = new WeakReference<FileDownload>(uploadListener);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			if (weakReference.get() == null) {
				return;
			} else {
				FileDownload pd = weakReference.get();

				if (pd.listener != null) {
					switch (msg.what) {
					case FileDownload.SUCCESSFUL:
						pd.listener.onDownloadFinished(msg.obj.toString());
						break;
					case FileDownload.FAILED:
						// ph.uploadListener.uploadFailed();
						break;
					case FileDownload.PROGRESS:
						Object obj = msg.obj;
						Map<String, Long> map = (Map<String, Long>) obj;
						Long transfered = map.get("transfered");
						Long progress = map.get("progress");
						pd.listener.onProgress(null,transfered, progress.intValue());
						break;
					}
				}
			}
		}
	}

	public FileDownload(String fileUrl, String saveFile, FileDownloadListener listener) {
		this.fileUrl = fileUrl;
		this.saveFile = saveFile;
		this.listener = listener;
		this.handler = new MyHandler(this);

		Log.i("FileDownload", "初始化下载");

		try {
			this.currentPart = new RandomAccessFile(this.saveFile, "rw");
		} catch (FileNotFoundException e) {
			Log.e("FileDownload", "文件不存在");
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		try {
			if (currentPart != null) {

				URL url = new URL(fileUrl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5 * 1000);
				conn.setRequestMethod("GET");

				StringBuffer reqProp = new StringBuffer();
				reqProp.append("image/gif,");
				reqProp.append("image/jpeg,");
				reqProp.append("image/pjpeg,");
				reqProp.append("application/x-shockwave-flash,");
				reqProp.append("application/xaml+xml,");
				reqProp.append("application/vnd.ms-xpsdocument,");
				reqProp.append("application/x-ms-xbap,");
				reqProp.append("application/x-ms-application,");
				reqProp.append("application/vnd.ms-excel,");
				reqProp.append("application/vnd.ms-powerpoint,");
				reqProp.append("application/msword,");
				reqProp.append("*/*");

				conn.setRequestProperty("Accept", reqProp.toString());
				conn.setRequestProperty("Accept-Language", "zh-CN");
				conn.setRequestProperty("Charset", "UTF-8");

				currentPartSize = conn.getContentLength();
				Log.i("FileDownload", "文件大小：" + currentPartSize);

				InputStream inStream = conn.getInputStream();

				cis = new CountingInputStream(inStream, currentPartSize, progressEventListener);

				byte[] buffer = new byte[1024];
				int hasRead = 0;

				// 读取网络数据，并写入本地文件
				while ((hasRead = cis.read(buffer)) > 0) {
					currentPart.write(buffer, 0, hasRead);
				}

				Message msg = new Message();
				msg.what = SUCCESSFUL;
				msg.obj = saveFile;

				handler.sendMessage(msg);

				currentPart.close();
				inStream.close();
				cis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	ProgressEventListener progressEventListener = new ProgressEventListener() {

		@Override
		public void onProgress(Object objc,long transfered, int progress) {
			Message msg = new Message();
			msg.what = PROGRESS;
			Map<String, Long> obj = new HashMap<String, Long>();
			obj.put("transfered", (long) transfered);
			obj.put("progress", (long) progress);

			msg.obj = obj;

			handler.sendMessage(msg);
		}
	};

	public void cancel() {
		if (cis != null) {

			try {
				cis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			File tempFile = new File(saveFile);

			if (tempFile.exists()) {
				tempFile.delete();
			}
		}
		
		Thread.currentThread().interrupt();
		conn.disconnect();
	}

	public interface FileDownloadListener extends ProgressEventListener {
		public void onDownloadFinished(String fileName);
	}
}
