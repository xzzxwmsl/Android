package com.example.asus.shopping;

public class order_item_citem
{
    private String imgpath;
    private String cid;
    private int price;
    private int num;

    public order_item_citem()
    {

    }
    public order_item_citem(String path,String id,int price,int num)
    {
        this.imgpath=path;
        this.cid=id;
        this.price=price;
        this.num=num;
    }

    public String getImgpath() {
        return imgpath;
    }

    public String getCid() {
        return cid;
    }

    public int getPrice() {
        return price;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
