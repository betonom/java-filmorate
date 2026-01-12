package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film add(Film film) {
        return filmStorage.add(film);
    }

    public Film update(Film newFilm) {
        return filmStorage.update(newFilm);
    }

    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId).orElseThrow(() -> {
            throw new NotFoundException("error", "Фильм с id " + filmId + "не найден");
        });
        User user = userStorage.getUserById(userId).orElseThrow(() -> {
            throw new NotFoundException("error", "Пользователь с id " + userId + "не найден");
        });

        if (!film.getLikes().contains(user.getId())) {
            film.getLikes().add(user.getId());
        }
    }

    public void deleteLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId).orElseThrow(() -> {
            throw new NotFoundException("error", "Пользователь с id " + userId + "не найден");
        });
        User user = userStorage.getUserById(userId).orElseThrow(() -> {
            throw new NotFoundException("error", "Пользователь с id " + userId + "не найден");
        });

        if (film.getLikes().contains(user.getId())) {
            film.getLikes().remove(user.getId());
        }
    }

    public List<Film> getTopFilms(int count) {
        return filmStorage.getFilms().stream()
                .sorted(Comparator.comparing(film -> film.getLikes().size(),
                        Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
