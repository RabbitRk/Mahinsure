package com.rabbitt.mahinsure.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class inspection extends RealmObject {
    private String year, date, month, v_no, cus_name, content;
    private int color;
    @PrimaryKey
    private String ref_no;

    private String rec, sub, app;

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

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
