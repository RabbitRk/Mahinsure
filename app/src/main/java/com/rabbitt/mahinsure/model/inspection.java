package com.rabbitt.mahinsure.model;

import io.realm.RealmObject;

public class inspection extends RealmObject {
    private String year, date, month, ref_no, v_no, cus_name, color;

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

    public String getRefno() {
        return ref_no;
    }

    public void setRefno(String refno) {
        this.ref_no = refno;
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

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
