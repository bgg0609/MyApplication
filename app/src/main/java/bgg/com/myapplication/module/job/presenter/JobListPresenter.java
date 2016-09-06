package bgg.com.myapplication.module.job.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bgg.com.myapplication.business.http.HttpMethod;
import bgg.com.myapplication.business.http.Response;
import bgg.com.myapplication.business.http.WebAPI;
import bgg.com.myapplication.common.persenter.BasePersenter;
import bgg.com.myapplication.module.job.model.JobModel;
import bgg.com.myapplication.module.job.model.entity.Job;
import bgg.com.myapplication.module.job.ui.adapter.RefreshFootAdapter;
import bgg.com.myapplication.module.job.ui.view.JobFragmentView;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class JobListPresenter extends BasePersenter{

    private List<Job> jobs = new ArrayList<Job>();
    private JobFragmentView jobFragmentView;

    public List<Job> getJobs() {
        return jobs;
    }

    public JobListPresenter(JobFragmentView jobFragmentView) {
        this.jobFragmentView = jobFragmentView;
    }

    public void refreshData() {
        jobFragmentView.showRefleshProgress();
        jobs.clear();
        loadMoreData();
    }

    public void loadMoreData() {
        jobFragmentView.showLoadMoreProgress();
        getJobList();
        //模拟数据加载完成后底部显示没有更多数据
        if (jobs.size() < 60) {
            jobFragmentView.updateItems(RefreshFootAdapter.PULLUP_LOAD_MORE);
        } else if (jobs.size() >= 60) {
            jobFragmentView.updateItems(RefreshFootAdapter.NO_MORE_DATA);
        }
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

        //使用http下载代码如下
        Map<String, Object> params = JobModel.buildGetJobListParams();
        startHttpRequest(HttpMethod.GET, WebAPI.GET_JOB_LIST_URL, params);
    }

    @Override
    public void requestDidSuccessful(Response response) {
        super.requestDidSuccessful(response);
        //返回成功，判断response
        if (response.matchAPI(HttpMethod.GET.getValue(),WebAPI.GET_JOB_LIST_URL )){
            //todo 处理数据逻辑
        }
    }

    @Override
    public void requestDidError(Response response) {
        super.requestDidError(response);
        //返回失败，判断response
        if (response.matchAPI(HttpMethod.GET.getValue(),WebAPI.GET_JOB_LIST_URL )){
            //todo 处理数据逻辑
        }
    }

    @Override
    public void requestDidStart() {
        super.requestDidStart();
    }
}
