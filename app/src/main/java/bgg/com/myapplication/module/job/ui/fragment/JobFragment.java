package bgg.com.myapplication.module.job.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bgg.com.myapplication.R;
import bgg.com.myapplication.common.customview.NRecyclerView;
import bgg.com.myapplication.common.customview.NSwipeRefreshLayout;
import bgg.com.myapplication.common.fragment.BaseFragment;
import bgg.com.myapplication.module.job.presenter.JobListPresenter;
import bgg.com.myapplication.module.job.ui.adapter.RefreshFootAdapter;
import bgg.com.myapplication.module.job.ui.view.JobFragmentView;
import bgg.com.myapplication.util.ToastUtils;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class JobFragment extends BaseFragment implements JobFragmentView {

    private NRecyclerView nRecyclerView;
    private RefreshFootAdapter adapter;
    private NSwipeRefreshLayout swipeRefreshLayout;

    private int lastVisibleItem;
    private JobListPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.job_fragment, null);
        initView(view);

        presenter = new JobListPresenter(this);

        adapter = new RefreshFootAdapter(getActivity(), presenter.getJobs());
        adapter.setLoadMoreStatus(RefreshFootAdapter.LOADING_MORE);
        nRecyclerView.setAdapter(adapter);

        presenter.refreshData();

        return view;
    }

    private void initView(View view) {
        swipeRefreshLayout = (NSwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshData();
            }
        });

        nRecyclerView = (NRecyclerView) view.findViewById(R.id.lvJob);
        nRecyclerView.setOrientation(OrientationHelper.VERTICAL);
        nRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    presenter.loadMoreData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = nRecyclerView.findLastVisibleItemPosition();
            }
        });

    }


    @Override
    public void showRefleshProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showLoadMoreProgress() {
        adapter.notifyDataSetChanged(RefreshFootAdapter.LOADING_MORE);
    }

    @Override
    public void hideRefleshProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.showNewToast(getActivity(), message);
    }

    @Override
    public void updateItems(int state) {
        hideRefleshProgress();
        adapter.notifyDataSetChanged(state);
    }

}
