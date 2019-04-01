package com.example.springbootdemo.dao;

import com.example.springbootdemo.pojo.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("roleDao")
public interface RoleDao {
    @Select("select name, password, rolesSet from role")
    public List<Role> getAllRole();

    @Insert("insert into role (name, password, rolesSet) values (#{name}, #{password}, #{rolesSet})")
    public int addRole(Role role);

    @Delete("delete from role where name = #{name}")
    public int delete(String name);

    @Update("update role set password = #{password}, role = #{rolesSet}, permissionsSet = #{permissionsSet} where name = #{name}")
    public int updateRole(Role role);

    @Select("select name, password, rolesSet, permissionsSet from role where name = #{name}")
    public Role getRole(String name);
}
