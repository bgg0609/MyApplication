package bgg.com.myapplication.common.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import bgg.com.myapplication.R;
import bgg.com.myapplication.common.listener.OnceOnClickListener;
import bgg.com.myapplication.common.customview.CommonHeaderBarView;

/**
 * 所有Activity基类
 */
@SuppressLint("NewApi")
public class BaseActivity extends FragmentActivity {

    private ProgressBar loading;

    protected static final String TAG = "BaseActivity";

    private CommonHeaderBarView headerBarView;
    // 网络请求失败的View
    private View ReflashLayout;
    // 网络请求失败后，点击刷新的按钮
    private Button ReflashLayoutBtn;
    private LinearLayout contentLayout;

    public CommonHeaderBarView getHeaderBarView() {
        return headerBarView;
    }


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initContentView();
    }


    private void initContentView() {
        ViewGroup content = (ViewGroup) findViewById(android.R.id.content);
        content.removeAllViews();
        contentLayout = new LinearLayout(this);
        content.addView(contentLayout);

        LayoutInflater.from(this).inflate(R.layout.common_activity_base, contentLayout, true);

        headerBarView = (CommonHeaderBarView) findViewById(R.id.headerView);
        headerBarView.setLeftOnClick(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        headerBarView.setRightOnClick(onRightIconClick1);
        headerBarView.setRightTextOnClick(onRightTextClick);
        headerBarView.setRightOnClick2(onRightIconClick2);
    }

    OnClickListener onRightIconClick1 = new OnceOnClickListener() {
        @Override
        public void onClickOnce(View v) {
            onClickNavBarRightIcon1();
        }
    };

    OnClickListener onRightTextClick = new OnceOnClickListener() {
        @Override
        public void onClickOnce(View v) {
            onClickNavBarRightText();
        }
    };

    OnClickListener onRightIconClick2 = new OnceOnClickListener() {
        @Override
        public void onClickOnce(View v) {
            onClickNavBarRightIcon2();
        }
    };


    @Override
    public void setContentView(int layoutResID) {
        View contentView = LayoutInflater.from(this).inflate(layoutResID, contentLayout, false);
        LinearLayout ll = (LinearLayout) findViewById(R.id.layout_content);
        ll.addView(contentView);

        ReflashLayout = findViewById(R.id.ReflashLayout);
        ReflashLayoutBtn = (Button) findViewById(R.id.ReflashLayoutBtn);

        loading = (ProgressBar) findViewById(R.id.loading);
    }

    @Override
    public void setContentView(View customContentView) {
        contentLayout.addView(customContentView);
    }


    /**
     * 点击刷新的按钮Listener
     *
     * @param
     */
    public void setReflashLayoutBtnOnclickListener(OnClickListener Listener) {
        ReflashLayoutBtn.setOnClickListener(Listener);
    }

    /**
     * 显示请求失败的View
     *
     * @param
     */
    protected void showReflashLayout(boolean IsShow) {
        ReflashLayout.setVisibility(IsShow ? View.VISIBLE : View.GONE);
    }


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        headerBarView.setMiddleText(title);
    }


    /**
     * 是否显示加载圈。true为显示，false为不显示
     *
     * @param isShow
     */
    public void showLoadingBar(boolean isShow) {
        if (loading == null)
            return;
        loading.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * 是否显示标题栏。true为显示，false为不显示
     *
     * @param isShow
     */
    public void showHeaderBar(boolean isShow) {
        if (headerBarView == null)
            return;
        headerBarView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }


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
     * @param isCancelable 是否可被取消
     */
    protected void setLoadingCancelable(boolean isCancelable) {
        mCancelable = isCancelable;
    }

    /**
     * 显示加载提示框
     */
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    /**
     * 显示加载提示框
     * param tip 提示语
     */
    public void showLoading(String tip) {
        loading.setContentDescription(tip);
        loading.setVisibility(View.VISIBLE);
    }

    /**
     * 关闭加载提示框
     */
    public void closeLoading() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void startActivity(Intent intent, int animIn, int animOut) {
        super.startActivity(intent);
        overridePendingTransition(animIn, animOut);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置导航栏右侧第一个图标点击事件
     */
    public void onClickNavBarRightIcon1() {

    }

    /**
     * 设置导航栏右侧文字点击事件
     */
    public void onClickNavBarRightText() {

    }

    /**
     * 设置导航栏右侧第二个图标点击事件
     */
    public void onClickNavBarRightIcon2() {

    }

}
