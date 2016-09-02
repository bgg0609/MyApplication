package bgg.com.commlibrary.http;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.model.MultipartRequestParams;
import com.android.volley.toolbox.MultipartRequest;
import com.android.volley.toolbox.MultipartRequest.UploadFileListener;
import com.android.volley.toolbox.OkHttpStack;
import com.android.volley.toolbox.Volley;

import java.io.File;

public abstract class GJKUploadRequest {
	public static RequestQueue queue;
	private String host = "";
	private String url;
	private Context context;

	protected UploadFileListener uploadFileListener;

	private Object backObj;

	public Object getBackObj() {
		return backObj;
	}

	public void setBackObj(Object backObj) {
		this.backObj = backObj;
	}

	private MultipartRequestParams params;
	private MultipartRequest request;

	public GJKUploadRequest(Context context, String host, String url) {
		this.host = host;
		this.url = url;
		this.context = context;
		this.params = new MultipartRequestParams();

		if (queue == null) {
			queue = Volley.newRequestQueue(this.context, new OkHttpStack());
		}
	}

	public void addParam(String key, Object value) {
		this.params.put(key, value);
	}
	
	public void addParam(String key, String value) {
		this.params.put(key, value);
	}

	public void AddFileParam(String key, File file) {
		this.params.put(key, file);
	}

	public void startUpload() {

		String urlStr = host + url;

		request = new MultipartRequest(Method.POST, urlStr, params, uploadFileListener, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if (uploadFileListener != null) {
					uploadFileListener.onUploadError(error.toString());
				}
			}
		});

		request.setBackObj(backObj);

		queue.add(request);

	}

	public void cancelUpload() {

		if (request != null) {
			request.cancel();
			this.uploadFileListener = null;
		}

	}

	public static RequestQueue getQueue() {
		return queue;
	}

	public static void setQueue(RequestQueue queue) {
		GJKUploadRequest.queue = queue;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	// public UploadFileListener getUploadFileListener() {
	// return uploadFileListener;
	// }
	//
	// public void setUploadFileListener(UploadFileListener uploadFileListener)
	// {
	// this.uploadFileListener = uploadFileListener;
	// }

	public MultipartRequestParams getParams() {
		return params;
	}

	public void setParams(MultipartRequestParams params) {
		this.params = params;
	}

}
