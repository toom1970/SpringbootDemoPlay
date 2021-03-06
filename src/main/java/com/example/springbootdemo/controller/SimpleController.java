package com.example.springbootdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootdemo.ShiroSessionListener;
import com.example.springbootdemo.pojo.User;
import com.example.springbootdemo.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class SimpleController {

    @Resource(name = "userService")
    UserService userService;

    @RequestMapping("/")
    @ResponseBody
    public PageInfo hello(HttpSession session, Model model, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
//        model.addAttribute("appName", "hello123");

        Subject subject = SecurityUtils.getSubject();

        PageHelper.startPage(start, size);
        List<User> userList = userService.listAll();
        PageInfo<User> page = new PageInfo<>(userList);
        if (page.getPageNum() <= 0)
            page.setNextPage(2);
//        model.addAttribute("page", page);
        session.setAttribute("lastPage", page.getNavigateLastPage());
//        JSONObject jsonObject = new JSONObject();
//        jsonObject = JSONObject.toJSON(page);
//        jsonObject.put("page", page);
//        System.out.println(jsonObject.toString());
//        return jsonObject.toJSONString();
        Jedis jedis = new Jedis("localhost");
        Logger logger = Logger.getLogger("Jedis");
        logger.info(jedis.ping());
//        if (true)
//            throw new Exception("asd");
        return page;
    }

    @RequestMapping("/addUser")
    public String addUser(User user, HttpSession session) {
        userService.addUser(user);
        int lastPage = (Integer) session.getAttribute("lastPage");
        System.out.println(lastPage);
        return "redirect:/?start=" + Integer.toString(lastPage);
    }

    @RequestMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") int id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

    @RequestMapping("/editUser/{id}")
    @ResponseBody
    public User editUser(@PathVariable(value = "id") int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
//        return "editUser";

        return user;
    }

    @RequestMapping("/updateUser")
    public String updateUser(User user) {
        userService.updateUser(user);
        return "redirect:/";
    }

    @RequestMapping("/success")
    public String certifiedSuccess() {
        return "redirect:/";
    }

    @ResponseBody
    @RequestMapping("/login")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password) {
        Subject subject = SecurityUtils.getSubject();
        JSONObject json = new JSONObject();
        if (subject.isAuthenticated() == false) {
            UsernamePasswordToken token = new UsernamePasswordToken(name, password);
            try {
                subject.login(token);
                json.put("message", "SUCCESS!");
//                subject.logout();
            } catch (UnknownAccountException e) {
                json.put("message", e.getMessage());
                return json.toJSONString();
            } catch (IncorrectCredentialsException e) {
                json.put("message", e.getMessage());
                return json.toJSONString();
            } catch (LockedAccountException e) {
                json.put("message", e.getMessage());
                return json.toJSONString();
            } catch (AuthenticationException e) {
                json.put("message", e.getMessage());
                return json.toJSONString();
            }
        }
        Session session = subject.getSession(true);
        json.put("sessionId", session.getId());
        json.put("sessionTime", session.getTimeout());

//        json.put("count",sessionListener.getSessionCount());
        return json.toJSONString();
    }

    @RequiresAuthentication
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }

    @RequestMapping("/403")
    public String unau() {
        return "error/403";
    }

    @ResponseBody
    @RequestMapping("/add")
    public String PermissionTest() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "good");
        jsonObject.put("CurrectSessionId", session.getId());
        return jsonObject.toJSONString();
    }
}
