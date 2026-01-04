package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class UserTest {
    private Validator validator;
    private Set<ConstraintViolation<User>> validates;
    private List<String> messages;
    private User user;

    @BeforeEach
    void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        user = new User();
    }

    @Test
    void validateUserNullFieldsTest() {
        getMessages(user);

        Assertions.assertTrue(validates.size() > 0, "Валидация на null проходит неверно");

        Assertions.assertTrue(
                messages.contains("Имейл не может быть null")
                        && messages.contains("Логин не может быть пустой или null"),
                "Валидация на null проходит неверно");
    }

    @Test
    void validateUserEmailFormatTest() {
        user.setEmail("email");

        getMessages(user);

        Assertions.assertTrue(validates.size() > 0, "Валидация на email format проходит неверно");

        Assertions.assertTrue(
                messages.contains("Имейл не может быть пустой и должен содержать символ @"),
                "Валидация на email format проходит неверно");
    }

    @Test
    void validateUserLoginFormatTest() {
        user.setLogin("l o g i n");

        getMessages(user);

        Assertions.assertTrue(validates.size() > 0, "Валидация на login format проходит неверно");

        Assertions.assertTrue(
                messages.contains("Логин должен быть от 4 до 12 символов и не должен содержать пробелы"),
                "Валидация на login format проходит неверно");

        ////////

        user.setLogin("ThereAreMoreThenTwentySymbols");

        getMessages(user);

        Assertions.assertTrue(validates.size() > 0, "Валидация на login format проходит неверно");

        Assertions.assertTrue(
                messages.contains("Логин должен быть от 4 до 12 символов и не должен содержать пробелы"),
                "Валидация на login format проходит неверно");
    }

    @Test
    void validateUserBirthDayPast() {
        user.setBirthday(LocalDate.of(2200, 1, 1));

        getMessages(user);

        Assertions.assertTrue(validates.size() > 0, "Валидация на birthday past проходит неверно");

        Assertions.assertTrue(
                messages.contains("День рождения не может быть в будущем"),
                "Валидация на birthday past проходит неверно");
    }

    void getMessages(User user) {
        validates = validator.validate(user);

        messages = validates.stream()
                .map(v -> v.getMessage())
                .collect(Collectors.toList());
    }
}