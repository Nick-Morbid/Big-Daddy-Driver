package com.daddy.demo.controller;

import com.daddy.demo.entity.POJO.User;
import com.daddy.demo.service.UserService;
import com.daddy.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public User Login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
        user = userService.Login(user);
//        未抛出异常说明登录成功
        response.setHeader("Authorization", JwtUtils.getToken(user.getId(),user.getName()));
        return user;
    }

    @PostMapping("/register")
    public void Register(@RequestBody User user){
        userService.Register(user);
    }


    @GetMapping("/loginout")
    public void loginOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
    }
    @GetMapping("/getUserList")
    public List<User> GetUserList(HttpServletRequest request){
        return userService.GetUserList((Integer) request.getSession().getAttribute("id"));
    }
    @GetMapping("/searchUsers")
    public List<User> SearchUsers(@RequestParam("name")String name,HttpServletRequest request){
        return userService.SearchUsers(name,(Integer)request.getSession().getAttribute("id"));
    }

}
