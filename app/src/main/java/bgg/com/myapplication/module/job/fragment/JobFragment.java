package bgg.com.myapplication.module.job.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bgg.com.myapplication.R;
import bgg.com.myapplication.common.customview.NRecyclerView;
import bgg.com.myapplication.common.customview.NSwipeRefreshLayout;
import bgg.com.myapplication.common.fragment.BaseFragment;
import bgg.com.myapplication.module.job.adapter.RefreshFootAdapter;
import bgg.com.myapplication.module.job.model.Job;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class JobFragment extends BaseFragment {

    private NRecyclerView nRecyclerView;
    private RefreshFootAdapter adapter;
    private NSwipeRefreshLayout swipeRefreshLayout;
    private List<Job> jobs = new ArrayList<>();
    private int lastVisibleItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.job_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {

        swipeRefreshLayout = (NSwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                jobs.clear();
                getJobList();
                handler.sendEmptyMessage(1);
            }
        });

        nRecyclerView = (NRecyclerView) view.findViewById(R.id.lvJob);
        nRecyclerView.setOrientation(OrientationHelper.VERTICAL);
        nRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {

                    handler.sendEmptyMessage(0);
                    getJobList();
                    handler.sendEmptyMessage(1);

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = nRecyclerView.findLastVisibleItemPosition();
            }
        });

        adapter = new RefreshFootAdapter(getActivity(), jobs);
        nRecyclerView.setAdapter(adapter);

        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getJobList();
                handler.sendEmptyMessage(1);
            }
        }, 3000);
    }

    //获取数据
    private void getJobList() {
        //测试数据
        for (int i = 0; i < 20; i++) {
            Job job = new Job();
            job.setAvatar("http://www.qqpk.cn/Article/UploadFiles/201409/20140910114015417_S.jpg");
            job.setName("职位" + i);
            jobs.add(job);
        }
    }

    private MyHandler handler = new MyHandler();

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    adapter.notifyDataSetChanged(RefreshFootAdapter.LOADING_MORE);
                    break;
                case 1:
                    swipeRefreshLayout.setRefreshing(false);
                    adapter.notifyDataSetChanged(RefreshFootAdapter.PULLUP_LOAD_MORE);
                    break;
                default:
                    break;
            }
        }
    }

}
