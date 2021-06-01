package com.culture.entity;

import lombok.Data;

import java.util.Date;


@Data
public class Culture {

    private Long id;
    private String cultureName;
    private String address;
    private String info;
    private String desc;
    private String fmUrl;
    private Long categoryId;
    private Category category;

    private Long creatorId;
    private User user;

    private Date createTime;

    private Long view;
    private double w;

}
