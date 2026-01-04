package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserStorage {
    ArrayList<User> getUsers();

    User add(User user);

    User update(User newUser);
}
