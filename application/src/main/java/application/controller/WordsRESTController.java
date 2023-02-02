package application.controller;

import application.model.Word;
import application.service.WordsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WordsRESTController {
    private final WordsService wordsService;

    @Autowired
    public WordsRESTController(WordsService wordsService) {
        this.wordsService = wordsService;
    }

    @GetMapping(value = "/get-words-with-same-letters")
    public List<String> getWordsWithSameLetters(@RequestParam @NotNull String word) {
        return wordsService.getWordsWithSameLetters(word);
    }

    @PostMapping(value = "/suggest-word",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> suggestWord(@RequestBody @NotNull Word word) {
        return List.of(wordsService.suggestWord(word.getWord()));
    }
}
