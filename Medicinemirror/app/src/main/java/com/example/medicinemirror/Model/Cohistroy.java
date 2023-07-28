package com.example.medicinemirror.Model;

public class Cohistroy {
    private String username,herbname,herbpicture;

    public Cohistroy(String username, String herbname, String herbpicture) {
        this.username = username;
        this.herbname = herbname;
        this.herbpicture = herbpicture;
    }

    public Cohistroy() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHerbname() {
        return herbname;
    }

    public void setHerbname(String herbname) {
        this.herbname = herbname;
    }

    public String getHerbpicture() {
        return herbpicture;
    }

    public void setHerbpicture(String herbpicture) {
        this.herbpicture = herbpicture;
    }
}
