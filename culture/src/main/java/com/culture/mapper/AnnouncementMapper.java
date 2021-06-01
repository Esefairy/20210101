package com.culture.mapper;

import com.culture.entity.Announcement;
import com.culture.query.BaseQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnnouncementMapper extends BaseMapper<Announcement> {


    @Select("select * from cul_sentence")
    List<Announcement> findAll();


    @Select("select * from cul_announcement order by createTime desc limit 1")
    List<Announcement> queryAll();

    @Insert("insert into cul_announcement(announcement,createTime) values (#{announcement},#{createTime})")
    void addAnnouncement(Announcement announcement);

    @Update("update cul_announcement set announcement=#{announcement} where id=#{id}")
    void editAnnouncement(Announcement announcement);

    @Delete("delete from cul_announcement where id=#{id}")
    void deleteAnnouncement(Long id);
}
