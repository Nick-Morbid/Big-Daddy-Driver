package com.daddy.demo.mapper;

import com.daddy.demo.entity.POJO.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NotificationMapper {
    void insertNotification(Notification friendRequest);

    List<Notification> selectNotificatioznList(Integer type, Integer receiverId);

    void deleteNotificationById(Integer id);
}
