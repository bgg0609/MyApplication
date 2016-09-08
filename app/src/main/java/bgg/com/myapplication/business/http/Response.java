package bgg.com.myapplication.business.http;

import org.json.JSONObject;

import java.util.Map;

/**
 * Http 响应回来的主结构
 */
public class Response {
	public static final int SUCCESS_CODE = 200;
	private int code;
	private Map data;
	private String msg;
	private String webAPI;
	private HttpMethod method;
	private Object extObject;
	private JSONObject result;

	public JSONObject getResult() {
		this.result= new JSONObject(data);
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}

	public Object getExtObject() {
		return extObject;
	}

	public void setExtObject(Object extObject) {
		this.extObject = extObject;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getWebAPI() {
		return webAPI;
	}

	public void setWebAPI(String webAPI) {
		this.webAPI = webAPI;
	}

	public boolean matchAPI(HttpMethod httpMethod, String api) {

		if (webAPI.equalsIgnoreCase(api) && method == httpMethod) {
			return true;
		}

		return false;
	}

	public boolean isOk() {
		return code == SUCCESS_CODE;
	}

}
