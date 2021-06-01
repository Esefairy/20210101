package com.culture.controller.admin;


import com.culture.entity.Culture;
import com.culture.query.CultureQuery;
import com.culture.service.CultureService;
import com.culture.service.CategoryService;
import com.culture.util.AjaxResult;
import com.culture.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CultureController {
    @Autowired
    private CultureService cultureService;

    @Autowired
    private CategoryService categoryService;

    //首页
    @GetMapping("/culture/index")
    public String cultureList(String menuid,Model model){

        model.addAttribute("menuid",menuid);
        model.addAttribute("categorys",categoryService.queryAll());

        return "admin/culture";
    }

    @GetMapping("/culture/add")
    public String addPage(String menuid,Model model){
        model.addAttribute("menuid",menuid);
        model.addAttribute("categorys",categoryService.queryAll());

        return "admin/cultureAdd";
    }

    @GetMapping("/culture/listpage")
    @ResponseBody
    public PageList listpage(CultureQuery cultureQuery){
        return  cultureService.listpage(cultureQuery);
    }

    //添加
    @PostMapping("/culture/add")
    @ResponseBody
    public int addCulture(Culture culture){
        cultureService.addCulture(culture);
        Integer cultureId = Integer.parseInt(culture.getId()+"");
        //添加成功返回id
        return cultureId;
    }

    //修改
    @PostMapping("/culture/edit")
//    @PreAuthorize("hasRole('管理员')")
    @ResponseBody
    public AjaxResult editSaveCulture(Culture culture){
        try {
            cultureService.editSaveCulture(culture);
            return new AjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxResult("修改失败");
    }

    //删除
    @GetMapping("/culture/delete")
    @PreAuthorize("hasRole('管理员')")
    @ResponseBody
    public AjaxResult deleteCulture(@RequestParam(required = true) Long id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            cultureService.deleteCulture(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult("删除失败");
        }

        return ajaxResult;
    }

}
