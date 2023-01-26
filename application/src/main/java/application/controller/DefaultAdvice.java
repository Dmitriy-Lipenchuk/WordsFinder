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
        String message = "\u0412\u0432\u0435\u0434\u0451\u043D\u043E\u0435 \u0441\u043B\u043E\u0432\u043E \u043D\u0435 \u0434\u043E\u043B\u0436\u043D\u043E \u0441\u043E\u0434\u0435\u0440\u0436\u0430\u0442\u044C \u043D\u0438\u0447\u0435\u0433\u043E \u043A\u0440\u043E\u043C\u0435 \u043A\u0438\u0440\u0438\u043B\u043B\u0438\u0446\u044B"; // "Введёное слово не должно содержать ничего кроме кириллицы"
        return new ResponseEntity<>(List.of(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({WordAlreadyExistException.class})
    public ResponseEntity<?> handleWordAlreadyExistsException() {
        String message = "\u0421\u043B\u043E\u0432\u043E \u0443\u0436\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442"; // "Слово уже существует"
        return new ResponseEntity<>(List.of(message), HttpStatus.BAD_REQUEST);
    }
}
