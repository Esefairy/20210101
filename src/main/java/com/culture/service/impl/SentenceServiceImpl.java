package com.culture.service.impl;

import com.culture.entity.Sentence;
import com.culture.mapper.SentenceMapper;
import com.culture.mapper.UserMapper;
import com.culture.query.SentenceQuery;
import com.culture.service.SentenceService;
import com.culture.util.CommonUtil;
import com.culture.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SentenceServiceImpl implements SentenceService {

    @Autowired
    private SentenceMapper sentenceMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public List<Sentence> findAll() {
        return sentenceMapper.findAll();
    }

    @Override
    public List<Sentence> queryAll() {
        return sentenceMapper.queryAll();
    }

    @Override
    public PageList listpage(SentenceQuery sentenceQuery) {
        PageList pageList = new PageList();

        Long total = sentenceMapper.queryTotal(sentenceQuery);
        List<Sentence> sentences = sentenceMapper.queryData(sentenceQuery);

        pageList.setTotal(total);
        pageList.setRows(sentences);
        return pageList;
    }

    @Override
    public void addSentence(Sentence sentence) {
        sentence.setCreateId(CommonUtil.getLoginUser().getId());
        Long uid = CommonUtil.getLoginUser().getId();
        sentence.setCreateTime(new Date());
        sentence.setCreateName(userMapper.findUserById(uid));
        sentence.setCreateImg(userMapper.findUserByImg(uid));
        sentenceMapper.addSentence(sentence);

    }

    @Override
    public void editSaveSentence(Sentence sentence) {
        sentenceMapper.editSaveSentence(sentence);
    }

    @Override
    public void deleteSentence(Long id) {
        sentenceMapper.deleteSentence(id);
    }
}
