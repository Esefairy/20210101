package com.culture.mapper;

import com.culture.entity.Culture;
import com.culture.entity.Like;
import com.culture.query.CultureQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface CultureMapper {

    //查询最新
    @Select("select * from cul_culture order by createTime desc limit 0,3")
    List<Culture> queryAll();

    //查询所有
    @Select("select * from cul_culture")
    List<Culture> findAll();

    //查询热门
    @Select("select * from cul_culture order by view desc limit 0,3")
    List<Culture> queryHotAll();

    //添加culture
    Integer addCulture(Culture culture);

    //根据culture id更新封面图
    void updateCultureFmUrl(Culture culture);

    //查询总的条数
    Long queryTotal(CultureQuery cultureQuery);

    //分页查询数据
    List<Culture> queryData(CultureQuery cultureQuery);

    //删除culture
    void deleteCulture(Long id);

    //修改culture
    void editSaveCulture(Culture culture);

    //查询culture详细信息
    @Select("select * from cul_culture where id=#{id}")
    Culture findDetailById(Long id);

    //更新浏览量
    @Update("update cul_culture set view=view+1 where id=#{id}")
    void updateViewNum(Long id);

    //查询用户喜欢的culture
    @Select("select * from cul_like where uid=#{uid}")
    List<Like> findUserLikeCulture(@Param("uid") Long uid);

    @Select("select * from cul_culture order by view desc limit 0,10")
    List<Culture> findTop4Culture();

    //通过id查询culture
    @Select("select * from cul_culture where id=#{id}")
    Culture findCultureById(Long id);

    //判断用户是否收藏culture
    @Select("select * from cul_like where uid=#{userid} and bid=#{bid}")
    Culture queryExistScCulture(Long userid, Long bid);

    //收藏culture
    @Insert("insert into cul_like (uid,bid) values(#{userid},#{bid})")
    void scCultureByUser(Long userid, Long bid);

    //查询用户收藏
    @Select("select * from cul_culture where id in(select bid from cul_like where uid=#{userid})")
    List<Culture> findMyScCulture(Long userid);

    //取消收藏
    @Delete("delete from cul_like where uid=#{userid} and bid=#{bid}")
    void deleteScCulture(Long userid, Long bid);

    //总浏览量
    @Select("select sum(view) from cul_culture")
    Long queryTotalViewNum();

    //总culture
    @Select("select count(*) from cul_culture")
    Long queryTotalCultureNum();

    //总用户
    @Select("select count(*) from cul_user")
    Long queryTotalUserRegNum();

    //总收藏
    @Select("select count(*) from cul_like")
    Long queryTotalScNum();

    @Select({
            "<script>" +
                    "select * from cul_culture where id in " +
                    "<foreach item='item' index ='index' collection = 'Ids' open='(' separator=',' close=')'>" +
                    "#{item}" +
                    "</foreach>"+
                    "</script>"})
    List<Culture> findAllByIds(@Param("Ids") List<Long> itemIds);
}
