package com.culture.controller.admin;

import com.culture.entity.Menu;
import com.culture.service.CultureService;
import com.culture.service.MenuService;
import com.culture.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class RouterController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private CultureService cultureService;

    //主页
    @RequestMapping("/admin")
    public String toLogin(HttpSession session, Model model){

        if(session == null){
            return "redirect:/toLogin";
        }
        //根据登录用户的角色 确定菜单
        Long userId = CommonUtil.getLoginUser().getId();
        List<Menu> menus = menuService.findAll(userId);
        if (menus != null) {
            session.setAttribute("menuList", menus);
        }

        //查询总的浏览量
        model.addAttribute("total_view",cultureService.queryTotalViewNum());
        //查询总的数量
        model.addAttribute("total_culture",cultureService.queryTotalCultureNum());
        //查询总的注册人数
        model.addAttribute("total_user",cultureService.queryTotalUserRegNum());
        //查询总的收藏量
        model.addAttribute("total_like",cultureService.queryTotalScNum());

        return "admin/index";
    }

    //登录页 toLogin
    @RequestMapping({"/toLogin"})
    public String toLogin(){
        return "admin/login";
    }



}
