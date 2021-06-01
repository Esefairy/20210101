package com.culture.mapper;

import com.culture.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    //查询所有分类目录
    List<Category> queryAll();

    //添加分类
    @Insert("insert into cul_category(categoryName) values(#{categoryName})")
    void addSave(Category category);

    //修改分类
    @Update("update cul_category set categoryName=#{categoryName} where id=#{id}")
    void editSaveCategory(Category category);

    //删除分类
    @Delete("delete from cul_category where id=#{id}")
    void deleteCategory(Long id);
}
