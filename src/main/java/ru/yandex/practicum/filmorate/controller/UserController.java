package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
    public User update(@RequestBody User newUser) {
        log.info("Start handling request with PUT method for /users");

        if (newUser.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан", "id");
        }
        if (users.containsKey(newUser.getId())) {
            log.trace("Updating old user object's fields");
            User oldUser = users.get(newUser.getId());

            if (newUser.getEmail() != null)
                oldUser.setEmail(newUser.getEmail());

            if (newUser.getLogin() != null)
                oldUser.setLogin(newUser.getLogin());

            if (newUser.getName() != null)
                oldUser.setName(newUser.getName());

            if (newUser.getBirthday() != null)
                oldUser.setBirthday(newUser.getBirthday());

            return oldUser;
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден", "id");
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
