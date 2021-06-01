package com.culture.service;



import com.culture.entity.Role;
import com.culture.query.RoleQuery;
import com.culture.util.PageList;

import java.util.List;

public interface RoleService {

    List<Role> listRoleByUserId(Long userid);

    PageList listpage(RoleQuery roleQuery);

    //查询所有角色
    List<Role> queryAll();
    //添加角色
    void addUserRole(String userId, List roleIds);
    //添加角色对应的权限
    void addRolePermission(String roleId, List permissionIds);
}
