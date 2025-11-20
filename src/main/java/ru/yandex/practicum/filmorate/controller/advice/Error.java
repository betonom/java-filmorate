package ru.yandex.practicum.filmorate.controller.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Error {

    private final String fieldName;
    private final String message;

}
