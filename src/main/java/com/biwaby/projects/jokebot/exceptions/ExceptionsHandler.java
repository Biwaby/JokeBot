package com.biwaby.projects.jokebot.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@Component
public class ExceptionsHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(UserAlreadyExistsException e) {
        return ResponseEntity.status(400).body(
                new ErrorResponse(
                        400L,
                        "Пользователь с данным именем уже существует"
                )
        );
    }

    @ExceptionHandler(IncorrectUserException.class)
    public ResponseEntity<ErrorResponse> handleException(IncorrectUserException e) {
        return ResponseEntity.status(400).body(
                new ErrorResponse(
                        400L,
                        "Введены некорректные данные пользователя"
                )
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException e) {
        return ResponseEntity.status(404).body(
                new ErrorResponse(
                        404L,
                        "Пользователь не найден"
                )
        );
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResponse(RoleNotFoundException e) {
        return ResponseEntity.status(404).body(
                new ErrorResponse(
                        404L,
                        "Роль не найдена"
                )
        );
    }

    @ExceptionHandler(UserDoesNotHaveRoleException.class)
    public ResponseEntity<ErrorResponse> handleResponse(UserDoesNotHaveRoleException e) {
        return ResponseEntity.status(400).body(
                new ErrorResponse(
                        400L,
                        "Пользователь не владеет данной ролью"
                )
        );
    }

    @ExceptionHandler(JokeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResponse(JokeNotFoundException e) {
        return ResponseEntity.status(404).body(
                new ErrorResponse(
                        404L,
                        "Шутка не найдена"
                )
        );
    }

    @ExceptionHandler(IncorrectJokeException.class)
    public ResponseEntity<ErrorResponse> handleResponse(IncorrectJokeException e) {
        return ResponseEntity.status(400).body(
                new ErrorResponse(
                        400L,
                        "Введена некорректная шутка (пустое или иное значение)"
                )
        );
    }
}
