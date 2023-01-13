package application.service;

import application.dao.WordsDao;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WordsService {
    final
    WordsDao wordsDao;

    public WordsService(WordsDao wordsDao) {
        this.wordsDao = wordsDao;
    }

    public List<String> getWordsWithSameLetters (@NotNull String word) {
        return wordsDao.getWordsWithSameLetters(word);
    }
}
