package bgg.com.myapplication.common.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import bgg.com.myapplication.R;

/**
 * The footer view
 * 
 */
public class XFooterView extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;
	public final static int STATE_END = 3;
	public final static int STATE_FIRST = 4;

	protected View mLayout;

	protected int mState = STATE_NORMAL;

	private View mProgressBar;

	private TextView mHintView;

	public XFooterView(Context context) {
		super(context);
		initView(context);
	}

	public XFooterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	protected void initView(Context context) {
		mLayout = LayoutInflater.from(context)
				.inflate(R.layout.common_vw_footer, null);
		mLayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		addView(mLayout);

		mProgressBar = mLayout.findViewById(R.id.footer_progressbar);
		mHintView = (TextView) mLayout.findViewById(R.id.footer_hint_text);
	}

	protected void showNormalState() {
		mHintView.setText("Normal");
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
	}

	protected void showReadyState() {
		mHintView.setText("Ready");
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
	}

	protected void showLoadingState() {
		mProgressBar.setVisibility(View.VISIBLE);	
		mHintView.setText("Loading");
		mHintView.setVisibility(View.VISIBLE);
	}

	protected void showEndState() {
		mHintView.setText("End");
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
	}

	protected void showFirstState() {
		mHintView.setText("First");
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
	}

	/**
	 * Set footer view state
	 * 
	 * @see #STATE_LOADING
	 * @see #STATE_NORMAL
	 * @see #STATE_READY
	 * 
	 * @param state
	 */
	public void setState(int state) {
		if (state == mState)
			return;

		switch (state) {
		case STATE_FIRST:
			showFirstState();
			break;
		case STATE_NORMAL:
			showNormalState();
			break;

		case STATE_READY:
			showReadyState();
			break;

		case STATE_LOADING:
			showLoadingState();
			break;
		case STATE_END:
			showEndState();
			break;
		}

		mState = state;
	}

	/**
	 * Set footer view bottom margin.
	 * 
	 * @param margin
	 */
	public void setBottomMargin(int margin) {
		if (margin < 0)
			return;
		LayoutParams lp = (LayoutParams) mLayout
				.getLayoutParams();
		lp.bottomMargin = margin;
		mLayout.setLayoutParams(lp);
	}
 
	/**
	 * Get footer view bottom margin.
	 * 
	 * @return
	 */
	public int getBottomMargin() {
		LayoutParams lp = (LayoutParams) mLayout
				.getLayoutParams();
		return lp.bottomMargin;
	}

	/**
	 * normal status
	 */
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}

	/**
	 * loading status
	 */
	public void loading() {
		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	/**
	 * hide footer when disable pull load more
	 */
	public void hide() {
		LayoutParams lp = (LayoutParams) mLayout
				.getLayoutParams();
		lp.height = 0;
		mLayout.setLayoutParams(lp);
	}

	/**
	 * show footer
	 */
	public void show() {
		LayoutParams lp = (LayoutParams) mLayout
				.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mLayout.setLayoutParams(lp);
	}

}
