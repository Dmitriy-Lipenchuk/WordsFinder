package application.controller;

import application.exceptions.NonCyrillicStringException;
import application.exceptions.WordAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(NonCyrillicStringException.class)
    public ResponseEntity<?> handleNonCyrillicStringException() {
        String message = "¬ведЄное слово не должно содержать ничего кроме кириллицы";
        return new ResponseEntity<>(List.of(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WordAlreadyExistException.class)
    public ResponseEntity<?> handleWordAlreadyExistsException() {
        String message = "—лово уже существует";
        return new ResponseEntity<>(List.of(message), HttpStatus.BAD_REQUEST);
    }
}
