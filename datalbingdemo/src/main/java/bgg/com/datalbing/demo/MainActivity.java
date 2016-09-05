package bgg.com.datalbing.demo;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bgg.com.datalbing.demo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        user = new User("安静的美女子", 18);
        binding.setStu(user);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                user.setName("00000000");
                user.setAge(20);
            }
        }, 5000);
    }
}
