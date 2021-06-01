package com.culture.service;



import com.culture.entity.Culture;
import com.culture.entity.Like;
import com.culture.query.CultureQuery;
import com.culture.util.PageList;
import com.culture.entity.Culture;

import java.util.List;


public interface CultureService {

    //查询所有culture
    List<Culture> queryAll();

    List<Culture> findAll();

    //添加culture
    Integer addCulture(Culture culture);

    //根据id更新封面图
    void updateCultureFmUrl(Culture culture);

    public PageList listpage(CultureQuery cultureQuery);

    void deleteCulture(Long id);

    void editSaveCulture(Culture culture);

    //查询热门
    List<Culture> queryHotCulture();

    Culture findDetailById(Long id);

    //更新浏览量
    void updateViewNum(Long id);

    //获取用户推荐
    List<Culture> getUserTjCulture(String username,Long cultureId);

    boolean isExistScCulture(Long userid, Long bid);

    void scCulture(Long userid, Long bid);


    List<Culture> findMyScCulture(Long id);

    void cancelScCulture(Long id, Long id1);

    Long queryTotalViewNum();

    Long queryTotalCultureNum();

    Long queryTotalUserRegNum();

    Long queryTotalScNum();
}
