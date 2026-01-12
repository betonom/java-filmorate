package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> getAll() {
        log.info("Start handling request with GET method for /films");

        return filmService.getFilms();
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Start handling request with GET method for /films/popular");

        return filmService.getTopFilms(count);
    }

    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        log.info("Start handling request with POST method for /films");

        return filmService.add(film);
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        log.info("Start handling request with PUT method for /films");

        return filmService.update(newFilm);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Start handling request with PUT method for /films/{id}/like/{userId}");

        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Start handling request with DELETE method for /films/{id}/like/{userId}");

        filmService.deleteLike(id, userId);
    }
}
