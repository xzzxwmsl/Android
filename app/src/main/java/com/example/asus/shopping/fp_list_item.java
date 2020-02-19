package com.example.asus.shopping;

public class fp_list_item
{
    private String imgsrc;
    private String cname;
    private String cid;
    private int cprice;
    private int cnum;
    private String category;

    public fp_list_item()
    {

    }

    public fp_list_item(String imgsrc,String cname,String cid,int cprice,int cnum,String category)
    {
        this.imgsrc=imgsrc;
        this.cname=cname;
        this.cid=cid;
        this.cprice=cprice;
        this.cnum=cnum;
        this.category=category;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setCnum(int cnum) {
        this.cnum = cnum;
    }

    public void setCprice(int cprice) {
        this.cprice = cprice;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getCid() {
        return cid;
    }

    public int getCnum() {
        return cnum;
    }

    public int getCprice() {
        return cprice;
    }

    public String getCname() {
        return cname;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
