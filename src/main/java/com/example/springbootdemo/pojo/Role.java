package com.example.springbootdemo.pojo;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Role {

    private String name;
    private String password;
    //0-normalUser  1-admin
    //    private Boolean role;
//    private Set<String> roles;
//    private Set<String> permissions;
    private String rolesSet;
    private String permissionsSet;

    public Set<String> getRoles() {
        return new HashSet<>(Arrays.asList(rolesSet.split(",")));
    }

    public void setRoles(Set<String> roles) {
        this.rolesSet = StringUtils.join(roles.toArray(), ",");
    }

    public Set<String> getPermissions() {
        return new HashSet<>(Arrays.asList(permissionsSet.split(",")));
    }

    public void setPermissions(Set<String> permissions) {
        this.permissionsSet = StringUtils.join(permissions.toArray(), ",");
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
