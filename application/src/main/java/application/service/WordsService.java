package application.service;

import application.dao.SuggestedWordsDao;
import application.dao.WordsDao;
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
            return "\u0422\u0430\u043A\u043E\u0435 \u0441\u043B\u043E\u0432\u043E \u0443\u0436\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442"; // "Такое слово уже существует"
        }

        suggestedWordsDao.create(word);
        return "\u0421\u043B\u043E\u0432\u043E \u043F\u0440\u0435\u0434\u043B\u043E\u0436\u0435\u043D\u043E"; // "Слово предложено"
    }
}
