package com.example.springbootdemo.service;

import com.example.springbootdemo.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<User> listAll();

    public int addUser(User user);

    public void deleteUser(int id);

    public int updateUser(User user);

    public User getUser(int id);
}
