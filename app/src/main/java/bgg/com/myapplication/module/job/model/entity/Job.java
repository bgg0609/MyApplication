package bgg.com.myapplication.module.job.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class Job implements Serializable{
    private String avatar;
    private String name;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
