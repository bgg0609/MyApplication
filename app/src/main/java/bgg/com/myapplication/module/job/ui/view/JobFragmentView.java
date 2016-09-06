package bgg.com.myapplication.module.job.ui.view;

import bgg.com.myapplication.common.persenter.BasePersenterView;

/**
 * 定义接口
 * Created by Administrator on 2016/9/5 0005.
 */
public interface JobFragmentView extends BasePersenterView{

    void showRefleshProgress();

    void showLoadMoreProgress();

    void hideRefleshProgress();

    void updateItems(int state);
}
