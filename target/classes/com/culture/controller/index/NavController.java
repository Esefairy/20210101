package com.culture.controller.index;

import com.culture.entity.Culture;
import com.culture.service.AnnouncementService;
import com.culture.service.CultureService;
import com.culture.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class NavController {

    @Autowired
    private CultureService cultureService;

    @Autowired
    private SentenceService sentenceService;

    @Autowired
    private AnnouncementService announcementService;

    //前台登录页
    @RequestMapping({"/login"})
    public String frontLoginPage(){
        return "index/login";
    }
    //前台注册页
    @RequestMapping({"/signup"})
    public String registerPage(){
        return "index/signup";
    }


    @RequestMapping({"/","/index"})
    public String index(Model model){

        model.addAttribute("announcements", announcementService.queryAll());

        //查询全部
        //model.addAttribute("cultures",cultureService.findAll());

        model.addAttribute("sentences",sentenceService.queryAll());
        //查询热门
        model.addAttribute("cultures",cultureService.queryHotCulture());

        List<Culture> list = cultureService.findAll();

        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        Date date = new Date(System.currentTimeMillis());
        int currentTime = Integer.parseInt(formatter.format(date));
        Long cultureId = 0L;
        for (Culture culture : list){

           if (culture.getId() % currentTime == 0){
               cultureId = culture.getId();
           }
        }
        if (0 == cultureId){
            cultureId = list.get(0).getId();
        }
        Culture cultureIdToday = cultureService.findDetailById(cultureId);
        model.addAttribute("cultureToday",cultureIdToday);
        return "index/index";
    }


    @RequestMapping("/sentence")
    public String sentence(Model model){
        model.addAttribute("sentences",sentenceService.findAll());

        return "index/sentence";
    }

    @RequestMapping("/about")
    public String about(){
        return "index/about";
    }


}
