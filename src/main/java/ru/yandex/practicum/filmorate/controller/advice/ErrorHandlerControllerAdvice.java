package ru.yandex.practicum.filmorate.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandlerControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentsNotValidException(MethodArgumentNotValidException e) {
        log.warn(e.getMessage(), e);
        return new ErrorResponse(
                e.getBindingResult().getFieldErrors().stream()
                        .map(error -> new Error(error.getField(), error.getDefaultMessage()))
                        .collect(Collectors.toList())
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConditionsNotMetException.class)
    public ErrorResponse handleConditionsNotMetException(ConditionsNotMetException e) {
        log.warn(e.getMessage(), e);
        return new ErrorResponse(
                List.of(new Error(e.getFieldName(), e.getMessage()))
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        log.warn(e.getMessage(), e);
        return new ErrorResponse(
                List.of(new Error(e.getFieldName(), e.getMessage()))
        );
    }
}
