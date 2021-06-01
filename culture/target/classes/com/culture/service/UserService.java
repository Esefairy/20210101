package com.culture.service;


import com.culture.entity.User;
import com.culture.query.UserQuery;
import com.culture.util.PageList;

import java.util.List;

public interface UserService {

    //根据用户名取到用户
    User findUserByUserName(String username);

    String findUserById(Long id);

    //查询所有用户
    List<User> queryAll();

    //添加用户
    Integer addUser(User user);

    //根据用户id更新头像
    void updateUserHeadImg(User user);

    //分页方法
    PageList listpage(UserQuery userQuery);

    //删除用户
    void deleteUser(Long id);

    //批量删除
    void batchRemove(List list);

    //修改保存用户
    void editSaveUser(User user);

}
