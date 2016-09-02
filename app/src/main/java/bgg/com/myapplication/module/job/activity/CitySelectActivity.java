package bgg.com.myapplication.module.job.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bgg.com.myapplication.R;
import bgg.com.myapplication.common.OnceOnClickListener;
import bgg.com.myapplication.common.activity.BaseActivity;
import bgg.com.myapplication.common.customview.FlowLayout;
import bgg.com.myapplication.module.job.adapter.CityListAdapter;
import bgg.com.myapplication.module.job.model.City;
import bgg.com.myapplication.module.job.model.CityGroup;
import bgg.com.myapplication.module.job.view.CityGroupView;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class CitySelectActivity extends BaseActivity {
    private static final int COUNT_MAX_SELECT_CITY = 3;
    private FlowLayout flSelectCity;
    private List<City> selectedCitys = new ArrayList<>();
    private List<CityGroup> cityGroups = new ArrayList<>();
    private ListView lvCity;
    private CityListAdapter adapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.job_activity_city_select);
        setTitle("城市选择");
        flSelectCity = (FlowLayout) findViewById(R.id.flSelectCity);
        lvCity = (ListView) findViewById(R.id.lvCity);

        updateSelectCityLayout();

        //测试数据
        CityGroup cg = new CityGroup();
        cg.setName("热门城市");
        List<City> citys = new ArrayList<>();
        City city = new City();
        city.setName("北京");
        citys.add(city);
        city = new City();
        city.setName("上海");
        citys.add(city);
        city = new City();
        city.setName("深圳");
        citys.add(city);
        city = new City();
        city.setName("广州");
        citys.add(city);
        city = new City();
        city.setName("天津");
        citys.add(city);
        city = new City();
        city.setName("杭州");
        citys.add(city);
        city = new City();
        city.setName("南京");
        citys.add(city);
        cg.setCityList(citys);
        cityGroups.add(cg);

        cg = new CityGroup();
        cg.setName("广东");
        citys = new ArrayList<>();
        city = new City();
        city.setName("广州");
        citys.add(city);
        city = new City();
        city.setName("深圳");
        citys.add(city);
        city = new City();
        city.setName("佛山");
        citys.add(city);
        city = new City();
        city.setName("珠海");
        citys.add(city);
        city = new City();
        city.setName("东莞");
        citys.add(city);
        city = new City();
        city.setName("中山");
        citys.add(city);
        cg.setCityList(citys);
        cityGroups.add(cg);


        cg = new CityGroup();
        cg.setName("浙江");
        citys = new ArrayList<>();
        city = new City();
        city.setName("杭州");
        citys.add(city);
        city = new City();
        city.setName("扬州");
        citys.add(city);
        cg.setCityList(citys);
        cityGroups.add(cg);

        cg = new CityGroup();
        cg.setName("湖南");
        citys = new ArrayList<>();
        city = new City();
        city.setName("长沙");
        citys.add(city);
        city = new City();
        city.setName("岳阳");
        citys.add(city);
        city = new City();
        city.setName("株洲");
        citys.add(city);
        city = new City();
        city.setName("衡阳");
        citys.add(city);
        city = new City();
        city.setName("郴州");
        citys.add(city);
        city = new City();
        city.setName("永州");
        citys.add(city);
        cg.setCityList(citys);
        cityGroups.add(cg);

        adapter = new CityListAdapter(getActivity(), cityGroups);
        adapter.setOnItemSelectListener(new CityGroupView.OnItemSelectListener() {
            @Override
            public void cityItemSelect(City city) {
                if (selectedCitys.size() < COUNT_MAX_SELECT_CITY) {
                    if (!selectedCitys.contains(city)) {
                        selectedCitys.add(city);
                        updateSelectCityLayout();
                    }
                }
            }
        });
        lvCity.setAdapter(adapter);
    }

    protected void updateSelectCityLayout() {
        if (selectedCitys != null) {
            flSelectCity.removeAllViews();
            for (final City tag : selectedCitys) {

                View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.job_city_selected_item, null);

                TextView mTextView = (TextView) itemView.findViewById(R.id.tvName);
                mTextView.setText(tag.getName());

                ImageButton ibDelete = (ImageButton) itemView.findViewById(R.id.ibDelete);
                ibDelete.setOnClickListener(new OnceOnClickListener() {
                    @Override
                    public void onClickOnce(View v) {
                        selectedCitys.remove(tag);
                        updateSelectCityLayout();
                    }
                });

                flSelectCity.addView(itemView);
            }
        }
    }
}
