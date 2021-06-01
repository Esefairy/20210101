package com.culture.controller.index;

import com.culture.entity.Culture;
import com.culture.query.CultureQuery;
import com.culture.service.CultureService;
import com.culture.service.CategoryService;
import com.culture.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
public class CultureIndexController {

    @Autowired
    private CultureService cultureService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/culture")
    public String cultureSearch(Model model) {

        //查询分类
        model.addAttribute("categorys", categoryService.queryAll());

        return "index/culture";
    }

    @RequestMapping("/cultureIndex/listpage")
    @ResponseBody
    public PageList listpage(CultureQuery cultureQuery) {
        cultureQuery.setOffset((cultureQuery.getPage() - 1) * cultureQuery.getPageSize());
        return cultureService.listpage(cultureQuery);
    }

    @RequestMapping("/culture/detail")
    public String detailPage(HttpSession httpSession, Long id, Model model) {
        //查找
        Culture culture = cultureService.findDetailById(id);
        //更新浏览量
        cultureService.updateViewNum(id);

        model.addAttribute("culture", culture);
        //系统推荐书籍
        String username = (String) httpSession.getAttribute("loginUserName");
        if (username != null) {
            List<Culture> cultureList = cultureService.getUserTjCulture(username, id);
            model.addAttribute("recommendCultures", cultureList);
        }

        return "index/detail";
    }

}
