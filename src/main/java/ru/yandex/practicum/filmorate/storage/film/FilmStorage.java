package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {
    ArrayList<Film> getFilms();

    Film add(Film film);

    Film update(Film newFilm);
}
