package com.culture.service;


import com.culture.entity.Announcement;
import com.culture.query.AnnouncementQuery;
import com.culture.util.PageList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AnnouncementService {

    List<Announcement> findAll();

    List<Announcement> queryAll();

    PageList listpage(AnnouncementQuery announcementQuery);

    void addAnnouncement(Announcement announcement);


    void editAnnouncement(Announcement announcement);

    void deleteAnnouncement(Long id);

}
