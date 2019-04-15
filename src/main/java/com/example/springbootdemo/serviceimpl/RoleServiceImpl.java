package com.example.springbootdemo.serviceimpl;

import com.example.springbootdemo.dao.RoleDao;
import com.example.springbootdemo.pojo.Role;
import com.example.springbootdemo.service.RoleService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource(name = "roleDao")
    RoleDao roleDao;

    @Override
    public List<Role> getAllRole() {
        return roleDao.getAllRole();
    }

    @Override
    public int addRole(Role role) {
        return roleDao.addRole(role);
    }

    @Override
    public int delete(String name) {
        return roleDao.delete(name);
    }

    @Override
    public int updateRole(Role role) {
        return roleDao.updateRole(role);
    }

    @Cacheable(key = "#name")
    @Override
    public Role getRole(String name) {
        return roleDao.getRole(name);
    }
}
