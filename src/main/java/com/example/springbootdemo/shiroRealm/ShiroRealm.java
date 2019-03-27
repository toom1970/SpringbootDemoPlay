package com.example.springbootdemo.shiroRealm;

import com.example.springbootdemo.dao.RoleDao;
import com.example.springbootdemo.pojo.Role;
import com.example.springbootdemo.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {
    @Resource(name = "roleService")
    RoleService roleService;

    /*权限验证,
     * principalCollection.getPrimaryPrincipal()返回类型
     *和SimpleAuthenticationInfo构造函数第一个参数类型一致
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Role role = (Role) principalCollection.getPrimaryPrincipal();
        Role roleInDatabase = roleService.getRole(role.getName());
        Set<String> roles = new HashSet<>();
        for (String roleType : roleInDatabase.getRoles())
            roles.add(roleType);
        simpleAuthorizationInfo.setRoles(roles);
        Set<String> permissions = new HashSet<>();
        for (String permission : roleInDatabase.getPermissions())
            permissions.add(permission);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    //身份验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String roleName = (String) usernamePasswordToken.getPrincipal();
        Role role = roleService.getRole(roleName);
        if (role == null)
            throw new AccountException("role not exist");
        return new SimpleAuthenticationInfo(role, role.getPassword(), getName());
    }
}
