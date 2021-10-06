package com.daddy.demo.mapper;

import com.daddy.demo.entity.POJO.Friend;
import com.daddy.demo.entity.POJO.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FriendMapper {
    List<User> selectFriendList(Integer id);

    void insertFriend(Friend friend);

    void deleteFriend(Friend friend);
}
