 package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
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

    public List<Long> getFriends(Long userMainId) {
        User userMain = userStorage.getUserById(userMainId);

        if (userMain == null) {
            throw new NotFoundException("error", "Пользователь с id " + userMainId + "не найден");
        }

        return new ArrayList<>(userMain.getFriends());
    }

    public void addFriend(Long userMainId, Long userFriendId) {
        User userMain =  userStorage.getUserById(userMainId);
        User userFriend =  userStorage.getUserById(userFriendId);

        if (userMain == null) {
            throw new NotFoundException("error", "Пользователь с id " + userMainId + "не найден");
        }

        if (userFriend == null) {
            throw new NotFoundException("error", "Пользователь с id " + userFriendId + "не найден");
        }

        if (userMain.getFriends().contains(userFriendId) || userFriend.getFriends().contains(userMainId)) {
            return;
        }

        userMain.getFriends().add(userFriend.getId());

        userFriend.getFriends().add(userMain.getId());
    }

    public void deleteFriend(Long userMainId, Long userFriendId) {
        User userMain =  userStorage.getUserById(userMainId);
        User userFriend =  userStorage.getUserById(userFriendId);

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

    public List<Long> getCommonFriends(Long userMainId, Long userFriendId) {
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
                .collect(Collectors.toList());
    }
}
