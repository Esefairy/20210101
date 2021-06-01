package com.culture.mapper;

import com.culture.entity.Sentence;
import com.culture.query.SentenceQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SentenceMapper extends BaseMapper<Sentence>{

    List<Sentence> findAll();

    @Select("select * from cul_sentence order by createTime desc limit 0,4")
    List<Sentence> queryAll();

    List<Sentence> queryData(SentenceQuery sentenceQuery);

    @Insert("insert into cul_sentence(content,createId,createName,createTime) values (#{content},#{createId},#{createName},#{createTime})")
    void addSentence(Sentence sentence);

    @Update("update cul_sentence set content=#{content} where id=#{id}")
    void editSaveSentence(Sentence sentence);

    @Delete("delete from cul_sentence where id=#{id}")
    void deleteSentence(Long id);

}
