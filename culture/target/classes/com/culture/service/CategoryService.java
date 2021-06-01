package com.culture.service;


import com.culture.entity.Category;
import com.culture.query.CategoryQuery;
import com.culture.util.PageList;

import java.util.List;


public interface CategoryService {

    //查询所有分类
    List<Category> queryAll();

    //分页查询方法
    PageList listpage(CategoryQuery categoryQuery);

    void addSave(Category category);

    void editSaveCategory(Category category);

    void deleteCategory(Long id);
}
