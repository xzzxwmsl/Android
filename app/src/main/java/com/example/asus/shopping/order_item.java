package com.example.asus.shopping;

import java.util.LinkedList;
import java.util.List;

public class order_item
{
    private int orderid;
    private int totalprice;
    private int totalnum;
    private String status;
    private LinkedList<order_item_citem> cdata;

    private Addlist_item add;

    public order_item()
    {

    }
    public order_item(int id,int price,int num,String statu,LinkedList<order_item_citem> data,Addlist_item add)
    {
        this.orderid=id;
        this.totalnum=num;
        this.totalprice=price;
        this.status=statu;
        this.cdata=data;
        this.add=add;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalnum(int totalnum) {
        this.totalnum = totalnum;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public void setAdd(Addlist_item add) {
        this.add = add;
    }

    public int getOrderid() {
        return orderid;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalnum() {
        return totalnum;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public Addlist_item getAdd() {
        return add;
    }

    public LinkedList<order_item_citem> getCdata() {
        return cdata;
    }

    public void setCdata(LinkedList<order_item_citem> cdata) {
        this.cdata = cdata;
    }

}
