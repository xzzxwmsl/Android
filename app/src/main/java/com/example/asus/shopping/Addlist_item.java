package com.example.asus.shopping;

import android.widget.TextView;

public class Addlist_item
{
    private String name;
    private String phone;
    private String address;


    public Addlist_item()
    {

    };
    public  Addlist_item(String name,String phone,String address)
    {
        this.name=name;
        this.phone=phone;
        this.address=address;

    }

    public String getName()
    {
        return this.name;
    }

    public String getPhone(){
        return this.phone;
    }

    public String getAddress() {
        return address;
    }



    public void setName(String name) { this.name = name; }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
