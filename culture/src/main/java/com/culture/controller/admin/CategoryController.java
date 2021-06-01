package com.culture.controller.admin;


import com.culture.entity.Category;
import com.culture.query.CategoryQuery;
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
public class CategoryController {


    @Autowired
    private CategoryService categoryService;


    @GetMapping("/category/index")
    public String categoryList(String menuid,Model model){

        model.addAttribute("menuid",menuid);

        return "admin/category";
    }

    @GetMapping("/category/listpage")
    @ResponseBody
    public PageList listpage(CategoryQuery categoryQuery){
        return  categoryService.listpage(categoryQuery);
    }

    //添加分类
    @PostMapping("/category/addCategory")
    @ResponseBody
    public AjaxResult addCategory(Category category){
        try {
            categoryService.addSave(category);
            return new AjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxResult("修改失败");

    }

    //修改
    @PostMapping("/category/editSaveCategory")
    @PreAuthorize("hasRole('管理员')")
    @ResponseBody
    public AjaxResult editSaveUser(Category category){
        try {
            categoryService.editSaveCategory(category);
            return new AjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxResult("修改失败");
    }

    //删除分类
    @GetMapping("/category/deleteCategory")
    @PreAuthorize("hasRole('管理员')")
    @ResponseBody
    public AjaxResult deleteCategory(@RequestParam(required = true) Long id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            categoryService.deleteCategory(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult("删除失败");
        }

        return ajaxResult;
    }


}
