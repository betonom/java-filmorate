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
@ControllerAdvice
public class ErrorHandlerControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse handleMethodArgumentsNotValidException(MethodArgumentNotValidException e) {
        log.warn(e.getMessage(), e);
        return new ValidationErrorResponse(
                e.getBindingResult().getFieldErrors().stream()
                        .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                        .collect(Collectors.toList())
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConditionsNotMetException.class)
    public ValidationErrorResponse handleConditionsNotMetException(ConditionsNotMetException e) {
        log.warn(e.getMessage(), e);
        return new ValidationErrorResponse(
                List.of(new ValidationError(e.getFieldName(), e.getMessage()))
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ValidationErrorResponse handleNotFoundException(NotFoundException e) {
        log.warn(e.getMessage(), e);
        return new ValidationErrorResponse(
                List.of(new ValidationError(e.getFieldName(), e.getMessage()))
        );
    }
}
