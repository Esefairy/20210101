package com.culture.controller.admin;


import com.culture.entity.Culture;
import com.culture.entity.User;
import com.culture.service.CultureService;
import com.culture.service.UserService;
import com.culture.util.AjaxResult;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileUpload {

    @Autowired
    private UserService userService;

    @Autowired
    private CultureService cultureService;

    @Value("${avatar.upload.path}")
    private String uploadPath;

    @Value("${culture.upload.path}")
    private String cultureFmPath;

    //上传头像
    @RequestMapping(value = "/file/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult upload(HttpServletRequest req, Integer id, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return new AjaxResult("文件为空");
            }
            //获取文件名
            String fileName = file.getOriginalFilename();
            //获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            String uuidString = UUID.randomUUID().toString();
            //新文件名
            String newFileName = uuidString + suffixName;


            File path = new File(uploadPath);
            //检测是否存在目录
            if (!path.exists()) path.mkdirs();

            File savefile = new File(path, newFileName);
            if (!savefile.getParentFile().exists()) savefile.getParentFile().mkdirs();
            //保存图片
            file.transferTo(savefile);

            //更新用户表的头像
            User user = new User();
            user.setId(Long.parseLong(id + ""));
            user.setHeadImg(newFileName);
            userService.updateUserHeadImg(user);

            return new AjaxResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //显示头像
    @RequestMapping(value = "/showimage/{image_name}")
    public String showphoto(@PathVariable("image_name") String image_name, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        // 获得的系统的根目录
        File fileParent = new File(File.separator);
        // 获得/usr/CBeann目录
        System.out.println("读取头像:" + image_name);
        File file = null;
        //os.name操作系统的名称
        String os = System.getProperty("os.name");
        //①PrintWriter out=response.getWriter()
        //out对象用于处理字符流数据。
        //②ServletOutputStream out=response.getOutputStream();
        //os用于输出字符流数据或者二进制的字节流数据都可以。
        ServletOutputStream out = response.getOutputStream();
        try {
            if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
                file = new File(uploadPath + "\\" + image_name);
            } else {  //linux 和mac
                file = new File(fileParent, uploadPath.substring(1) + "/" + image_name);
            }
            //IOUtils快速进行内容复制
            IOUtils.copy(new FileInputStream(file), out);
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }


    //上传封面
    @RequestMapping(value = "/file/uploadCultureFmFile", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult uploadCultureFmFile(HttpServletRequest req, Integer id, @RequestParam("file") MultipartFile file) {
        System.out.println("上传文件封面...." + id);
        try {
            if (file.isEmpty()) {
                return new AjaxResult("文件为空");
            }
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String uuidString = UUID.randomUUID().toString();
            String newFileName = uuidString + suffixName;

            File path = new File(cultureFmPath);
            if (!path.exists()) path.mkdirs();

            File savefile = new File(path, newFileName);
            if (!savefile.getParentFile().exists()) savefile.getParentFile().mkdirs();
            file.transferTo(savefile);

            //更新封面图片
            Culture culture = new Culture();
            culture.setId(Long.parseLong(id + ""));
            culture.setFmUrl(newFileName);
            cultureService.updateCultureFmUrl(culture);

            return new AjaxResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //显示封面
    @RequestMapping(value = "/showFmImg/{image_name}")
    public String showFmImg(@PathVariable("image_name") String image_name, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        // 获得的系统的根目录
        File fileParent = new File(File.separator);
        // 获得/usr/CBeann目录
        System.out.println("读取封面:" + image_name);
        File file = null;
        String os = System.getProperty("os.name");
        ServletOutputStream out = response.getOutputStream();
        try {
            if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
                file = new File(cultureFmPath + "\\" + image_name);
            } else {  //linux 和mac
                file = new File(fileParent, cultureFmPath.substring(1) + "/" + image_name);
            }
            IOUtils.copy(new FileInputStream(file), out);
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }


}
