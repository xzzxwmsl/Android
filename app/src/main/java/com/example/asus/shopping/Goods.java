package com.example.asus.shopping;

/**
 * Created by LYC on 2019/7/4.
 */

public class Goods {
    private String name;
    private int imageID;
    private String content;

    public Goods(String name, int imageID)
    {
        this.name=name;
        this.imageID=imageID;

    }
    public String getName()
    {
        return  name;
    }
    public  int getImageID()
    {
        return imageID;
    }
    public String getcontent()
    {
        return content;
    }

}
