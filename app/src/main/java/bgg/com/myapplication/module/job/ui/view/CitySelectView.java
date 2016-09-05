package bgg.com.myapplication.module.job.ui.view;

import java.util.ArrayList;

import bgg.com.myapplication.module.job.model.entity.City;
import bgg.com.myapplication.module.job.model.entity.CityGroup;

/**
 * Created by Administrator on 2016/9/5 0002.
 */
public interface CitySelectView{

    void showProgress();

    void hideProgress();

    void showMessage(String message);

    void updateItems();

    void addItem(City city);

    void showLetter(String s);

    void setSelectionByIndex(int index);

    void setCharData(ArrayList<CityGroup> data);

    void showCharLayout();
}
