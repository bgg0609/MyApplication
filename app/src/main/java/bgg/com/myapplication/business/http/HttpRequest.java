package bgg.com.myapplication.business.http;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.OkHttpStack;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import bgg.com.myapplication.util.HttpUtils;


/**
 * Http 请求的超类
 */
public abstract class HttpRequest {
    public static RequestQueue queue;
    private String host = "";

    private JsonObjectRequest request;
    private JSONObject params;

    private String webAPI;
    private Context context;

    private HttpMethod requestMethod;

    public HttpMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(HttpMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public HttpRequest(Context context, String host, String webAPI) {
        this.webAPI = webAPI;
        this.host = host;
        this.context = context;
        this.params = new JSONObject();

        if (queue == null) {
            synchronized (HttpRequest.class) {
                if (queue == null) {
                    queue = Volley.newRequestQueue(this.context, new OkHttpStack());
                }
            }
        }
    }

    public abstract void requestDidStart();

    /**
     * 请求成功
     *
     * @param response
     */
    public abstract void requestDidSuccessful(HttpMethod method, JSONObject response);

    /**
     * 请求失败
     *
     * @param code
     * @param errorMsg
     */
    public abstract void requestDidError(HttpMethod method, int code, String errorMsg);

    /**
     * 添加请求的参数
     *
     * @param key   参数名
     * @param value 参数值
     */
    public void addParam(String key, Object value) {
        try {
            params.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行HTTP请求的方法
     *
     * @param method
     */
    public void requestWithMethod(HttpMethod method) {
        this.requestMethod = method;

        requestDidStart();

        StringBuffer url = new StringBuffer(host);
        url.append(webAPI);

        // 判断执行的方法，如果是GET,DELETE将参数串起来
        if (method.getValue() == Method.GET || method.getValue() == Method.DELETE) {
            url.append("?");
            url.append(HttpUtils.jsonToURLParams(params, true));

            HttpUtils.printLog("GET URL:\n" + url.toString());
        } else {
            HttpUtils.printLog("POST URL:\n" + url.toString() + "\n");
            HttpUtils.printLog(params);
        }

        // 正常响应回来的监听
        Listener<JSONObject> listener = new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                HttpUtils.printLog("请求成功:\n");
                HttpUtils.printLog(response);
                requestDidSuccessful(requestMethod, response);
            }
        };

        // 请求失败的监听
        ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                HttpUtils.printLog("请求失败了:" + error.getMessage());
                requestDidError(requestMethod, 1, error.getMessage());
            }
        };

        // 创建json请求
        request = new JsonObjectRequest(method.getValue(), url.toString(), params, listener, errorListener);
        request.setTag(this.webAPI);
        queue.add(request);
    }

    /**
     * 发起GET请求
     */
    public void startGetRequest() {
        requestWithMethod(HttpMethod.GET);
    }

    /**
     * 发起POST请求
     */
    public void startPostRequest() {
        requestWithMethod(HttpMethod.POST);
    }

    /**
     * 发起DELETE请求
     */
    public void startDeleteRequest() {
        requestWithMethod(HttpMethod.DELETE);
    }

    /**
     * 发起PUT请求
     */
    public void startPutRequest() {
        requestWithMethod(HttpMethod.PUT);
    }

    /**
     * 发起PATCH请求 接口地址
     */
    public void startPatchRequest() {
        requestWithMethod(HttpMethod.PATCH);
    }

    /**
     * 取消请求
     */
    public void cancelRequest() {

        if (request != null) {
            request.cancel();
        }

    }

    public JsonObjectRequest getRequest() {
        return request;
    }

    public void setRequest(JsonObjectRequest request) {
        this.request = request;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getWebAPI() {
        return webAPI;
    }

    public void setWebAPI(String webAPI) {
        this.webAPI = webAPI;
    }
}
