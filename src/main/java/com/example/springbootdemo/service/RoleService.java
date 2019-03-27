package com.example.springbootdemo.service;

import com.example.springbootdemo.pojo.Role;

import java.util.List;

public interface RoleService {
    public List<Role> getAllRole();

    public int addRole(Role role);

    public int delete(String name);

    public int updateRole(Role role);

    public Role getRole(String name);
}
