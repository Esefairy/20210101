package com.culture.controller.admin;


import com.culture.entity.Sentence;
import com.culture.query.SentenceQuery;
import com.culture.service.SentenceService;
import com.culture.util.AjaxResult;
import com.culture.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SentenceController {

    @Autowired
    private SentenceService sentenceService;

    @GetMapping("/sentence/index")
    public String sentenceList(){
        return "admin/sentence";
    }

    @GetMapping("/sentence/listpage")
    @ResponseBody
    public PageList listPage(SentenceQuery sentenceQuery) {
        return sentenceService.listpage(sentenceQuery);
    }


    //添加
    @PostMapping("/sentence/addSentence")
    @ResponseBody
    public AjaxResult addSentence(Sentence sentence) {
        try {
            sentenceService.addSentence(sentence);
            return new AjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxResult("修改失败");
    }

    //修改
    @PostMapping("/sentence/editSentence")
    @PreAuthorize("hasRole('管理员')")
    @ResponseBody
    public AjaxResult editSentence(Sentence sentence) {
        try {
            sentenceService.editSaveSentence(sentence);
            return new AjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxResult("修改失败");
    }

    //删除
    @GetMapping("/sentence/deleteSentence")
    @PreAuthorize("hasRole('管理员')")
    @ResponseBody
    public AjaxResult deleteSentence(@RequestParam(required = true) Long id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {

            sentenceService.deleteSentence(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult("删除失败");
        }
        return ajaxResult;
    }
}
