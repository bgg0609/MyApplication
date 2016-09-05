package bgg.com.myapplication.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 输入相关工具
 */
public class InputManagerUtil {

    public static int lastContentHeight = 0;

    public static void toggleSoftInputArea(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示软键盘
     * @param context
     * @param view
     */
    public static void showSoftInput(Context context, View view) {
        if (view == null) {
            return;
        }
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏软键盘
     * @param context
     * @param view
     */
    public static void hideSoftInput(Context context, View view) {
        if (view == null) {
            return;
        }
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
    }

    /**
     * 判断软键盘是否显示
     * @param context
     * @return
     */
    public static boolean isSoftInputShow(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开

        return isOpen;
    }

    /**
     * 复制到剪贴板
     *
     * @param content
     * @return
     */
    public static boolean copyContent(Context context, String content) {
        try {
            ClipboardManager cmb = (ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setPrimaryClip(ClipData.newPlainText(null, content));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



}
