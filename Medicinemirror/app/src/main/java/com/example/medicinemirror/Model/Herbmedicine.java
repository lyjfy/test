package com.example.medicinemirror.Model;

public class Herbmedicine {
//    private int herbid;//草药id
    private String herbpicturepath, title,content;//草药名字和内容

    public Herbmedicine() {
    }

    public Herbmedicine(  String name, String content,String herbpicturepath) {
        this.herbpicturepath = herbpicturepath;
        this.title = name;
        this.content = content;
    }

    public String getHerbpicturepath() {
        return herbpicturepath;
    }

    public void setHerbpicturepath(String herbpicturepath) {
        this.herbpicturepath = herbpicturepath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
