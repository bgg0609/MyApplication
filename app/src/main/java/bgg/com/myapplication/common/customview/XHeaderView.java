package bgg.com.myapplication.common.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import bgg.com.myapplication.R;

/**
 * The header view for {@link bgg.com.myapplication.common.customview.XListView}
 *
 * @author markmjw
 * @date 2013-10-08
 */
public class XHeaderView extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;

	protected LinearLayout mContainer;

	protected int mState = STATE_NORMAL;

	private final int ROTATE_ANIM_DURATION = 180;
	private ImageView mArrowImageView;
	private ProgressBar mProgressBar;
	private TextView mHintTextView;
	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;

	private boolean mIsFirst;

	public XHeaderView(Context context) {
		super(context);
		initView(context);
	}

	public XHeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	protected void initView(Context context) {
		// Initial set header view height 0
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.common_vw_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);

		mArrowImageView = (ImageView) findViewById(R.id.header_arrow);
		mHintTextView = (TextView) findViewById(R.id.header_hint_text);
		mProgressBar = (ProgressBar) findViewById(R.id.header_progressbar);

		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);

		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}

	protected void showNormalState() {

		mArrowImageView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);

		if (mState == STATE_READY) {
			mArrowImageView.startAnimation(mRotateDownAnim);
		}

		if (mState == STATE_REFRESHING) {
			mArrowImageView.clearAnimation();
		}

		mHintTextView.setText(R.string.xlistview_header_hint_normal);
	}

	protected void showReadyState() {
		mArrowImageView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);

		if (mState != STATE_READY) {
			mArrowImageView.clearAnimation();
			mArrowImageView.startAnimation(mRotateUpAnim);
			mHintTextView.setText(R.string.xlistview_header_hint_ready);
		}
	}

	protected void showLoadingState() {
		mArrowImageView.clearAnimation();
		mArrowImageView.setVisibility(View.INVISIBLE);
		mProgressBar.setVisibility(View.VISIBLE);
		mHintTextView.setText(R.string.xlistview_header_hint_loading);

	}

	public void setState(int state) {
		if (state == mState && mIsFirst) {
			mIsFirst = true;
			return;
		}

		switch (state) {
			case STATE_NORMAL:

				Log.e("state", "STATE_NORMAL");
				showNormalState();
				break;
			case STATE_READY:
				Log.e("state", "STATE_READY");
				showReadyState();
				break;
			case STATE_REFRESHING:
				Log.e("state", "STATE_REFRESHING");
				showLoadingState();
				break;
			default:
				break;
		}

		mState = state;
	}

	public View getHeaderContainer() {
		return findViewById(R.id.header_content);
	}

	/**
	 * Set the header view visible height.
	 * @param height
	 */
	public void setVisibleHeight(int height) {
		if (height < 0)
			height = 0;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer
				.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	/**
	 * Get the header view visible height.
	 *
	 * @return
	 */
	public int getVisibleHeight() {
		return mContainer.getHeight();
	}

}
