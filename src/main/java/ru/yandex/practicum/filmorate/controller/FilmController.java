package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmController(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @GetMapping
    public Collection<Film> getAll() {
        log.info("Start handling request with GET method for /films");

        return filmStorage.getFilms();
    }

    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        log.info("Start handling request with POST method for /films");

        return filmStorage.add(film);
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        log.info("Start handling request with PUT method for /films");

        return filmStorage.update(newFilm);
    }
}
