package bgg.com.myapplication.module.mine.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bgg.com.myapplication.R;
import bgg.com.myapplication.common.fragment.BaseFragment;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class MineFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment, null);
        return view;
    }
}
