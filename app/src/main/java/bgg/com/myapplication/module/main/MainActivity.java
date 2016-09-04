package bgg.com.myapplication.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bgg.com.myapplication.R;
import bgg.com.myapplication.common.OnceOnClickListener;
import bgg.com.myapplication.common.activity.BaseActivity;
import bgg.com.myapplication.common.customview.BottomDialog;
import bgg.com.myapplication.common.customview.CommonHeaderBarView;
import bgg.com.myapplication.module.job.activity.CitySelectActivity;
import bgg.com.myapplication.module.job.fragment.JobFragment;
import bgg.com.myapplication.module.mine.fragment.MineFragment;
import bgg.com.myapplication.module.msg.fragment.MsgFragment;
import bgg.com.myapplication.util.ToastUtils;

public class MainActivity extends BaseActivity implements OnClickListener, OnPageChangeListener {
    public static final String TAG = "MainActivity";

    public final static int PAGE_JOB = 0;
    public final static int PAGE_MSG = 1;
    public final static int PAGE_MINE = 2;

    private ViewPager mViewPager;

    private ArrayList<Fragment> mFragments;
    private ArrayList<TextView> mTabViews;

    private int mCurrentIndex = 0; // 记录当前页索引


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {

        setContentView(R.layout.main_activity);

        swithTitleBar(PAGE_JOB);

        getHeaderBarView().setRightText("筛选");

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        // 初始化底部Tab
        mTabViews = new ArrayList<>();

        TextView tvCma = (TextView) findViewById(R.id.tvJob);
        TextView tvPro = (TextView) findViewById(R.id.tvMsg);
        TextView tvMine = (TextView) findViewById(R.id.tvMine);

        // 一开始默认选择第一页
        tvCma.setSelected(true);
        tvCma.setOnClickListener(this);
        tvPro.setOnClickListener(this);
        tvMine.setOnClickListener(this);

        mTabViews.add(tvCma);
        mTabViews.add(tvPro);
        mTabViews.add(tvMine);

        // 初始化Fragment
        mFragments = new ArrayList<>();

        mFragments.add(new JobFragment());
        mFragments.add(new MsgFragment());
        mFragments.add(new MineFragment());

        mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), mFragments));
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onClickNavBarRightText() {
        super.onClickNavBarRightText();
        BottomDialog bottomDialog = new BottomDialog(getActivity());
        bottomDialog.setTitle("工资水平");
        final List<String> options = new ArrayList<>();
        options.add("不限");
        options.add("1000~2000");
        options.add("2000~3000");
        options.add("3000~4000");
        options.add("4000~5000");
        options.add("5000~6000");
        options.add("7000~8000");
        options.add("8000~9000");
        options.add("10000~12000");
        options.add("12000~15000");
        options.add("15000以上");

        bottomDialog.setOptions(options, new BottomDialog.CallBack() {
            @Override
            public void onItemSelect(int position) {
                ToastUtils.showNewToast(getActivity(),options.get(position) );
            }
        });
        bottomDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void handleSwith(int index) {
        // 如果是当前页，那么不需要做任何操作
        if (index == mCurrentIndex)
            return;

        mCurrentIndex = index;
        mViewPager.setCurrentItem(mCurrentIndex, false);

        swithTabState(index);
        swithTitleBar(index);
    }

    // 改变底部tab的状态
    private void swithTabState(int index) {
        for (int i = 0; i < mTabViews.size(); i++) {
            if (i == index) {
                mTabViews.get(i).setSelected(true);
            } else {
                mTabViews.get(i).setSelected(false);
            }
        }
    }


    // 改变标题以及操作
    private void swithTitleBar(int index) {
        switch (index) {
            case PAGE_JOB: {
                setTitle(R.string.bottom_tab_job);
                CommonHeaderBarView bar = getHeaderBarView();
                bar.setLeftIcon(R.drawable.icon_location);
                bar.setLeftText("广州");
                bar.setLeftOnClick(new OnceOnClickListener() {
                    @Override
                    public void onClickOnce(View v) {
                        startActivity(new Intent(getActivity(), CitySelectActivity.class));
                    }
                });
                bar.showLeftArea(true);
                break;
            }
            case PAGE_MSG: {
                setTitle(R.string.bottom_tab_msg);
                CommonHeaderBarView bar = getHeaderBarView();
                bar.showLeftArea(false);
                break;
            }

            case PAGE_MINE: {
                setTitle(R.string.bottom_tab_mine);
                CommonHeaderBarView bar = getHeaderBarView();
                bar.showLeftArea(false);
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvJob:
                handleSwith(PAGE_JOB);
                break;
            case R.id.tvMsg:
                handleSwith(PAGE_MSG);
                break;
            case R.id.tvMine:
                handleSwith(PAGE_MINE);
                break;
        }
    }

    @Override
    public void onPageSelected(int arg0) {
        handleSwith(arg0);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
    }

}
