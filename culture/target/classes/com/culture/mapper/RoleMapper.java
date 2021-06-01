package com.culture.mapper;


import com.culture.entity.Role;
import com.culture.entity.User;
import com.culture.query.RoleQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<Role> listRoleByUserId(Long userid);

    Long queryTotal(RoleQuery userQuery);

    List<User> queryData(RoleQuery userQuery);


    //查询所有的角色
    List<Role> queryAll();
    //添加用户角色
    void addUserRole(List userRole);

    //删除用户角色
    void deleteUserRole(Long userId);
    //删除角色对应的权限
    void deleteRolePermission(Long parseLong);
    //添加角色对应的权限
    void addRolePermission(List rolePermissionList);
}
