package bgg.com.myapplication.module.job.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bgg.com.myapplication.R;
import bgg.com.myapplication.common.customview.RoundedImageView;
import bgg.com.myapplication.module.job.model.entity.Job;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class JobListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;

    //正在加载中
    public static final int LOADING_MORE = 1;

    //没有更多数据
    public static final int NO_MORE_DATA = 2;


    //上拉加载更多状态-默认为0
    private int loadMoreStatus = 0;
    private LayoutInflater mInflater;
    private List<Job> jobs = null;

    private static final int TYPE_BLANK = 0;  //空数据界面
    private static final int TYPE_ITEM = 1;  //普通Item View
    private static final int TYPE_FOOTER = 2;  //顶部FootView


    public JobListAdapter(Context context, List<Job> mTitles) {
        this.mInflater = LayoutInflater.from(context);
        this.jobs = mTitles;
    }

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.loadMoreStatus = loadMoreStatus;
    }

    /**
     * item显示类型
     *
     * @param parent
     * @param viewType
     * @return
     */
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TYPE_BLANK) {

            View view = mInflater.inflate(R.layout.job_blank_item, parent, false);
            BlankViewHolder blankViewHolder = new BlankViewHolder(view);

            return blankViewHolder;

        } else if (viewType == TYPE_ITEM) {

            View view = mInflater.inflate(R.layout.job_list_item, parent, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);

            return itemViewHolder;

        } else if (viewType == TYPE_FOOTER) {

            View foot_view = mInflater.inflate(R.layout.recycler_load_more_layout, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            FootViewHolder footViewHolder = new FootViewHolder(foot_view);

            return footViewHolder;

        }
        return null;
    }

    /**
     * 数据的绑定显示
     *
     * @param holder
     * @param position
     */
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {

            Job job = jobs.get(position);

            ((ItemViewHolder) holder).tvName.setText(job.getName());

            ((ItemViewHolder) holder).ivAvatar.setImageResource(R.drawable.common_ic_default_avatar);
            ((ItemViewHolder) holder).ivAvatar.setOval(false);
            ((ItemViewHolder) holder).ivAvatar.loadImage(job.getAvatar());

            holder.itemView.setTag(position);

        } else if (holder instanceof FootViewHolder) {

            FootViewHolder footViewHolder = (FootViewHolder) holder;

            switch (loadMoreStatus) {
                case PULLUP_LOAD_MORE:
                    footViewHolder.foot_view_item_tv.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
                    break;
            }
        }
    }

    /**
     * 进行判断是普通Item视图还是FootView视图
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        if (jobs == null || jobs.size() == 0) {
            return TYPE_BLANK;
        } else if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (jobs == null || jobs.size() == 0)
            return 1;
        return jobs.size() + 1;
    }

    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     *
     * @param status
     */
    public void notifyDataSetChanged(int status) {
        loadMoreStatus = status;
        notifyDataSetChanged();
    }


    /**
     * 数据空界面
     */
    public static class BlankViewHolder extends RecyclerView.ViewHolder {
//        private TextView foot_view_item_tv;

        public BlankViewHolder(View view) {
            super(view);
//            foot_view_item_tv = (TextView) view.findViewById(R.id.tvLoadMore);
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public RoundedImageView ivAvatar;
        public TextView tvName;

        public ItemViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            ivAvatar = (RoundedImageView) view.findViewById(R.id.ivAvatar);
        }
    }

    /**
     * 底部FootView布局
     */
    public static class FootViewHolder extends RecyclerView.ViewHolder {
        private TextView foot_view_item_tv;

        public FootViewHolder(View view) {
            super(view);
            foot_view_item_tv = (TextView) view.findViewById(R.id.tvLoadMore);
        }
    }


}
