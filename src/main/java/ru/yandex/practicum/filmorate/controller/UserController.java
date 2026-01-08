package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserStorage userStorage;
    private final UserService userService;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getAll() {
        log.info("Start handling request with GET method for /users");

        return userStorage.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        log.info("Start handling request with GET method for /users/{id}");

        return userStorage.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    public List<Long> getFriends(@PathVariable Long id) {
        log.info("Start handling request with GET method for /users/{id}/friends");

        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<Long> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("Start handling request with GET method for /users/{id}/friends/common/{otherId}");

        return userService.getCommonFriends(id, otherId);
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

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<String> addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Start handling request with PUT method for /users/{id}/friends/{friendId}");

        userService.addFriend(id, friendId);

        return new ResponseEntity<>("Друг успешно добавлен!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<String> deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Start handling request with DELETE method for /users/{id}/friends/{friendId}");

        userService.deleteFriend(id, friendId);

        return new ResponseEntity<>("Друг успешно удален!", HttpStatus.OK);
    }
}
