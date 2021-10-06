package com.daddy.demo.service;

import com.daddy.demo.entity.POJO.Friend;
import com.daddy.demo.entity.POJO.Notification;
import com.daddy.demo.entity.POJO.User;
import com.daddy.demo.mapper.FriendMapper;
import com.daddy.demo.mapper.NotificationMapper;
import com.daddy.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class FriendService {
    @Autowired
    FriendMapper friendMapper;
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    UserMapper userMapper;

    public List<User> GetFriends(Integer id) {
        return friendMapper.selectFriendList(id);
    }

    public void RequestFriend(String name,Integer id, Integer friendId) {
        Notification friendRequest = new Notification(null,"用户"+name+"向你发来了一条好友申请.",0,id,friendId,new Date(System.currentTimeMillis()));
        notificationMapper.insertNotification(friendRequest);
    }

    @Transactional
    public void DealFriendRequest(Integer notificationId,Integer operation,Integer id, Integer friendId) {
//        这里的friendId为之前申请加好友的用户的id
        Notification passFriendReuquest = null;
        if (operation==1){//1为通过
            passFriendReuquest = new Notification(null,"用户"+userMapper.selectById(id).getName()+"同意了你的好友申请",1,id,friendId,new Date(System.currentTimeMillis()));
            friendMapper.insertFriend(new Friend(null,friendId,id));//插入朋友关系
            friendMapper.insertFriend(new Friend(null,id,friendId));//插入朋友关系
        }else {
            passFriendReuquest = new Notification(null,"用户"+userMapper.selectById(id).getName()+"拒绝了你的好友申请",1,id,friendId,new Date(System.currentTimeMillis()));
        }
        notificationMapper.insertNotification(passFriendReuquest);
//        操作完成后，需要删除对应的消息
        notificationMapper.deleteNotificationById(notificationId);
    }

    @Transactional
    public void DeleteFriend(Integer id, Integer friendId) {
        friendMapper.deleteFriend(new Friend(null,id,friendId));
        friendMapper.deleteFriend(new Friend(null,friendId,id));
    }
}
