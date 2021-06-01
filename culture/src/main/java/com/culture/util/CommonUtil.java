package com.culture.util;

import com.culture.config.UserSecurity;
import com.culture.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtil {

    //获取当前登录用户
    public static User getLoginUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal().equals("anonymousUser")){
            return null;
        }else {
            UserSecurity userSecurity = (auth != null) ? (UserSecurity) auth.getPrincipal() : null;
            return userSecurity.getLoginUser();
        }
    }


}
