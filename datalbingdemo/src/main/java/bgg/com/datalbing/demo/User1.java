package bgg.com.datalbing.demo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;


/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class User1 extends BaseObservable {
    private String name;
    private int age;

    public User1(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(bgg.com.datalbing.demo.BR.name);
    }

    @Bindable
    public String getName() {
        return name;
    }


    public int getAge() {
        return age;
    }

    @Bindable
    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(bgg.com.datalbing.demo.BR.age);
    }
}
