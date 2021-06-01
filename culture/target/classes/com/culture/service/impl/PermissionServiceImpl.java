package com.culture.service.impl;


import com.culture.entity.Permission;
import com.culture.mapper.PermissionMapper;
import com.culture.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public List<Permission> listPermissionByUserId(Long userid) {
        return permissionMapper.listPermissionByUserId(userid);
    }

    @Override
    public List<Permission> findAllPermisisons() {
        return permissionMapper.findAllPermisisons();
    }
}
