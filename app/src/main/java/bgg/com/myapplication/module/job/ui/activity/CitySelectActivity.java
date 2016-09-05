package bgg.com.myapplication.module.job.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bgg.com.myapplication.R;
import bgg.com.myapplication.common.OnceOnClickListener;
import bgg.com.myapplication.common.activity.BaseActivity;
import bgg.com.myapplication.common.customview.FlowLayout;
import bgg.com.myapplication.module.job.presenter.CitySelectPresenter;
import bgg.com.myapplication.module.job.ui.adapter.CharAdapter;
import bgg.com.myapplication.module.job.ui.adapter.CityListAdapter;
import bgg.com.myapplication.module.job.ui.custom.LetterListView;
import bgg.com.myapplication.module.job.model.entity.City;
import bgg.com.myapplication.module.job.model.entity.CityGroup;
import bgg.com.myapplication.module.job.ui.custom.CityGroupLayout;
import bgg.com.myapplication.module.job.ui.view.CitySelectView;
import bgg.com.myapplication.util.ToastUtils;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class CitySelectActivity extends BaseActivity implements CitySelectView {

    private FlowLayout flSelectCity;
    private ListView lvCity;
    private CityListAdapter adapter;
    private LetterListView letterListView;
    private LinearLayout char_layout;
    private ListView char_list;
    private TextView char_text;


    private CitySelectPresenter presenter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initView();

        presenter = new CitySelectPresenter(this);
        adapter = new CityListAdapter(getActivity(), presenter.getCityGroups());
        adapter.setOnItemSelectListener(new CityGroupLayout.OnItemSelectListener() {
            @Override
            public void cityItemSelect(City city) {
                presenter.onItemSelect(city);

            }
        });
        lvCity.setAdapter(adapter);

        presenter.loadData();
    }


    private void initView() {
        setContentView(R.layout.job_activity_city_select);
        setTitle("城市选择");
        flSelectCity = (FlowLayout) findViewById(R.id.flSelectCity);
        lvCity = (ListView) findViewById(R.id.lvCity);
        letterListView = (LetterListView) findViewById(R.id.letterListView);
        char_layout = (LinearLayout) findViewById(R.id.char_layout);
        char_list = (ListView) findViewById(R.id.char_list);
        char_text = (TextView) findViewById(R.id.char_text);

        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        lvCity.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (char_layout.isShown()) {
                    char_layout.setVisibility(View.GONE);
                }
                return false;
            }
        });

    }

    protected void addItemView(final City city) {
        final View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.job_city_selected_item, null);
        TextView mTextView = (TextView) itemView.findViewById(R.id.tvName);
        mTextView.setText(city.getName());
        ImageButton ibDelete = (ImageButton) itemView.findViewById(R.id.ibDelete);
        ibDelete.setOnClickListener(new OnceOnClickListener() {
            @Override
            public void onClickOnce(View v) {
                flSelectCity.removeView(itemView);
                presenter.deleteItem(city);
            }
        });
        flSelectCity.addView(itemView);
    }

    @Override
    public void showProgress() {
        showLoading();
    }

    @Override
    public void hideProgress() {
        closeLoading();
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.showNewToast(getActivity(), message);
    }

    @Override
    public void updateItems() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addItem(City city) {
        addItemView(city);
    }

    @Override
    public void showLetter(String s) {
        char_text.setText(s.toUpperCase());
    }

    @Override
    public void setSelectionByIndex(int index) {
        lvCity.setSelection(index);
    }

    @Override
    public void setCharData(final ArrayList<CityGroup> data) {
        char_list.setAdapter(new CharAdapter(getActivity(), data));
        char_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onCharItemSelect(data.get(position));
                char_layout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showCharLayout() {
        if (!char_layout.isShown()) {
            char_layout.setVisibility(View.VISIBLE);
        }
    }

    private class LetterListViewListener implements LetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            presenter.letterChanged(s);
        }
    }
}
