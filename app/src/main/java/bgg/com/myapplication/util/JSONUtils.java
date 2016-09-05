package bgg.com.myapplication.util;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class JSONUtils {


	public static <T> T jsonToObject(String json, Class<T> classOfT){
		return JSON.parseObject(json,classOfT);
	}

	public static <T> List<T> jsonParseArray(String json, Class<T> classOfT){
		return JSON.parseArray(json, classOfT);
	}

	public static String jsonToString(Object object){
		return JSON.toJSONString(object);
	}

}
