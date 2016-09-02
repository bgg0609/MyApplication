package com.android.volley.model;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.android.volley.toolbox.MultipartRequest.UploadFileListener;

import android.os.Handler;
import android.os.Looper;

public class MultipartRequestParams {
	private UploadFileListener uploadFileListener;
	private Object backObj;

	private ConcurrentHashMap<String, Object> urlParams;
	private ConcurrentHashMap<String, FileWrapper> fileParams;
	private final static char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			.toCharArray();

	public Object getBackObj() {
		return backObj;
	}

	public void setBackObj(Object backObj) {
		this.backObj = backObj;
	}

	public String boundary = null;

	public UploadFileListener getUploadFileListener() {
		return uploadFileListener;
	}

	public void setUploadFileListener(UploadFileListener uploadFileListener) {
		this.uploadFileListener = uploadFileListener;
	}

	public MultipartRequestParams() {
		init();
	}

	public MultipartRequestParams(String key, String value) {
		init();
		put(key, value);
	}

	private void init() {
		urlParams = new ConcurrentHashMap<String, Object>();
		fileParams = new ConcurrentHashMap<String, FileWrapper>();

		final StringBuffer buf = new StringBuffer();
		final Random rand = new Random();
		for (int i = 0; i < 30; i++) {
			buf.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
		}
		this.boundary = buf.toString();
	}

	public ConcurrentHashMap<String, Object> getUrlParams() {
		return urlParams;
	}

	public void setUrlParams(ConcurrentHashMap<String, Object> urlParams) {
		this.urlParams = urlParams;
	}

	public ConcurrentHashMap<String, FileWrapper> getFileParams() {
		return fileParams;
	}

	public void setFileParams(ConcurrentHashMap<String, FileWrapper> fileParams) {
		this.fileParams = fileParams;
	}

	public void remove(String key) {
		if (key != null) {
			urlParams.remove(key);
		}
	}
	
	/**
	 * 添加value到request中
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) {
		if (key != null && value != null) {
			urlParams.put(key, value);
		}
	}

	/**
	 * 添加value到request中
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, String value) {
		if (key != null && value != null) {
			urlParams.put(key, value);
		}
	}

	/**
	 * 添加文件到request中
	 * 
	 * @param key
	 * @param file
	 */
	public void put(String key, File file) {
		try {
			put(key, new FileInputStream(file), file.getName(), file.length());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加stream到request中
	 * 
	 * @param key
	 * @param stream
	 * @param fileName
	 */
	public void put(String key, InputStream stream, String fileName, long fileSize) {
		put(key, stream, fileName, null, fileSize);
	}

	/**
	 * 添加stream到request中
	 * 
	 * @param key
	 * @param stream
	 * @param fileName
	 * @param contentType
	 */
	public void put(String key, InputStream stream, String fileName, String contentType, long fileSize) {
		if (key != null && stream != null) {
			fileParams.put(key, new FileWrapper(stream, fileName, contentType, fileSize));
		}
	}

	public byte[] getBody() {
		// Add string params
		StringBuffer sb = new StringBuffer();
		int index = 0;

		for (ConcurrentHashMap.Entry<String, Object> entry : urlParams.entrySet()) {
			index++;
			if (index == 1) {
				sb.append("--" + boundary + "\r\n");
			}

			sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
			sb.append(entry.getValue());
			sb.append("\r\n--" + boundary + "\r\n");
		}

		return sb.toString().getBytes();
	}

	public void write(OutputStream out) {
		DataOutputStream dos = (DataOutputStream) out;
		try {

			// Add file params
			int index = 0;
			int fileCount = fileParams.entrySet().size();

			for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
				final FileWrapper file = entry.getValue();
				index++;

				if (file.inputStream != null) {
					// 发送文件数据
					StringBuilder split = new StringBuilder();
					split.append("Content-Disposition: form-data;name=\"" + entry.getKey() + "\";filename=\""
							+ file.getFileName() + "\"\r\n");
					split.append("Content-Type: " + file.contentType + "\r\n");
					split.append("Content-Transfer-Encoding: binary\r\n\r\n");

					dos.write(split.toString().getBytes());

					if (file.inputStream != null) {
						byte[] buffer = new byte[1024];
						int len = -1;
						int count = 0;
						while ((len = file.inputStream.read(buffer)) != -1) {
							dos.write(buffer, 0, len);
							count += len;
							final int prog = (int) (count * 100 / file.fileSize);

							Handler mainThread = new Handler(Looper.getMainLooper());
							mainThread.post(new Runnable() {
								@Override
								public void run() {
									if (uploadFileListener != null) {
										uploadFileListener.onProgress(backObj, file.fileSize, prog);
									}
								}
							});

						}
						file.inputStream.close();
					}

					if (index < fileCount) {
						dos.write(("\r\n--" + boundary).getBytes());
					}

				}
			}

			dos.writeBytes("\r\n--" + boundary + "--\r\n");
			dos.flush();

		} catch (IOException e) {
			if (uploadFileListener != null) {
				uploadFileListener.onUploadError(e.toString());
			}

			try {
				dos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public MultipartEntity getEntity() {
		MultipartEntity entity = null;
		if (!fileParams.isEmpty()) {
			MultipartEntity multipartEntity = new MultipartEntity();

			// Add string params
			for (ConcurrentHashMap.Entry<String, Object> entry : urlParams.entrySet()) {
				multipartEntity.addPart(entry.getKey(), String.valueOf(entry.getValue()));
			}

			// Add file params
			int currentIndex = 0;
			int lastIndex = fileParams.entrySet().size() - 1;
			for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
				FileWrapper file = entry.getValue();
				if (file.inputStream != null) {
					boolean isLast = currentIndex == lastIndex;
					if (file.contentType != null) {
						multipartEntity.addPart(entry.getKey(), file.getFileName(), file.inputStream, file.contentType,
								isLast);
					} else {
						multipartEntity.addPart(entry.getKey(), file.getFileName(), file.inputStream, isLast);
					}
				}
				currentIndex++;
			}

			entity = multipartEntity;
		}
		return entity;
	}

	private static class FileWrapper {
		public InputStream inputStream;
		public String fileName;
		public String contentType = "application/octet-stream";
		public long fileSize;

		public FileWrapper(InputStream inputStream, String fileName, String contentType, long fileSize) {
			this.inputStream = inputStream;
			this.fileName = fileName;
			this.fileSize = fileSize;

			if (contentType != null) {
				this.contentType = contentType;
			}
		}

		public String getFileName() {
			if (fileName != null) {
				return fileName;
			} else {
				return "nofilename";
			}
		}
	}
}