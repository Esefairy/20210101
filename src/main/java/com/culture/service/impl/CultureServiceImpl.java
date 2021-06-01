package com.culture.service.impl;


import com.culture.entity.Culture;
import com.culture.entity.Like;
import com.culture.entity.User;
import com.culture.mapper.CultureMapper;
import com.culture.mapper.UserMapper;
import com.culture.query.CultureQuery;
import com.culture.service.CultureService;
import com.culture.service.RecommendService;
import com.culture.util.CommonUtil;
import com.culture.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CultureServiceImpl implements CultureService {

    @Autowired
    private CultureMapper cultureMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RecommendService recommendService;

    @Override
    public List<Culture> queryAll() {
        return cultureMapper.queryAll();
    }

    @Override
    public List<Culture> findAll() {
        return cultureMapper.findAll();
    }

    //添加culture
    @Override
    public Integer addCulture(Culture culture) {
        culture.setCreatorId(CommonUtil.getLoginUser().getId());
        culture.setView(0L);

        //culture新增
        culture.setCreateTime(new Date());

        return cultureMapper.addCulture(culture);
    }

    @Override
    public void updateCultureFmUrl(Culture culture) {
        cultureMapper.updateCultureFmUrl(culture);
    }

    @Override
    public PageList listpage(CultureQuery cultureQuery) {
        PageList pageList = new PageList();
        //查询总的条数
        Long total = cultureMapper.queryTotal(cultureQuery);
        List<Culture> cultures = cultureMapper.queryData(cultureQuery);
        pageList.setTotal(total);
        pageList.setRows(cultures);
        //分页查询的数据
        return pageList;
    }

    //删除culture
    @Override
    public void deleteCulture(Long id) {
        cultureMapper.deleteCulture(id);
    }

    //修改culture
    @Override
    public void editSaveCulture(Culture culture) {
        cultureMapper.editSaveCulture(culture);
    }

    //查询热门culture
    @Override
    public List<Culture> queryHotCulture() {
        return cultureMapper.queryHotAll();
    }

    //查找culture
    @Override
    public Culture findDetailById(Long id) {
        return cultureMapper.findDetailById(id);
    }

    //更新浏览量
    @Override
    public void updateViewNum(Long id) {
        cultureMapper.updateViewNum(id);
    }

    //获取用户推荐culture
    @Override
    public List<Culture> getUserTjCulture(String username, Long cultureId) {
        User user = userMapper.findUserByUserName(username);
        Long uid = user.getId();

        List<Culture> cultures = recommendService.getRecommendItemsByUser(uid, 4);
        if (cultures.size() < 4) {
            return getFullRecommendList(cultures, cultureId);
        }
        return cultures;
    }

    //判断是否已经收藏了culture
    @Override
    public boolean isExistScCulture(Long userid, Long bid) {
        Culture culture = cultureMapper.queryExistScCulture(userid, bid);
        if (culture != null) {
            return true;
        }
        return false;
    }

    //收藏culture
    @Override
    public void scCulture(Long userid, Long bid) {

//        like.setCreateTime(new Date());

        cultureMapper.scCultureByUser(userid, bid);
    }

    //我的收藏
    @Override
    public List<Culture> findMyScCulture(Long userid) {
        List<Culture> myscCulture = cultureMapper.findMyScCulture(userid);
        return myscCulture;
    }

    //取消收藏
    @Override
    public void cancelScCulture(Long userid, Long bid) {
        cultureMapper.deleteScCulture(userid, bid);
    }

    @Override
    public Long queryTotalViewNum() {
        return cultureMapper.queryTotalViewNum();
    }

    @Override
    public Long queryTotalCultureNum() {
        return cultureMapper.queryTotalCultureNum();
    }

    @Override
    public Long queryTotalUserRegNum() {
        return cultureMapper.queryTotalUserRegNum();
    }

    @Override
    public Long queryTotalScNum() {
        return cultureMapper.queryTotalScNum();
    }

    //推荐不满4个 补足4个
    public List<Culture> getFullRecommendList(List<Culture> tjList, Long cultureId) {
        List<Culture> top4Culture = cultureMapper.findTop4Culture();
        for (Culture culture : top4Culture) {
            if (isExistCulture(tjList, culture.getId()) && tjList.size() < 4 && culture.getId() != cultureId) {
                tjList.add(culture);
                System.out.println("补充:"+culture);
            }
        }
        return tjList;
    }

    //得到比较器
    public static TreeSet<Culture> getPreList() {
        return new TreeSet<Culture>(new Comparator<Culture>() {
            @Override
            public int compare(Culture o1, Culture o2) {
                if (o1.getW() != o2.getW()) {
                    return (int) (o1.getW() - o2.getW()) * 100;
                } else {
                    return Integer.valueOf((o1.getView() - o2.getView()) + "");
                }
            }
        });
    }

    //是否重复  false就是重复
    //cultures是推荐列表 bid 查询top 的id
    public static boolean isExistCulture(List<Culture> cultures, Long bid) {
        for (Culture culture : cultures) {
            if (culture.getId() == bid) {
                return false;
            }
        }
        return true;
    }
}
