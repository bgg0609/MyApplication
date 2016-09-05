package bgg.com.myapplication.module.job.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class City implements Serializable{
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
