package com.example.springbootdemo.dao;

import com.example.springbootdemo.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserDao {

    @Select("select * from ggg")
    List<User> listAll();

    @Insert("insert into ggg (name, sex) values (#{name}, #{sex})")
    public int addUser(User user);

    @Delete("delete from ggg where id = #{id}")
    public void deleteUser(int id);

    @Update("update ggg set name=#{name}, sex=#{sex} where id = #{id}")
    public int updateUser(User user);

    @Select("select * from ggg where id = #{id}")
    public User getUser(int id);
}
