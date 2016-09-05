package bgg.com.myapplication.module.job.presenter;

import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bgg.com.myapplication.module.job.model.entity.City;
import bgg.com.myapplication.module.job.model.entity.CityGroup;
import bgg.com.myapplication.module.job.ui.adapter.CharAdapter;
import bgg.com.myapplication.module.job.ui.view.CitySelectView;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class CitySelectPresenter {
    private static final int COUNT_MAX_SELECT_CITY = 3;

    private CitySelectView citySelectView;
    private List<CityGroup> cityGroups = new ArrayList<CityGroup>();
    private List<String> letters = new ArrayList<String>();
    private Map<String, ArrayList<CityGroup>> chars = new HashMap<String, ArrayList<CityGroup>>();
    private List<City> selectedCitys = new ArrayList<City>();

    public List<CityGroup> getCityGroups() {
        return cityGroups;
    }

    public CitySelectPresenter(CitySelectView citySelectView) {
        this.citySelectView = citySelectView;
    }

    public void loadData() {
        citySelectView.showProgress();
        //测试数据
        CityGroup cg = new CityGroup();
        cg.setName("热门城市");
        List<City> citys = new ArrayList<City>();
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
        cg.setLetter("R");
        cityGroups.add(cg);

        cg = new CityGroup();
        cg.setName("广东");
        citys = new ArrayList<City>();
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
        cg.setLetter("G");
        cityGroups.add(cg);

        cg = new CityGroup();
        cg.setName("广西");
        citys = new ArrayList<City>();
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
        cg.setLetter("G");
        cityGroups.add(cg);


        cg = new CityGroup();
        cg.setName("浙江");
        citys = new ArrayList<City>();
        city = new City();
        city.setName("杭州");
        citys.add(city);
        city = new City();
        city.setName("扬州");
        citys.add(city);
        cg.setCityList(citys);
        cg.setLetter("Z");
        cityGroups.add(cg);

        cg = new CityGroup();
        cg.setName("湖南");
        citys = new ArrayList<City>();
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
        cg.setLetter("H");
        cityGroups.add(cg);

        cg = new CityGroup();
        cg.setName("湖北");
        citys = new ArrayList<City>();
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
        cg.setLetter("H");
        cityGroups.add(cg);

        cg = new CityGroup();
        cg.setName("河南");
        citys = new ArrayList<City>();
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
        cg.setLetter("H");
        cityGroups.add(cg);

        cg = new CityGroup();
        cg.setName("河北");
        citys = new ArrayList<City>();
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
        cg.setLetter("H");
        cityGroups.add(cg);

        initData();

        citySelectView.hideProgress();
        citySelectView.updateItems();
    }

    /**
     * 处理字母检索等数据
     */
    private void initData() {
        for (int i = 0; i < cityGroups.size(); i++) {
            String str = cityGroups.get(i).getLetter();
            if (!letters.contains(str))
                letters.add(str);

            if (chars.containsKey(str)) {
                if (!chars.get(str).contains(cityGroups.get(i))) {
                    chars.get(str).add(cityGroups.get(i));
                }
            } else {
                ArrayList<CityGroup> firstNames = new ArrayList<CityGroup>();
                firstNames.add(cityGroups.get(i));
                chars.put(str, firstNames);
            }
        }
    }

    public void onItemSelect(City city) {

        if (selectedCitys.size() < COUNT_MAX_SELECT_CITY) {
            if (!selectedCitys.contains(city)) {
                selectedCitys.add(city);
                citySelectView.addItem(city);
            }
        }
    }

    public void deleteItem(City city) {
        selectedCitys.remove(city);
    }

    public void letterChanged(String s) {

        if (letters.contains(s)) {

            citySelectView.showLetter(s.toUpperCase());

            final int index = letters.indexOf(s);
            citySelectView.setSelectionByIndex(index);

            final ArrayList<CityGroup> data = chars.get(s);
            citySelectView.setCharData(data);

            citySelectView.showCharLayout();

        }
    }

    public void onCharItemSelect(CityGroup cg) {

        for (int i = 0; i < cityGroups.size(); i++) {
            if (cg.getName().equals(cityGroups.get(i))) {
                citySelectView.setSelectionByIndex(i);
            }
        }
    }
}
