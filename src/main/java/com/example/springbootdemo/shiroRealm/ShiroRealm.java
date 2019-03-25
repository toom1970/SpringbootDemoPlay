package com.example.springbootdemo.shiroRealm;

import com.example.springbootdemo.dao.RoleDao;
import com.example.springbootdemo.pojo.Role;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    RoleDao roleDao;

    //身份认证
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //权限认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String roleName = (String) usernamePasswordToken.getPrincipal();
        Role role = roleDao.getRole(roleName);
        if(role == null)
            throw new AccountException("role not exist");
        return new SimpleAuthenticationInfo(role,role.getPassword(),getName());
    }
}
