package com.culture.mapper;

import com.culture.entity.User;
import com.culture.query.UserQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    //根据用户名查询用户
    User findUserByUserName(String username);

    @Select("select username from cul_user where id=#{id}")
    String findUserById(Long id);

    @Select("select headImg from cul_user where id=#{id}")
    String findUserByImg(Long id);

    //查询所有用户
    List<User> queryAll();

    @Select("select * from cul_user")
    List<User> findAll();

    //添加用户
    Integer addUser(User user);

    //根据用户id更新头像
    void updateUserHeadImg(User user);

    //查询总的条数
    Long queryTotal(UserQuery userQuery);

    //分页查询数据
    List<User> queryData(UserQuery userQuery);

    //删除数据
    void deleteUser(Long id);

    //批量删除
    void batchRemove(List ids);

    //修改保存用户
    void editSaveUser(User user);

//    //更新用户金额
//    @Update("update t_user set fineamt=#{fineamt} where id=#{id} ")
//    void updateUserFinecost(Culture user);
}
