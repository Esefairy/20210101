package com.culture.service;

import com.culture.entity.Sentence;
import com.culture.query.SentenceQuery;
import com.culture.util.PageList;


import java.util.List;

public interface SentenceService {

    List<Sentence> findAll();

    List<Sentence> queryAll();

    PageList listpage(SentenceQuery sentenceQuery);

    void addSentence(Sentence sentence);

    void editSaveSentence(Sentence sentence);

    void deleteSentence(Long id);
}
