package bgg.com.myapplication.business.http;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.OkHttpStack;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.concurrent.ConcurrentHashMap;

import bgg.com.commlibrary.http.NNHttpRequest;
import bgg.com.myapplication.MyApplication;

/**
 * HTTP 客户端，包含POST,GET,DELETE,PUT,PATCH请求
 */
public class ABOHttpRequest extends NNHttpRequest {


	private HttpEventListener httpEventListener;


	public ABOHttpRequest(Context context, String host, String webAPI) {
		super(context, host, webAPI);
		initParams();
	}
	public ABOHttpRequest(Context context, String webAPI) {
		super(context, WebAPI.HOST, webAPI);
		initParams();
	}


	public ABOHttpRequest(String webAPI) {
		super(MyApplication.getInstance(), WebAPI.HOST, webAPI);
		initParams();
	}

	private void initParams() {
		ConcurrentHashMap<String, Object> paramMap = new ConcurrentHashMap<String, Object>();
		CommonParamUtil.builderCommonParam(getContext(), paramMap);
		setParams(new JSONObject(paramMap));
	}

	@Override
	public void requestWithMethod(int method) {
		super.requestWithMethod(method);
	}

	/**
	 * 取消请求
	 */
	public void cancelRequest() {
		this.httpEventListener = null;
		super.cancelRequest();
	}

	public HttpEventListener getHttpEventListener() {
		return httpEventListener;
	}

	public void setHttpEventListener(HttpEventListener httpEventListener) {
		this.httpEventListener = httpEventListener;
	}

	@Override
	public void requestDidStart() {
		if (httpEventListener != null) {
			httpEventListener.requestDidStart();
		}
	}

	@Override
	public void requestDidSuccessful(int method, JSONObject response) {
		// 判断是否有实现监听
		if (httpEventListener != null) {
			Response rep= JSON.parseObject(response.toString(),Response.class);
			// 转换成基本结构
			rep.setWebAPI((String) getRequest().getTag());
			rep.setMethod(method);

			if (rep.isOk()) {//  判断是否成功执行
				httpEventListener.requestDidSuccessful(rep);
			} else {// 失败了，可以统一在此处理一些事儿
				StringBuffer msg = new StringBuffer(rep.getMsg());
				msg.append("(");
				msg.append(rep.getCode());
				msg.append(")");

				httpEventListener.requestDidError(rep);
			}

		}

	}

	@Override
	public void requestDidError(int method, int code, String errorMsg) {
		if (httpEventListener != null) {
			Response rep = new Response();
			rep.setWebAPI((String) getRequest().getTag());
			rep.setMsg(errorMsg);
			rep.setMethod(method);

			httpEventListener.requestDidError(rep);
		}
	}

	public static RequestQueue getRequestQueue(Context context) {

		if (NNHttpRequest.queue == null) {
			NNHttpRequest.queue = Volley.newRequestQueue(context, new OkHttpStack());
		}

		return NNHttpRequest.queue;
	}

	/**
	 * 
	 * @author robert
	 *
	 *         HTTP请求回调接口
	 */
	public interface HttpEventListener {
		public void requestDidStart();

		public void requestDidSuccessful(Response response);

		public void requestDidError(Response response);
	}

}
