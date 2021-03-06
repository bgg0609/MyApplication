package bgg.com.myapplication.business.http;

/**
 * http请求方法,与com.android.volley.Request.Method对应
 */

public enum HttpMethod {
    DEPRECATED_GET_OR_POST(-1),
    GET(0),
    POST(1),
    PUT(2),
    DELETE(3),
    HEAD(4),
    OPTIONS(5),
    TRACE(6),
    PATCH(7);

    private final int value;

    HttpMethod(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
