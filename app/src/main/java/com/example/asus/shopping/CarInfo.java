package com.example.asus.shopping;
import java.io.Serializable;

/**
 * cjy
 */

public class CarInfo extends BaseInfo implements Serializable {

    private String carid;
    private String spsn;
    private String spname;
    private String sptype;
    private String spprice;
    private String spnum;
    private String spimg;

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getSpsn() {
        return spsn;
    }

    public void setSpsn(String spsn) {
        this.spsn = spsn;
    }

    public String getSpname() {
        return spname;
    }

    public void setSpname(String spname) {
        this.spname = spname;
    }

    public String getSptype() {
        return sptype;
    }

    public void setSptype(String sptype) {
        this.sptype = sptype;
    }

    public String getSpprice() {
        return spprice;
    }

    public void setSpprice(String spprice) {
        this.spprice = spprice;
    }

    public String getSpnum() {
        return spnum;
    }

    public void setSpnum(String spnum) {
        this.spnum = spnum;
    }

    public String getSpimg() {
        return spimg;
    }

    public void setSpimg(String spimg) {
        this.spimg = spimg;
    }
}
