package bgg.com.myapplication.util;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * HTTP请求相关的一个工具
 * 
 */
public class HttpUtils {
	private static final String TAG = "HTTP";

	/**
	 * 输入日志
	 * 
	 * @param logStr
	 */
	public static void printLog(Object logStr) {
		if (logStr instanceof JSONObject) {
			JSONObject obj = (JSONObject) logStr;

			try {
				Log.i(TAG, obj.toString(4));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Log.i(TAG, logStr.toString());
		}
	}

	/**
	 * 将JSONObject参数转换成Get请求的参数格式
	 * 
	 * @param mJsonObject
	 * @return
	 */
	public static String jsonToURLParams(JSONObject mJsonObject, boolean encode) {

		Iterator<String> keys = mJsonObject.keys();
		HashMap<String, Object> params = new HashMap<String, Object>();

		while (keys.hasNext()) {
			String key = keys.next();
			try {
				Object value = mJsonObject.get(key);

				if (value instanceof Number) {
					params.put(key, mJsonObject.get(key));
				} else {
					if (encode) {
						params.put(key, Uri.encode(mJsonObject.getString(key)));
					} else {
						params.put(key, mJsonObject.getString(key));
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		Collection<String> keyset = params.keySet();
		List<String> list = new ArrayList<String>(keyset);

		// 对key键值按字典升序排序
		Collections.sort(list);

		StringBuilder mStringBuilder = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			Object value = params.get(list.get(i));
			mStringBuilder.append(list.get(i));
			mStringBuilder.append("=");

			// 这里主用于解决浮点型，小数为0时会自动添加.0，造成签名错误的问题，不处理会出现这样的错误。
			// 例如：20，会改成20.0
			if (value instanceof Number) {
				Number srcVal = (Number) value;

				// 如果长整和浮点值相等，说明小数点后面为0，那里转值和签名统一用去掉小数点来处理
				if (srcVal.longValue() == srcVal.floatValue()) {
					mStringBuilder.append(srcVal.longValue());
				} else {// 否则带小数点和小数位数值传值和签名
					mStringBuilder.append(value);
				}

			} else {
				mStringBuilder.append(value);
			}

			if (i < list.size() - 1) {
				mStringBuilder.append("&");
			}
		}

		return mStringBuilder.toString();
	}
}
