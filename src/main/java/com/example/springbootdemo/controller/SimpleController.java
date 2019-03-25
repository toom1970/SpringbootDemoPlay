package com.example.springbootdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootdemo.pojo.User;
import com.example.springbootdemo.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class SimpleController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    @ResponseBody
    public PageInfo hello(HttpSession session, Model model, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "10") int size) throws
            Exception {
        model.addAttribute("appName", "hello123");

        PageHelper.startPage(start, size);
        List<User> userList = userService.listAll();
        PageInfo<User> page = new PageInfo<>(userList);
        if (page.getPageNum() <= 0)
            page.setNextPage(2);
        model.addAttribute("page", page);
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
        Map<String, Object> map = new HashMap<>();

        Subject subject = SecurityUtils.getSubject();
        JSONObject json = new JSONObject();
        if (subject.isAuthenticated() == false) {
            UsernamePasswordToken token = new UsernamePasswordToken(name, password);
            try {
                subject.login(token);
                json.put("message", "SUCCESS!");
                subject.logout();
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
        return json.toJSONString();
    }
}
