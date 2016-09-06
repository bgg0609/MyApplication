package bgg.com.myapplication.common.persenter;

import org.json.JSONException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import bgg.com.myapplication.MyApplication;
import bgg.com.myapplication.business.http.ABOHttpRequest;
import bgg.com.myapplication.business.http.HttpMethod;
import bgg.com.myapplication.business.http.Response;

/**
 * 基础业务类，实现http请求
 * Created by dell on 2016/9/6.
 */
public class BasePersenter implements ABOHttpRequest.HttpEventListener {

    private Set<ABOHttpRequest> requestQueue = new HashSet<ABOHttpRequest>();

    @Override
    public void requestDidStart() {

    }

    @Override
    public void requestDidSuccessful(Response response) {
        cancelHttpRequest(response.getWebAPI());
    }

    @Override
    public void requestDidError(Response response) {
    }

    public void onDestory(){
        cancelHttpRequest();
    }
    /**
     * Http请求以及响应相关
     */

    public void startHttpRequest(HttpMethod method, String webAPI, Map<String, Object> params) {

        MyApplication app = MyApplication.getInstance();

        ABOHttpRequest httpRequest = new ABOHttpRequest(app, webAPI);
        httpRequest.setHttpEventListener(this);

        if (params != null) {
            Iterator<String> keys = params.keySet().iterator();

            while (keys.hasNext()) {
                String key = keys.next();

                try {
                    httpRequest.getParams().put(key, params.get(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        requestQueue.add(httpRequest);
        httpRequest.requestWithMethod(method.getValue());
    }

    /**
     * 取消某个请求
     *
     * @param webAPI
     */
    public void cancelHttpRequest(String webAPI) {
        Iterator<ABOHttpRequest> iter = requestQueue.iterator();

        while (iter.hasNext()) {
            ABOHttpRequest request = iter.next();

            if (request.getWebAPI().equals(webAPI)) {
                request.cancelRequest();
                requestQueue.remove(request);
                break;
            }
        }

    }
    public void cancelHttpRequest() {
        Iterator<ABOHttpRequest> iter = requestQueue.iterator();

        while (iter.hasNext()) {
            ABOHttpRequest request = iter.next();
            request.cancelRequest();
        }

    }
}
