package com.culture.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class User {
    @Excel(name = "编号", width = 25)
    private Long id;
    @Excel(name = "姓名", width = 25)
    private String username;
    private String password;
    @Excel(name = "邮箱", width = 25)
    private String email;
    @Excel(name = "电话", width = 25)
    private String tel;
    @Excel(name = "创建时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
    //时间格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Boolean sex;
    private String headImg;

    private List<Role> roles = new ArrayList();//用户对应的角色集合


    public User() {
    }
}
