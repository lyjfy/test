package com.example.medicinemirror.Model;

public class Herbtype {
    private String title,picturepath,type;

    public Herbtype() {
    }

    public Herbtype(String title, String picturepath, String type) {
        this.title = title;
        this.picturepath = picturepath;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicturepath() {
        return picturepath;
    }

    public void setPicturepath(String picturepath) {
        this.picturepath = picturepath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
