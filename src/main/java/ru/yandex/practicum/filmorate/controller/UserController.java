package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserStorage userStorage;

    @Autowired
    public UserController(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @GetMapping
    public Collection<User> getAll() {
        log.info("Start handling request with GET method for /users");

        return userStorage.getUsers();
    }

    @PostMapping
    public User add(@Valid @RequestBody User user) {
        log.info("Start handling request with POST method for /users");

        return userStorage.add(user);
    }

    @PutMapping
    public User update(@RequestBody User newUser) {
        log.info("Start handling request with PUT method for /users");

        return userStorage.update(newUser);
    }
}
