package bgg.com.myapplication.module.job.ui.view;

/**
 * 定义接口
 * Created by Administrator on 2016/9/5 0005.
 */
public interface JobFragmentView {

    void showRefleshProgress();

    void showLoadMoreProgress();

    void hideRefleshProgress();

    void showMessage(String message);

    void updateItems(int state);
}
