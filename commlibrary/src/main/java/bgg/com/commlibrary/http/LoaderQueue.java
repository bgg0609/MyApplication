package bgg.com.commlibrary.http;

/**
 *远程图片下载队列
 * @author Robert
 */

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import java.util.ArrayList;
import java.util.List;


public class LoaderQueue {
	private static final int MEMORY_CACHE_SIZE_LIMIT = (int) (Runtime.getRuntime().maxMemory() / 3);
	public static LruCache<String, Bitmap> bitmapCache = new LruCache<String, Bitmap>(MEMORY_CACHE_SIZE_LIMIT);
	public static final int MAX_CONNECTIONS = 5;
	private List<ImageLoader> activeList = new ArrayList<ImageLoader>();
	private List<ImageLoader> queueList = new ArrayList<ImageLoader>();

	private static LoaderQueue instance;

	public static LoaderQueue getInstance() {
		if (instance == null) {
			instance = new LoaderQueue();
		}
		return instance;
	}

	public void push(ImageLoader imageLoader) {
		queueList.add(imageLoader);
		if (queueList.size() <= MAX_CONNECTIONS) {
			startNext();
		}
	}

	public void startNext() {

		if (!queueList.isEmpty()) {
			ImageLoader next = queueList.get(0);
			queueList.remove(0);
			activeList.add(next);
			next.load();
		}

	}

	public void didComplete(ImageLoader imageLoader) {
		activeList.remove(imageLoader);
		startNext();
	}

}
