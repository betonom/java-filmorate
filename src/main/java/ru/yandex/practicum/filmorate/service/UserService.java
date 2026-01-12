package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    public List<User> getFriends(Long userMainId) {
        User userMain = userStorage.getUserById(userMainId);

        if (userMain == null) {
            throw new NotFoundException("error", "Пользователь с id " + userMainId + "не найден");
        }

        return userMain.getFriends().stream()
                .map(friendId -> userStorage.getUserById(friendId))
                .collect(Collectors.toList());
    }

    public User add(User user) {
        return userStorage.add(user);
    }

    public User update(User newUser) {
        return userStorage.update(newUser);
    }

    public void addFriend(Long userMainId, Long userFriendId) {
        User userMain = userStorage.getUserById(userMainId);
        User userFriend = userStorage.getUserById(userFriendId);

        if (userMain == null) {
            throw new NotFoundException("error", "Пользователь с id " + userMainId + "не найден");
        }

        if (userFriend == null) {
            throw new NotFoundException("error", "Пользователь с id " + userFriendId + "не найден");
        }

        userMain.getFriends().add(userFriendId);

        userFriend.getFriends().add(userMainId);
    }

    public void deleteFriend(Long userMainId, Long userFriendId) {
        User userMain = userStorage.getUserById(userMainId);
        User userFriend = userStorage.getUserById(userFriendId);

        if (userMain == null) {
            throw new NotFoundException("error", "Пользователь с id " + userMainId + "не найден");
        }

        if (userFriend == null) {
            throw new NotFoundException("error", "Пользователь с id " + userFriendId + "не найден");
        }

        Set<Long> mainFriends = userMain.getFriends();
        Set<Long> friendFriends = userFriend.getFriends();
        if (mainFriends.contains(userFriendId)) {
            mainFriends.remove(userFriendId);
        }
        if (friendFriends.contains(userMainId)) {
            friendFriends.remove(userMainId);
        }
    }

    public List<User> getCommonFriends(Long userMainId, Long userFriendId) {
        User userMain = userStorage.getUserById(userMainId);
        User userFriend = userStorage.getUserById(userFriendId);

        if (userMain == null) {
            throw new NotFoundException("error", "Пользователь с id " + userMainId + "не найден");
        }

        if (userFriend == null) {
            throw new NotFoundException("error", "Пользователь с id " + userFriendId + "не найден");
        }

        return userMain.getFriends().stream()
                .filter(userId -> userFriend.getFriends().contains(userId))
                .map(userId -> userStorage.getUserById(userId))
                .collect(Collectors.toList());
    }
}
