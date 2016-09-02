package com.android.volley.toolbox;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache.Entry;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.listener.ProgressEventListener;
import com.android.volley.model.MultipartRequestParams;

import android.util.Log;

/**
 * 上传文件带进度
 * 
 * @author robert
 *
 */
public class MultipartRequest extends Request<JSONObject> {
	private static final String TAG=MultipartRequest.class.getSimpleName();
	
	private MultipartRequestParams params = null;
	private UploadFileListener listener;
	private ErrorListener errorListener;
	private Object backObj;
	
	private String postBodyContentType="application/x-www-form-urlencoded; charset=UTF-8";
	
	public String getPostBodyContentType() {
		this.postBodyContentType="multipart/form-data;boundary="+this.params.boundary;
		return postBodyContentType;
	}


	public void setPostBodyContentType(String postBodyContentType) {
		this.postBodyContentType = postBodyContentType;
	}


	public Object getBackObj() {
		return backObj;
	}


	public void setBackObj(Object backObj) {
		this.backObj = backObj;
		this.params.setBackObj(backObj);
	}
	
	public MultipartRequest(int method, String url, MultipartRequestParams params, UploadFileListener listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);

		this.params = params;
		this.params.setUploadFileListener(listener);
		this.listener = listener;
		this.errorListener = errorListener;
	}
	
  @Override
  public Map<String, String> getHeaders() throws AuthFailureError {
      HashMap<String, String> headerMap = new HashMap<String, String>();
      headerMap.put("Charset", "UTF-8");
      headerMap.put("Connection", "Keep-Alive"+this.params.boundary);
      headerMap.put("Content-Type", "multipart/form-data; boundary=" + this.params.boundary);
      return headerMap;
  }

	@Override
	public byte[] getBody() throws AuthFailureError {
		if (listener!=null) {
			listener.onStartUpload();
		}
		
		return params.getBody();
	}
	
	@Override
	public void handRequest(OutputStream out) {
		params.write(out);
	}

	@Override
	protected void deliverResponse(JSONObject response) {
		try {
			Log.d(TAG, "deliverResponse:" + response.toString(1));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		if (listener!=null) {
			listener.onUploadFinished(backObj,response);
		}
	}

	@Override
	public void deliverError(VolleyError error) {
		Log.d(TAG, "deliverError:" + error.toString());
		
		if (errorListener != null) {
			errorListener.onErrorResponse(error);
		}
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			Log.d(TAG, "parseNetworkResponse:" + jsonString);
			
			JSONObject json=new JSONObject(jsonString);
			Entry cacheHeader = HttpHeaderParser.parseCacheHeaders(response);
			return Response.success(json, cacheHeader);
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(response));
		} catch (JSONException je) {
			return Response.error(new ParseError(response));
		}
	}


	public UploadFileListener getListener() {
		return listener;
	}

	public void setListener(UploadFileListener listener) {
		this.listener = listener;
	}

	public ErrorListener getErrorListener() {
		return errorListener;
	}

	public void setErrorListener(ErrorListener errorListener) {
		this.errorListener = errorListener;
	}
	
	public interface UploadFileListener extends ProgressEventListener{
		public void onStartUpload();
		public void onUploadFinished(Object backObj,JSONObject response);
		public void onUploadError(String errorInfo);
	}
	
}
