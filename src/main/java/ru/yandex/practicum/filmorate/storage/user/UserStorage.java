package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Optional;

public interface UserStorage {
    ArrayList<User> getUsers();

    Optional<User> getUserById(Long id);

    User add(User user);

    User update(User newUser);
}
