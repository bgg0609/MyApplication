package bgg.com.myapplication.common.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.support.v4.app.Fragment;
import android.util.Log;

import bgg.com.myapplication.R;

/**
 * Fragment基类
 * 
 */
public class BaseFragment extends Fragment {

	private ProgressDialog mProgressDialog;

	/**
	 * <p>
	 * loading对话框是否可被取消。默认是可以取消。
	 * </p>
	 * 在调用showDialog()前，调用setLoadingCancelable()才会生效
	 */
	protected boolean mCancelable = true;

	/**
	 * 设置Loading对话框是否可被取消
	 * 
	 * @param isCancelable
	 *            是否可被取消
	 */
	protected void setLoadingCancelable(boolean isCancelable) {
		mCancelable = isCancelable;
	}

	protected void showLoading() {
		if(getActivity() == null){
			Log.e("BaseFragment", "Activity is null!");
			return;
		}
			
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(getActivity());
			mProgressDialog.setCanceledOnTouchOutside(false);
			mProgressDialog.setCancelable(mCancelable);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setIndeterminate(false);

			if (mCancelable) {
				mProgressDialog.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						getActivity().onBackPressed();
					}
				});
			}
			mProgressDialog.show();
			// 必须在show()函数调用后setContentView！
			mProgressDialog.setContentView(R.layout.common_loading_dialog);
		}
	}

	protected void closeLoading() {
		if (getActivity() != null && mProgressDialog != null) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}
	
	@Override
	public void startActivity(Intent intent) {
		getActivity().startActivity(intent);
	}
	
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		getActivity().startActivityForResult(intent, requestCode);
	}
	
	
	public Context getContext(){
		return getActivity();
	}
}
