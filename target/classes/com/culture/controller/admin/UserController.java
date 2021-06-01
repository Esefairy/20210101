package com.culture.controller.admin;


import com.culture.entity.Role;
import com.culture.entity.User;
import com.culture.query.UserQuery;
import com.culture.service.RoleService;
import com.culture.service.UserService;
import com.culture.util.AjaxResult;
import com.culture.util.CommonUtil;
import com.culture.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @GetMapping("/user/index")
    @PreAuthorize("hasRole('管理员')")
    public String userList(String menuid,Model model){
        List<Role> roles = queryAllRole();
        model.addAttribute("roles",roles);
        model.addAttribute("menuid",menuid);
        //用户首页
        return "admin/user";
    }

    @GetMapping("/user/info")
    public String info(Model model){
        User loginUser = CommonUtil.getLoginUser();
        model.addAttribute("loginUser",loginUser);
        //用户个人
        return "admin/me";
    }

    @GetMapping("/user/listpage")
    @ResponseBody
    @PreAuthorize("hasRole('管理员')")
    public PageList listpage(UserQuery userQuery){
        return  userService.listpage(userQuery);
    }

    //添加用户
    @PostMapping("/user/addUser")
    @ResponseBody
    public int addUser(User user){
        userService.addUser(user);
        Integer userid = Integer.parseInt(user.getId()+"");
        //添加成功返回用户id
        return userid;
    }

    //修改用户editSaveUser
    @PostMapping("/user/editSaveUser")
    @PreAuthorize("hasRole('管理员')")
    @ResponseBody
    public AjaxResult editSaveUser(User user){
        System.out.println("修改用户...."+user);
        try {
            userService.editSaveUser(user);
            return new AjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxResult("修改失败");
    }

    //添加用户
    @GetMapping("/user/deleteUser")
    @PreAuthorize("hasRole('管理员')")
    @ResponseBody
    public AjaxResult deleteUser(@RequestParam(required = true) Long id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult("删除失败");
        }

        return ajaxResult;
    }

    @PostMapping(value="/user/deleteBatchUser")
    @PreAuthorize("hasRole('管理员')")
    @ResponseBody
    public AjaxResult deleteBatchUser(String ids){
        String[] idsArr = ids.split(",");
        List list = new ArrayList();
        for(int i=0;i<idsArr.length;i++){
            list.add(idsArr[i]);
        }
        try{
            userService.batchRemove(list);
            return new AjaxResult();
        }catch(Exception e){
           return new AjaxResult("批量删除失败");
        }
    }

    //查询所有角色
    public List<Role> queryAllRole(){
        return roleService.queryAll();
    }

    //添加用户的角色
    @PostMapping("/user/addUserRole")
    @ResponseBody
    public AjaxResult addUserRole(@RequestBody Map paramMap){
        AjaxResult ajaxResult = new AjaxResult();
        String userId = (String)paramMap.get("userId");
        List roleIds = (List) paramMap.get("roleIds");
        System.out.println(userId);
        System.out.println(roleIds);
        try {
            //添加用户对应的角色
            roleService.addUserRole(userId,roleIds);
            return ajaxResult;
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxResult("保存角色失败");
        }

    }


}
