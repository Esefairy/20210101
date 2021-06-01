package com.culture.controller.index;


import com.culture.entity.Culture;
import com.culture.entity.User;
import com.culture.service.CultureService;
import com.culture.service.UserService;
import com.culture.util.SucErrResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserIndexController {
    @Autowired
    private UserService userService;

    @Autowired
    private CultureService cultureService;


    //注册
    @PostMapping("/index/signup")
    @ResponseBody
    public SucErrResult regUser(@RequestBody  User user){
        try {
            //如果用户名已经存在，就提示用户
            User dbuser = userService.findUserByUserName(user.getUsername());
            if(dbuser != null){
                return SucErrResult.error(" , 此用户名已被注册 ! 请换一个用户名");
            }
            userService.addUser(user);
            return SucErrResult.ok();
        }catch(Exception e){
            return SucErrResult.error("注册失败");
        }

    }

    //登录
    @RequestMapping("/index/login")
    @ResponseBody
    public SucErrResult login(Model model, HttpSession session, @RequestBody User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            User dbUser = userService.findUserByUserName(user.getUsername());
            if(dbUser == null){
                return SucErrResult.error("用户不存在 ! 请前往注册页注册账号");
            }

            if(!passwordEncoder.matches(user.getPassword(),dbUser.getPassword())){
                return SucErrResult.error("密码错误!");
            }
            //登录成功
            session.setAttribute("loginUserName",user.getUsername());
            return SucErrResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SucErrResult.error(e.getMessage());
        }

    }

    @RequestMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute("loginUserName");
        return "redirect:/";
    }

    //个人中心
    @RequestMapping("/center")
    public String myPage(HttpSession session,Model model){
       String username =  (String)session.getAttribute("loginUserName");
       User loginUser = (User) userService.findUserByUserName(username);
        //查询个人收藏
        List<Culture> myScLikes = cultureService.findMyScCulture(loginUser.getId());
        model.addAttribute("likes",myScLikes);
        model.addAttribute("loginUser",loginUser);
        return "index/center";
    }

}
