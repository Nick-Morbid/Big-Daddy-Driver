package com.daddy.demo.service;

import com.daddy.demo.customSettings.Error.ServiceCommon;
import com.daddy.demo.customSettings.Exception.ServiceException;
import com.daddy.demo.entity.POJO.User;
import com.daddy.demo.mapper.UserMapper;
import com.daddy.demo.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    //    登录业务（注：空值检测放到前端来做）
    public User Login(User user) throws ServiceException {
        User _user = userMapper.selectByName(user.getName());
        if (_user==null){
            throw new ServiceException(ServiceCommon.NOT_FOUND);
        }

//        达到这一步说明上面的步骤都未出错
        if (user.getPassword()==null||user.getPassword().equals("")){
            throw new ServiceException(ServiceCommon.VALUE_IS_NULL);
        }

        if (!MD5Utils.passwordIsTrue(user.getPassword(),_user.getPassword())){
            throw new ServiceException(ServiceCommon.LOGIN_NOT_PASSED);
        }else {
            return _user;
        }
    }

    //    注册业务
    @Transactional
    public void Register(User user) throws ServiceException{
        Map<String,Object> map = new HashMap<>();
        if (user.getName()==null||user.getName().equals("")||
            user.getPassword()==null||user.getPassword().equals("")){
            throw new ServiceException(ServiceCommon.VALUE_IS_NULL);
        }
        User _user = userMapper.selectByName(user.getName());
        if (_user!=null){
            throw new ServiceException(ServiceCommon.NAME_NOT_ONLY);
        }

        user.setPassword(MD5Utils.string2MD5(user.getPassword()));
        userMapper.insertUser(user);//需要开启事务支持
    }

    public List<User> GetUserList(Integer id) {
       return userMapper.selectList(id);
    }

    public List<User> SearchUsers(String name, Integer id) {
        if (name==null||name.equals("")){
            return GetUserList(id);
        }
        return userMapper.SearchUsers(name,id);
    }
}
