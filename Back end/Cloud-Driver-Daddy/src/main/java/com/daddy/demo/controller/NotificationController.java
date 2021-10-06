package com.daddy.demo.controller;

import com.daddy.demo.entity.POJO.Notification;
import com.daddy.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    NotificationService notificationService;
    @GetMapping("/getNotification")
    public List<Notification> GetNotification(@RequestParam("type")Integer type, HttpServletRequest request){
        return notificationService.GetNotification(type,(Integer)request.getSession().getAttribute("id"));
    }
}
