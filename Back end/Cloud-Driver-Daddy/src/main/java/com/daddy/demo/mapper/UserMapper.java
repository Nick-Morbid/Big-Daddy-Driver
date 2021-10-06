package com.daddy.demo.mapper;

import com.daddy.demo.entity.POJO.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    User selectByName(String name);
    void insertUser(User user);

    User selectById(Integer id);

//    查找除用户id的好友外的用户
    List<User> selectList(Integer id);

    List<User> SearchUsers(@Param("name") String name, @Param("id") Integer id);
}
