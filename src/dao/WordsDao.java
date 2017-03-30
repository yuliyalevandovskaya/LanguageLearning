package dao;

import domain.Words;

import java.util.List;

public interface WordsDao {
    void createTable();
    void insertWords(Words words);
    List<Words> selectWords();
    void updateWords(Words words);
    void updateGermanCounter(Words words);
    void updateFrenchCounter(Words words);
    void deleteWords(String english);

}
