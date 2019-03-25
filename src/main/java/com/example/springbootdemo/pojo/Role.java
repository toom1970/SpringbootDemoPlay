package com.example.springbootdemo.pojo;

public class Role {
    //0-normalUser  1-admin
    private Boolean role;
    private String name;
    private String password;

    public void setRole(Boolean role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
