package com.culture.service.impl;


import com.culture.entity.Role;
import com.culture.entity.User;
import com.culture.mapper.RoleMapper;
import com.culture.query.RoleQuery;
import com.culture.service.RoleService;
import com.culture.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> listRoleByUserId(Long userid) {
        return roleMapper.listRoleByUserId(userid);
    }


    @Override
    public PageList listpage(RoleQuery userQuery) {
        PageList pageList = new PageList();
        //查询总的条数
        Long total = roleMapper.queryTotal(userQuery);
        List<User> users = roleMapper.queryData(userQuery);
        pageList.setTotal(total);
        pageList.setRows(users);
        //分页查询的数据
        return pageList;
    }


    @Override
    public List<Role> queryAll() {
        return roleMapper.queryAll();
    }

    //  insert into t_user_role(userid,roleid) values(xx,xxx),(xx,yy)
    @Override
    @Transactional
    public void addUserRole(String userId, List roleIds) {
        List userRolesList = new ArrayList();
        for (Object roleId : roleIds) {
            Map mp = new HashMap();
            mp.put("userId", userId);
            mp.put("roleId", roleId);
            userRolesList.add(mp);
        }
        //先删除用户角色
        roleMapper.deleteUserRole(Long.parseLong(userId));
        //添加用户角色
        roleMapper.addUserRole(userRolesList);
    }

    @Override
    @Transactional
    public void addRolePermission(String roleId, List permissionIds) {

        List rolePermissionList = new ArrayList();
        for (Object permissionId : permissionIds) {
            Map mp = new HashMap();
            mp.put("roleId", roleId);
            mp.put("permissionId", permissionId);
            rolePermissionList.add(mp);
        }
        //先删除角色对应的权限
        roleMapper.deleteRolePermission(Long.parseLong(roleId));
        //添加角色对应的权限
        roleMapper.addRolePermission(rolePermissionList);

    }
}
