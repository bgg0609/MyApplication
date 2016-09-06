package bgg.com.myapplication.common.listener;

import android.view.View;
import android.view.View.OnClickListener;

public abstract class OnceOnClickListener implements OnClickListener {

	private static long lastClickTime;

	/**
	 * 防止重复点击或多次点击
	 *
	 * @return
	 */
	public synchronized static boolean isFastClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	@Override
	public void onClick(View v) {

		if (isFastClick()) {
			return;
		}

		onClickOnce(v);

	}

	public abstract void onClickOnce(View v);

}
