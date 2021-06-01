package com.culture.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class Sentence {
    private Long id;
    private String content;
    private Long createId;
    private String createName;
    private String createImg;
    private Date createTime;

    List<User> userList;
}
