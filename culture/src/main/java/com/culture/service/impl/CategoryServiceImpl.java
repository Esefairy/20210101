package com.culture.service.impl;


import com.culture.entity.Category;
import com.culture.mapper.CategoryMapper;
import com.culture.query.CategoryQuery;
import com.culture.service.CategoryService;
import com.culture.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> queryAll() {
        return categoryMapper.queryAll();
    }

    @Override
    public PageList listpage(CategoryQuery categoryQuery) {
        PageList pageList = new PageList();
        //查询总的条数
        Long total = categoryMapper.queryTotal(categoryQuery);
        List<Category> categories = categoryMapper.queryData(categoryQuery);
        pageList.setTotal(total);
        pageList.setRows(categories);
        //分页查询的数据
        return pageList;
    }

    @Override
    public void addSave(Category category) {
        categoryMapper.addSave(category);
    }

    @Override
    public void editSaveCategory(Category category) {
        categoryMapper.editSaveCategory(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryMapper.deleteCategory(id);
    }
}
