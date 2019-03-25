package com.example.springbootdemo;

import com.example.springbootdemo.shiroRealm.ShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //负责shiroBean的生命周期
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    //自定义的认证类，继承AuthorizingRealm，负责用户认证和权限处理
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        return shiroRealm;
    }

    //安全管理器，将realm加入securityManager
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    /*shiro filter 工厂类
     * 1.定义ShiroFilterFactoryBean
     * 2.设置SecurityManager
     * 3.配置拦截器
     * 4.返回定义ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        //1
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //2
        //注册securityManagee
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //3
        // 拦截器+配置登录和登录成功之后的url
        //LinkHashMap是有序的，shiro会根据添加的顺序进行拦截
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //配置不会被拦截的连接  这里顺序判断
        //anon，所有的url都可以匿名访问
        //authc：所有url都必须认证通过才可以访问
        //user，配置记住我或者认证通过才能访问
        //logout，退出登录
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/editUser/*", "authc");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**", "authc");
        //设置登录界面，如果不设置为寻找web根目录下"/login.jsp"页面 或 "/login"映射
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/success");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //4
        return shiroFilterFactoryBean;
    }
}
