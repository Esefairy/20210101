package com.culture.controller.index;

import com.culture.entity.Culture;
import com.culture.entity.Like;
import com.culture.entity.User;
import com.culture.service.CultureService;
import com.culture.service.UserService;
import com.culture.util.SucErrResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LikeIndexController {

    @Autowired
    private CultureService cultureService;

    @Autowired
    private UserService userService;

    @RequestMapping("/culture/cancel/{id}")
    public String cancelScCulture(HttpSession httpSession, @PathVariable("id") Long id, Model model) {

        String username = (String) httpSession.getAttribute("loginUserName");
        User loginUser = userService.findUserByUserName(username);
        //取消收藏
        cultureService.cancelScCulture(loginUser.getId(), id);

        List<Culture> myScCulture = cultureService.findMyScCulture(loginUser.getId());
        model.addAttribute("likes", myScCulture);
        model.addAttribute("loginUser", loginUser);

        return "/index/center";
    }

    @RequestMapping("/culture/like/{id}")
    @ResponseBody
    public SucErrResult scCulture(HttpSession httpSession, @PathVariable("id") Long id) {

        try {
            String username = (String) httpSession.getAttribute("loginUserName");
            User loginUser = userService.findUserByUserName(username);
            boolean isExist = cultureService.isExistScCulture(loginUser.getId(), id);
            if (isExist) {
                return SucErrResult.error("您已经收藏过了");
            } else {
                cultureService.scCulture(loginUser.getId(), id);
            }

            return SucErrResult.ok();

        } catch (Exception e) {
            e.printStackTrace();
            return SucErrResult.error("收藏失败");
        }
    }


}
