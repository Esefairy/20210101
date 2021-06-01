package com.culture.service.impl;

import com.culture.entity.Announcement;
import com.culture.mapper.AnnouncementMapper;
import com.culture.query.AnnouncementQuery;
import com.culture.service.AnnouncementService;
import com.culture.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public List<Announcement> findAll() {
        return announcementMapper.findAll();
    }

    @Override
    public List<Announcement> queryAll() {
        return announcementMapper.queryAll();
    }

    @Override
    public PageList listpage(AnnouncementQuery announcementQuery) {
        PageList pageList = new PageList();

        Long total = announcementMapper.queryTotal(announcementQuery);
        List<Announcement> announcements = announcementMapper.queryData(announcementQuery);

        pageList.setTotal(total);
        pageList.setRows(announcements);
        return pageList;

    }

    @Override
    public void addAnnouncement(Announcement announcement) {
        announcement.setCreateTime(new Date());

        announcementMapper.addAnnouncement(announcement);

    }

    @Override
    public void editAnnouncement(Announcement announcement) {

        announcementMapper.editAnnouncement(announcement);
    }

    @Override
    public void deleteAnnouncement(Long id) {

        announcementMapper.deleteAnnouncement(id);
    }
}
