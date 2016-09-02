package bgg.com.myapplication.module.job.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bgg.com.myapplication.R;
import bgg.com.myapplication.common.customview.XListView;
import bgg.com.myapplication.common.fragment.BaseFragment;
import bgg.com.myapplication.module.job.adapter.JobListAdapter;
import bgg.com.myapplication.module.job.model.Job;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class JobFragment extends BaseFragment {
    private XListView listview;
    private JobListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.job_fragment, null);
        initView(contentView);
        return contentView;
    }

    private void initView(View contentView) {
        listview = (XListView) contentView.findViewById(R.id.lvJob);
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Job job = new Job();
            job.setAvatar("http://www.qqpk.cn/Article/UploadFiles/201409/20140910114015417_S.jpg");
            job.setName("职位" + i);
            jobs.add(job);
        }
        adapter = new JobListAdapter(getActivity(), jobs);
        listview.setAdapter(adapter);
    }
}
