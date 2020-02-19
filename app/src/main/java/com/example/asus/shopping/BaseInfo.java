package com.example.asus.shopping;

/**
 * Created by Administrator on 2018/3/7.
 */

public class BaseInfo {
    protected String Id;
    protected String name;
    protected boolean isChoosed;

    public BaseInfo() {
        super();
    }

    public BaseInfo(String id, String name) {
        super();
        Id = id;
        this.name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }
}

