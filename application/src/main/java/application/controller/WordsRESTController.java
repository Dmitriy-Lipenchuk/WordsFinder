package application.controller;

import application.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WordsRESTController {
    private final WordsService wordsService;

    @Autowired
    public WordsRESTController(WordsService wordsService) {
        this.wordsService = wordsService;
    }

    @GetMapping(value = "/get-words-with-same-letters")
    public List<String> getWordsWithSameLetters(@RequestParam String word) {
     return wordsService.getWordsWithSameLetters(word);
    }
}
