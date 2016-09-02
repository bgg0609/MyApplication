package bgg.com.myapplication.module.job.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bgg.com.myapplication.R;
import bgg.com.myapplication.common.customview.RoundedImageView;
import bgg.com.myapplication.module.job.model.Job;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class JobListAdapter extends BaseAdapter {
    private Context context;
    private List<Job> jobs;

    public JobListAdapter(Context context, List<Job> jobs) {
        this.context = context;
        this.jobs = jobs;
    }

    @Override
    public int getCount() {
        if (jobs == null) return 0;
        return jobs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {

            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.job_list_item, null);
            holder.ivAvatar = (RoundedImageView) view.findViewById(R.id.ivAvatar);
            holder.tvName = (TextView) view.findViewById(R.id.tvName);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        Job job = jobs.get(i);

        holder.ivAvatar.setImageResource(R.drawable.common_ic_default_avatar);
        holder.ivAvatar.setOval(false);
        holder.ivAvatar.loadImage(job.getAvatar());

        holder.tvName.setText(job.getName());

        return view;
    }

    class ViewHolder {
        RoundedImageView ivAvatar;
        TextView tvName;
    }
}
