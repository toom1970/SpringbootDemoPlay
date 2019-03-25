package com.example.springbootdemo.dao;

import com.example.springbootdemo.pojo.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleDao {
    @Select("select name, password, role from role")
    public List<Role> getAllRole();

    @Insert("insert into role (name, password, role) values (#{name}, #{password}, #{role})")
    public int addRole(Role role);

    @Delete("delete from role where name = #{name}")
    public int delete(String name);

    @Update("update role set password = #{password}, role = #{role} where name = #{name}")
    public int updateRole(Role role);

    @Select("select name, password, role from role where name = #{name}")
    public Role getRole(String name);
}
