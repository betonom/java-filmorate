package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class FilmTest {

    private Validator validator;
    private Set<ConstraintViolation<Film>> validates;
    private List<String> messages;
    private Film film;

    @BeforeEach
    void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        film = new Film();
    }

    @Test
    void validateFilmNullTest() {
        getMessages(film);

        Assertions.assertTrue(validates.size() > 0, "Валидация на null проходит неверно");

        Assertions.assertTrue(
                messages.contains("Название не может быть пустым или null")
                        && messages.contains("Описание не может быть пустым или null")
                        && messages.contains("Дата релиза не может быть раньше 28 декабря 1895 года или null")
                        && messages.contains("Продолжительность не может быть null"),
                "Валидация на null проходит неверно");
    }

    @Test
    void validateFilmDescMoreThan200Test() {
        film.setName("name");
        film.setDescription("a".repeat(201));
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(1);

        getMessages(film);

        Assertions.assertTrue(validates.size() > 0,
                "Валидация на description < 200 проходит неверно");

        Assertions.assertTrue(messages.contains("Описание должно быть не более 200 символов"),
                "Валидация на description < 200 проходит неверно");
    }

    @Test
    void validateFilmReleaseDateTest() {
        film.setReleaseDate(LocalDate.of(1895, Month.DECEMBER, 27));

        getMessages(film);

        Assertions.assertTrue(validates.size() > 0,
                "Валидация на releaseDate isAfter 1895-12-28 проходит неверно");

        Assertions.assertTrue(messages.contains("Дата релиза не может быть раньше 28 декабря 1895 года или null"),
                "Валидация на releaseDate isAfter 1895-12-28 проходит неверно");

        ////////

        film.setDescription("desc");
        film.setReleaseDate(LocalDate.of(2200, Month.DECEMBER, 27));

        getMessages(film);

        Assertions.assertTrue(validates.size() > 0,
                "Валидация на releaseDate is past проходит неверно");

        Assertions.assertTrue(messages.contains("Дата не может быть в будущем"),
                "Валидация на releaseDate is past проходит неверно");
    }

    @Test
    void validateFilmDurationIsPositiveTest() {
        film.setReleaseDate(LocalDate.of(2000, Month.DECEMBER, 27));
        film.setDuration(-11);

        getMessages(film);

        Assertions.assertTrue(validates.size() > 0,
                "Валидация на duration is positive проходит неверно");

        Assertions.assertTrue(messages.contains("Продолжительность должна быть положительной"),
                "Валидация на duration is positive проходит неверно");
    }

    void getMessages(Film film) {
        validates = validator.validate(film);

        messages = validates.stream()
                .map(v -> v.getMessage())
                .collect(Collectors.toList());
    }
}