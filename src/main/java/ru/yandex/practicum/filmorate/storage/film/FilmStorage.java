package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Optional;

public interface FilmStorage {
    ArrayList<Film> getFilms();

    Optional<Film> getFilmById(Long id);

    Film add(Film film);

    Film update(Film newFilm);
}
