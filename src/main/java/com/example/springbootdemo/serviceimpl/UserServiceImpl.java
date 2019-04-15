package com.example.springbootdemo.serviceimpl;

import com.example.springbootdemo.dao.UserDao;
import com.example.springbootdemo.pojo.User;
import com.example.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service("userService")
@CacheConfig(cacheNames = "UserCache")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    @Cacheable(key = "'listAll'")
    public List<User> listAll() {
        return userDao.listAll();
    }

    @Override
    @CacheEvict(allEntries = true)
    public int addUser(User user) {
        userDao.addUser(user);
        return 0;
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    @CachePut(key = "#user.getId()")
    public int updateUser(User user) {
        userDao.updateUser(user);
        return user.getId();
    }

    @Override
    @Cacheable(key = "#id")
    public User getUser(int id) {
        Logger logger = Logger.getLogger("log");
        logger.info("Redis");
//        System.out.println("Redis");
        return userDao.getUser(id);
    }
}
