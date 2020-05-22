package com.rabbitt.mahinsure.model;

import android.graphics.Bitmap;
import android.net.Uri;

public class grid {

    String event_name;
    Bitmap image_uri;

    public grid() {
    }

    public grid(String event_name, Bitmap bitmap) {
        this.event_name = event_name;
        this.image_uri = bitmap;
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
