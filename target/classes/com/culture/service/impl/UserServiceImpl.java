package com.culture.service.impl;


import com.culture.entity.User;
import com.culture.mapper.UserMapper;
import com.culture.query.UserQuery;
import com.culture.service.UserService;
import com.culture.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUserName(String username) {
        return userMapper.findUserByUserName(username);
    }

    @Override
    public String findUserById(Long id) {
        return userMapper.findUserById(id);
    }

    @Override
    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    @Override
    public Integer addUser(User user) {
        //设置创建时间
        user.setCreateTime(new Date());
        //加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePwd = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePwd);
        return userMapper.addUser(user);
    }

    @Override
    public void updateUserHeadImg(User user) {
        userMapper.updateUserHeadImg(user);
    }

    @Override
    public PageList listpage(UserQuery userQuery) {
        PageList pageList = new PageList();
        //查询总的条数
        Long total = userMapper.queryTotal(userQuery);
        List<User> users = userMapper.queryData(userQuery);
        pageList.setTotal(total);
        pageList.setRows(users);
        //分页查询的数据
        return pageList;
    }

    //删除用户
    @Override
    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
    }

    @Override
    public void batchRemove(List list) {
        userMapper.batchRemove(list);
    }

    @Override
    public void editSaveUser(User user) {
        userMapper.editSaveUser(user);
    }


}
