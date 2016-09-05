package bgg.com.myapplication.util;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * 日志输出工具类
 */
@SuppressLint("NewApi")
public class Logger {
    private static LoggerLevel show_falg = LoggerLevel.VERBOSE;
    private static final String TAG = "Logger";

    public enum LoggerLevel {
        VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT
    }

    public static void D(String tag, String msg) {
        if (LoggerLevel.DEBUG.ordinal() >= show_falg.ordinal()) {
            Log.d(tag, msg);
        }
    }

    public static void I(String tag, String msg) {
        if (LoggerLevel.INFO.ordinal() >= show_falg.ordinal()) {
            Log.i(tag, msg);
        }
    }

    public static void W(String tag, String msg) {
        if (LoggerLevel.WARN.ordinal() >= show_falg.ordinal()) {
            Log.w(tag, msg);
        }
    }

    public static void E(String tag, String msg) {
        if (LoggerLevel.ERROR.ordinal() >= show_falg.ordinal()) {
            Log.e(tag, msg);
        }
    }

    public static void E(String tag, String msg, Throwable tr) {
        if (LoggerLevel.ERROR.ordinal() >= show_falg.ordinal()) {
            Log.e(tag, msg, tr);
        }
    }

}
