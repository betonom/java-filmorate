package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);

        if (!film.getLikes().contains(user.getId())) {
            film.getLikes().add(user.getId());
        }
    }

    public void deleteLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);

        if (film.getLikes().contains(user.getId())) {
            film.getLikes().remove(user.getId());
        }
    }

    public List<Film> getTopFilms(int count) {
        ArrayList<Film> films = filmStorage.getFilms();
        films.sort((Film film1, Film film2) -> {
            if (film1.getLikes().size() > film2.getLikes().size()) {
                return 1;
            } else if ((film1.getLikes().size() < film2.getLikes().size())) {
                return -1;
            } else {
                return 0;
            }
        });

        ArrayList<Film> topTenFilms = new ArrayList<>(count);

        if (films.size() < count) {
            topTenFilms.addAll(films);
        } else {
            topTenFilms.addAll(films.size() - count, films);
        }
        return topTenFilms;
    }
}
