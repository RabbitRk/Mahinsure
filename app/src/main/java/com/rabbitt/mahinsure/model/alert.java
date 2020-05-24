package com.rabbitt.mahinsure.model;

import io.realm.RealmObject;

public class alert extends RealmObject {
    private String ref_no;

    private String content;

    public String getRef_no() {
        return ref_no;
    }

    public void setRef_no(String ref_no) {
        this.ref_no = ref_no;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
