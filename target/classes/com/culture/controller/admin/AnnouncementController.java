package com.culture.controller.admin;

import com.culture.entity.Announcement;
import com.culture.query.AnnouncementQuery;
import com.culture.service.AnnouncementService;
import com.culture.util.AjaxResult;
import com.culture.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/announcement/index")
    public String announcementList() {
        return "admin/announcement";
    }

    @GetMapping("/announcement/listpage")
    @ResponseBody
    public PageList listPage(AnnouncementQuery announcementQuery){
        return announcementService.listpage(announcementQuery);
    }

    @PostMapping("/announcement/addAnnouncement")
    @ResponseBody
    public AjaxResult addAnnouncement(Announcement announcement) {

        try {
            announcementService.addAnnouncement(announcement);
            return new AjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxResult("修改失败");
    }

    @PostMapping("/announcement/editAnnouncement")
    @ResponseBody
    public AjaxResult editAnnouncement(Announcement announcement) {
        try {
            announcementService.editAnnouncement(announcement);
            return new AjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxResult("修改失败");
    }

    @GetMapping("/announcement/deleteAnnouncement")
    @ResponseBody
    public AjaxResult deleteAnnouncement(@RequestParam(required = true) Long id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            announcementService.deleteAnnouncement(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult("删除失败");
        }
        return ajaxResult;
    }


}
