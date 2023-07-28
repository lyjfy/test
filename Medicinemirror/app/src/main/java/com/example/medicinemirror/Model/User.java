package com.example.medicinemirror.Model;

public class User {
    private String username;//用户名
    private String userpwd;//密码
    private String usersex;//性别
    private String usertel;//电话
    private String useradress;//住址

    public User() {
    }

    public User(String username, String userpwd, String usersex, String usertel, String useradress) {
        this.username = username;
        this.userpwd = userpwd;
        this.usersex = usersex;
        this.usertel = usertel;
        this.useradress = useradress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getUsersex() {
        return usersex;
    }

    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }

    public String getUsertel() {
        return usertel;
    }

    public void setUsertel(String usertel) {
        this.usertel = usertel;
    }

    public String getUseradress() {
        return useradress;
    }

    public void setUseradress(String useradress) {
        this.useradress = useradress;
    }
}
