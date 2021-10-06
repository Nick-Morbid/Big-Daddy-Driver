package com.daddy.demo.service;

import com.daddy.demo.entity.POJO.Notification;
import com.daddy.demo.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;
    public List<Notification> GetNotification(Integer type, Integer id) {
        return notificationMapper.selectNotificatioznList(type,id);
    }
}
