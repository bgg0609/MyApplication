package bgg.com.myapplication.common.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import bgg.com.myapplication.module.job.adapter.DividerItemDecoration;

/**
 * Created by dell on 2016/9/3.
 */
public class NRecyclerView extends RecyclerView {
    private LinearLayoutManager linearLayoutManager;

    public NRecyclerView(Context context) {
        super(context);
        linearLayoutManager = new LinearLayoutManager(getContext());
        initStyle();
    }

    public NRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        linearLayoutManager = new LinearLayoutManager(getContext());
        initStyle();
    }

    public NRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        linearLayoutManager = new LinearLayoutManager(getContext());
        initStyle();
    }

    private void initStyle() {
        //添加分隔线
        addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL_LIST));
    }

    public void setOrientation(int orientation) {

        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        setLayoutManager(linearLayoutManager);
    }

    public int findLastVisibleItemPosition() {
        return linearLayoutManager.findLastVisibleItemPosition();
    }
}
