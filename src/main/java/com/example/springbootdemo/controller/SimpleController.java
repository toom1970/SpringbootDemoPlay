package com.example.springbootdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootdemo.dao.UserDao;
import com.example.springbootdemo.pojo.User;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SimpleController {

    @Autowired
    UserDao userDao;

    @RequestMapping("/")
    @ResponseBody
    public PageInfo hello(HttpSession session, Model model, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        model.addAttribute("appName", "hello123");

        PageHelper.startPage(start, size);
        List<User> userList = userDao.listAll();
        PageInfo<User> page = new PageInfo<>(userList);

//        System.out.println(start + ", " + size);
//        System.out.println(page.getPageNum() + ", " + page.getNavigateFirstPage());
        if (page.getPageNum() <= 0)
            page.setNextPage(2);
        model.addAttribute("page", page);
        session.setAttribute("lastPage", page.getNavigateLastPage());
//        JSONObject jsonObject = new JSONObject();
//        jsonObject = JSONObject.toJSON(page);
//        jsonObject.put("page", page);
//        System.out.println(jsonObject.toString());
//        return jsonObject.toJSONString();
        return page;
    }

    @RequestMapping("/addUser")
    public String addUser(User user, HttpSession session) throws Exception {
        userDao.addUser(user);
        List<User> list = userDao.listAll();
        PageInfo<User> page = new PageInfo<>(list);

        int lastPage = (Integer) session.getAttribute("lastPage");
        System.out.println(lastPage);
        return "redirect:/?start=" + Integer.toString(lastPage);
    }

    @RequestMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") int id) throws Exception {
        userDao.deleteUser(id);
        return "redirect:/";
    }

    @RequestMapping("/editeUser/{id}")
    public String editUser(@PathVariable(value = "id") int id, Model model) throws Exception {
        User user = userDao.getUser(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @RequestMapping("/updateUser")
    public String updateUser(User user) throws Exception {
        userDao.updateUser(user);
        return "redirect:/";
    }
}
