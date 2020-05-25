package com.rabbitt.mahinsure.model;

import android.graphics.Bitmap;

public class grid {

    private String event_name;
    private Boolean bool;
    private Bitmap image_uri;

    public grid() {
    }

    public Boolean getBool() {
        return bool;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }

    public grid(String event_name, Bitmap bitmap, Boolean bool) {
        this.event_name = event_name;
        this.image_uri = bitmap;
        this.bool = bool;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public Bitmap getImage() {
        return image_uri;
    }

    public void setImage(Bitmap image) {
        this.image_uri = image;
    }
}
