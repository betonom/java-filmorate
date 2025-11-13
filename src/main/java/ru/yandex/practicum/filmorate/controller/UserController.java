package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getAll() {
        log.info("Start handling request with GET method for /users");

        return users.values();
    }

    @PostMapping
    public User add(@Valid @RequestBody User user) {
        log.info("Start handling request with POST method for /users");

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        log.trace("Put film into hashMap users");
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        log.info("Start handling request with PUT method for /users");

        if (newUser.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан", "id");
        }
        if (users.containsKey(newUser.getId())) {
            log.trace("Updating old user object's fields");
            User oldUser = users.get(newUser.getId());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setLogin(newUser.getLogin());
            if (newUser.getName() == null) {
                oldUser.setName(newUser.getLogin());
            } else {
                oldUser.setName(newUser.getName());
            }
            oldUser.setBirthday(newUser.getBirthday());
            return oldUser;
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден", "id");
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConditionsNotMetException.class,
            NotFoundException.class})
    public Map<String, String> handleMethodArgumentNotValidException(Exception ex) {
        Map<String, String> errors = new HashMap<>();

        if (ex.getClass().equals(MethodArgumentNotValidException.class)) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
            e.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            log.warn("MethodArgumentNotValidException on handle /users");
        }
        if (ex.getClass().equals(ConditionsNotMetException.class)) {
            ConditionsNotMetException e = (ConditionsNotMetException) ex;
            errors.put(e.getFieldName(), e.getMessage());
            log.warn("ConditionsNotMetException on handle /users");
        }
        if (ex.getClass().equals(NotFoundException.class)) {
            NotFoundException e = (NotFoundException) ex;
            errors.put(e.getFieldName(), e.getMessage());
            log.warn("NotFoundException on handle /users");
        }

        return errors;
    }

    private Long getNextId() {
        log.trace("Getting next id for object user");
        Long maxId = users.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++maxId;
    }
}
