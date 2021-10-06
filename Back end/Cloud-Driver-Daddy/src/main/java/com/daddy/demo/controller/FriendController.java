package com.daddy.demo.controller;

import com.daddy.demo.entity.POJO.User;
import com.daddy.demo.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    FriendService friendService;
    @GetMapping("/getFriends")
    public List<User> GetFriends(HttpServletRequest request){
        return friendService.GetFriends((Integer)request.getSession().getAttribute("id"));
    }
//    好友申请接口
    @GetMapping("/requestFriend")
    public void RequestFriend(@RequestParam("id") Integer friendId,HttpServletRequest request){
        friendService.RequestFriend((String)request.getSession().getAttribute ("name"),(Integer)request.getSession().getAttribute("id"),friendId);
    }
//    好友申请处理接口
    @GetMapping("/dealFriendRequest")
    public void DealFriendRequest(@RequestParam("operation")Integer operation, @RequestParam("notificationId") Integer notificationId,@RequestParam("friendId") Integer friendId,HttpServletRequest request){
        friendService.DealFriendRequest(notificationId,operation,(Integer)request.getSession().getAttribute("id"),friendId);
    }
//    删除好友接口
    @GetMapping("/deleteFriend")
    public void DeleteFriend(@RequestParam("id") Integer friendId,HttpServletRequest request){
        friendService.DeleteFriend((Integer)request.getSession().getAttribute("id"),friendId);
    }
}
