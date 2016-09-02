package bgg.com.myapplication;

import android.app.Application;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class MyApplication extends Application {

    public static MyApplication application;

    public static MyApplication getInstance() {

        if (application == null) {
            application = new MyApplication();
        }

        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

}