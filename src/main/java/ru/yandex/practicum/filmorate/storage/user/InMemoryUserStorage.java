package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public ArrayList<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User add(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        log.trace("Put film into hashMap users");
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User newUser) {
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
