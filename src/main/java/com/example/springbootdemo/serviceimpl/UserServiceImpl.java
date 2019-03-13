package com.example.springbootdemo.serviceimpl;

import com.example.springbootdemo.dao.UserDao;
import com.example.springbootdemo.pojo.User;
import com.example.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public List<User> listAll() {
        return userDao.listAll();
    }

    @Override
    public int addUser(User user) {
        userDao.addUser(user);
        return 0;
    }

    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public int updateUser(User user) {
        userDao.updateUser(user);
        return user.getId();
    }

    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }
}
