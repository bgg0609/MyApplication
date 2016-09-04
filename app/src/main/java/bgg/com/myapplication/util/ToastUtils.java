package bgg.com.myapplication.util;



import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 吐司工具类
 * 
 * @author 
 */
public class ToastUtils {

	private static final Object SYNC_LOCK = new Object();

	private static Toast mToast;

	@SuppressLint("ShowToast")
	public static Toast getToastInstance(Context context) {
		if (mToast == null) {
			synchronized (SYNC_LOCK) {
				if (mToast == null) {
					mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
				}
			}
		}
		return mToast;
	}

	/**
	 * 展示系统默认的吐司（展示位置在屏幕底部，短吐司）
	 * 
	 * @param context
	 *            环境
	 * @param text
	 *            内容
	 */
	public static void showToast(Context context, String text) {
		if (context == null || TextUtils.isEmpty(text)) return;
		
		if(mToast == null){
			getToastInstance(context);
		}
		
		mToast.setDuration(Toast.LENGTH_SHORT);
		mToast.setText(text);
		mToast.show();
	}

	/**
	 * 展示系统默认的吐司
	 * @param context
	 *            环境
	 * @param text
	 *            内容
	 * @param isLength_Long
	 *            是否长时间展示
	 */
	public static void showToast(Context context, String text, boolean isLength_Long) {
		if (context == null || TextUtils.isEmpty(text)) return;
		
		if(mToast == null){
			getToastInstance(context);
		}
		
		if (isLength_Long) {
			getToastInstance(context);
			mToast.setDuration(Toast.LENGTH_LONG);
			mToast.setText(text);
			mToast.show();
		} else {
			showToast(context, text);
		}
	}

	/**
	 * 展示新吐司
	 * 
	 * @param context
	 *            环境
	 * @param text
	 *            内容
	 */
	public static void showNewToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 展示新吐司
	 * 
	 * @param context
	 *            环境
	 * @param strId
	 *            内容资源id
	 */
	public static void showNewToast(Context context, int strId) {
		Toast.makeText(context, strId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 展示新吐司
	 * 
	 * @param context
	 *            环境
	 * @param text
	 *            内容
	 * @param isLength_Long
	 *            是否长时间展示
	 */
	public static void showNewToast(Context context, String text,
			boolean isLength_Long) {
		if (isLength_Long) {
			Toast.makeText(context, text, Toast.LENGTH_LONG).show();
		} else {
			showNewToast(context, text);
		}
	}
	
}
