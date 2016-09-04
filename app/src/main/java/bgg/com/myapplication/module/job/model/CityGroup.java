package bgg.com.myapplication.module.job.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class CityGroup implements Serializable {

    private String letter;
    private String name;
    private List<City> cityList;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
}
