package com.culture.mapper;



import com.culture.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {

    List<Permission> listPermissionByUserId(Long userid);
    //查找所有的权限
    List<Permission> findAllPermisisons();

}
