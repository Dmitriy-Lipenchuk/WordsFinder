package application.service;

import application.dao.SuggestedWordsDao;
import application.dao.WordsDao;
import application.exceptions.WordAlreadyExistException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WordsService {
    private final WordsDao wordsDao;
    private final SuggestedWordsDao suggestedWordsDao;

    public WordsService(WordsDao wordsDao, SuggestedWordsDao suggestedWordsDao) {
        this.wordsDao = wordsDao;
        this.suggestedWordsDao = suggestedWordsDao;
    }

    public List<String> getWordsWithSameLetters(@NotNull String word) {
        return wordsDao.getWordsWithSameLetters(word);
    }

    public String suggestWord(@NotNull String word) {
        if (wordsDao.checkIfWordExists(word)) {
            throw new WordAlreadyExistException();
        }

        suggestedWordsDao.create(word);
        return "\u0421\u043B\u043E\u0432\u043E \u043F\u0440\u0435\u0434\u043B\u043E\u0436\u0435\u043D\u043E"; // "Слово предложено"
    }
}
