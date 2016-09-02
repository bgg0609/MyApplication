package bgg.com.commlibrary.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import bgg.com.commlibrary.utils.FileUtils;
import bgg.com.commlibrary.utils.Md5;

/**
 * 图片加载
 *
 * @author Robert
 */

/**
 * 
 * @author robert
 *
 */
public class ImageLoader extends AsyncTask<String, Integer, Bitmap> {
	public static final int SUCCESSFUL = 0;
	public static final int FAILED = 1;
	public static final int BEGIN = 2;

	// public static HashMap<String, SoftReference<Bitmap>> cacheBitmaps = new
	// HashMap<String, SoftReference<Bitmap>>();

	private String imgURL;
	private String baseDir;
	private String localPath;
	private String fileName;

	private int screenWidth;
	private int screenHeight;

	private ImageLoaderListener imageLoaderListener;

	public ImageLoader(String url, int screenWidth, int screenHeight) {
		this.imgURL = url;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

		this.baseDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
				+ "abodoctor/images/";
		makePath();
	}

	private void makePath() {
		String[] temp = imgURL.split("\\/");
		fileName = temp[temp.length - 1];

		int pointPosition = fileName.lastIndexOf(".");

		if (pointPosition > 0) {
			fileName = fileName.substring(0, pointPosition);
		}

		localPath = baseDir + File.separator + fileName;
	}

	public void loadImage() {
		LoaderQueue.getInstance().push(this);
	}

	public void load() {
		if (imgURL == null || imgURL.equals("")) {
			return;
		}

		Bitmap bitmap = LoaderQueue.bitmapCache.get(Md5.md5(imgURL));

		if (bitmap == null) {
			File f = new File(localPath);
			Bitmap drawable = null;

			if (f.exists()) {
				drawable = BitmapFactory.decodeFile(localPath);
			}

			if (drawable == null) {
				FileUtils.deleteFile(localPath);
				execute();
			} else {
				LoaderQueue.bitmapCache.put(Md5.md5(imgURL), drawable);

				if (imageLoaderListener != null) {
					imageLoaderListener.loadFinished(drawable);
					LoaderQueue.getInstance().didComplete(this);
				}
			}

		} else {
			if (imageLoaderListener != null) {
				imageLoaderListener.loadFinished(bitmap);
				LoaderQueue.getInstance().didComplete(this);
			}
		}
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		if (imageLoaderListener != null) {
			imageLoaderListener.loadBegin();
		}

		try {

			URL url = new URL(imgURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.connect();

			InputStream is = conn.getInputStream();
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inTempStorage = new byte[12 * 1024];
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inJustDecodeBounds = true;

			byte[] data = getBytes(is);
			BitmapFactory.decodeByteArray(data, 0, data.length, opt);

			int inSampleSize = computeSampleSize(opt);

			opt.inSampleSize = inSampleSize;

			// 获取文件流
			opt.inJustDecodeBounds = false;
			Bitmap drawable = BitmapFactory.decodeByteArray(data, 0, data.length, opt);
			// Bitmap drawable = BitmapFactory.decodeStream(is,null,opt);

			// 写入SDCard
			if (FileUtils.isExistSDCard()) {
				FileUtils.write2SDFromInput(localPath, is);
			}

			is.close();
			conn.disconnect();

			return drawable;
		} catch (MalformedURLException e) {
			File file = new File(imgURL);

			if (file.exists()) {

				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inPreferredConfig = Bitmap.Config.RGB_565;
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(imgURL, opts);

				opts.inSampleSize = computeSampleSize(opts);
				opts.inJustDecodeBounds = false;
				Bitmap drawable = BitmapFactory.decodeFile(imgURL, opts);

				return drawable;
			}
		} catch (IOException e) {
			mHandler.sendEmptyMessage(FAILED);
		}

		return null;
	}

	public int computeSampleSize(BitmapFactory.Options opts) {
		final int width = opts.outWidth;
		final int height = opts.outHeight;

		int inSampleSize = 1;

		if (width > screenWidth || height > screenHeight) {
			if (width > height) {
				inSampleSize = Math.round((float) width / (float) screenWidth);
			} else {
				inSampleSize = Math.round((float) height / (float) screenHeight);
			}
		}

		return inSampleSize;
	}

	public static byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024]; // 用数据装
		int len = -1;
		while ((len = is.read(buffer)) != -1) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();
		// 关闭流一定要记得。
		return outstream.toByteArray();
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {

		if (imageLoaderListener != null) {
			if (bitmap != null) {
				imageLoaderListener.loadFinished(bitmap);
				LoaderQueue.bitmapCache.put(Md5.md5(imgURL), bitmap);
				LoaderQueue.getInstance().didComplete(this);
			} else {
				imageLoaderListener.loadFailed();
				LoaderQueue.getInstance().didComplete(this);
			}
		}

	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public ImageLoaderListener getImageLoaderListener() {
		return imageLoaderListener;
	}

	public void setImageLoaderListener(ImageLoaderListener imageLoaderListener) {
		this.imageLoaderListener = imageLoaderListener;
	}

	/**
	 * 使用静态的内部类，不会持有当前对象的引用
	 */
	private static class MyHandler extends Handler {

		private final WeakReference<ImageLoaderListener> listenerRreference;

		public MyHandler(ImageLoaderListener loaderListener) {
			listenerRreference = new WeakReference<ImageLoaderListener>(loaderListener);
		}

		@Override
		public void handleMessage(Message msg) {
			ImageLoaderListener listener = listenerRreference.get();
			if (listener != null) {
				switch (msg.what) {
				case FAILED:
					listener.loadFailed();
					break;
				case BEGIN:
					listener.loadBegin();
					break;
				}
			}
		}
	}

	private final MyHandler mHandler = new MyHandler(this.imageLoaderListener);

	/**
	 * 
	 * 
	 * @author Robert
	 * 
	 */
	public interface ImageLoaderListener {
		public void loadFailed();

		public void loadBegin();

		public void loadFinished(Bitmap bitmap);
	}

}
