package bgg.com.myapplication.business.http;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileDownloadManager {
	public static ExecutorService pool;

	static {
		pool = Executors.newCachedThreadPool();
	}

	public FileDownload downloadFile(String fileUrl, String savePath, FileDownload.FileDownloadListener listener) {
		FileDownload fd = new FileDownload(fileUrl, savePath, listener);
		pool.submit(fd);
		return fd;
	}

}
