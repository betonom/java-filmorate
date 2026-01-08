 package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

        return new ArrayList<>(userMain.getFriends());
    }

    public void addFriend(Long userMainId, Long userFriendId) {
        User userMain =  userStorage.getUserById(userMainId);
        User userFriend =  userStorage.getUserById(userFriendId);

        userMain.getFriends().add(userFriend.getId());

        userFriend.getFriends().add(userMain.getId());
    }

    public void deleteFriend(Long userMainId, Long userFriendId) {
        User userMain =  userStorage.getUserById(userMainId);
        User userFriend =  userStorage.getUserById(userFriendId);

        Set<Long> friends = userMain.getFriends();
        if (friends.contains(userFriend.getId())) {
            friends.remove(userFriend.getId());
        }
    }

    public List<Long> getCommonFriends(Long userMainId, Long userFriendId) {
        User userMain = userStorage.getUserById(userMainId);
        User userFriend = userStorage.getUserById(userFriendId);

        return userMain.getFriends().stream()
                .filter(userId -> userFriend.getFriends().contains(userId))
                .collect(Collectors.toList());
    }
}
