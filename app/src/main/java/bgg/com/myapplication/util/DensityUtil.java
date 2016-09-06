package bgg.com.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;

public class DensityUtil {

	/**
	 * dp转px
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px转dp
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	
	/** 
	 * 获取状态栏高度
	 * @param activity 
	 * @return > 0 success; <= 0 fail 
	 * 
	 */  
	public static int getStatusHeight(Activity activity){  
	    int statusHeight = 0;  
	    Rect localRect = new Rect();  
	    activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);  
	    statusHeight = localRect.top;  
	    if (0 == statusHeight){  
	        Class<?> localClass;  
	        try {  
	            localClass = Class.forName("com.android.internal.R$dimen");  
	            Object localObject = localClass.newInstance();  
	            int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());  
	            statusHeight = activity.getResources().getDimensionPixelSize(i5);  
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IllegalAccessException e) {  
	            e.printStackTrace();  
	        } catch (InstantiationException e) {  
	            e.printStackTrace();  
	        } catch (NumberFormatException e) {  
	            e.printStackTrace();  
	        } catch (IllegalArgumentException e) {  
	            e.printStackTrace();  
	        } catch (SecurityException e) {  
	            e.printStackTrace();  
	        } catch (NoSuchFieldException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    return statusHeight;  
	}

	/**
	 * 得到设备屏幕的宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 得到设备屏幕的高度
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 得到设备的密度
	 */
	public static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}
}