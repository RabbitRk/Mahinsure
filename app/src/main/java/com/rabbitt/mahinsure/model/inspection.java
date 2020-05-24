package com.rabbitt.mahinsure.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class inspection extends RealmObject {
    private String year, date, month, v_no, cus_name, content;
    private int color;
    private Date date_;
    @PrimaryKey
    private String ref_no;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getV_no() {
        return v_no;
    }

    public void setV_no(String v_no) {
        this.v_no = v_no;
    }

    public String getCus_name() {
        return cus_name;
    }

    public Date getDate_() {
        return date_;
    }

    public void setDate_(Date date_) {
        this.date_ = date_;
    }

    public String getRef_no() {
        return ref_no;
    }

    public void setRef_no(String ref_no) {
        this.ref_no = ref_no;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
