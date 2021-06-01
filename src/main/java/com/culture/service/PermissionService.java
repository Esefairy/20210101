package com.culture.service;


import com.culture.entity.Permission;

import java.util.List;

public interface PermissionService {

    //根据用户查询权限
    List<Permission> listPermissionByUserId(Long userid);
    //查找所有的权限
     List<Permission> findAllPermisisons();

}
