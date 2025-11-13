package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> getAll() {
        log.info("Start handling request with GET method for /films");

        return films.values();
    }

    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        log.info("Start handling request with POST method for /films");

        film.setId(getNextId());
        log.trace("Put film into hashMap films");
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        log.info("Start handling request with PUT method for /films");

        if (newFilm.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан", "id");
        }
        if (films.containsKey(newFilm.getId())) {
            log.trace("Updating old film object's fields");
            Film oldFilm = films.get(newFilm.getId());
            oldFilm.setName(newFilm.getName());
            oldFilm.setDescription(newFilm.getDescription());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            oldFilm.setDuration(newFilm.getDuration());
            return oldFilm;
        }
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден", "id");
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConditionsNotMetException.class,
            NotFoundException.class})
    public Map<String, String> handleMethodArgumentNotValidException(Exception ex) {
        Map<String, String> errors = new HashMap<>();

        if (ex.getClass().equals(MethodArgumentNotValidException.class)) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
            e.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            log.warn("MethodArgumentNotValidException on handle /films");
        }
        if (ex.getClass().equals(ConditionsNotMetException.class)) {
            ConditionsNotMetException e = (ConditionsNotMetException) ex;
            errors.put(e.getFieldName(), e.getMessage());
            log.warn("ConditionsNotMetException on handle /films");
        }
        if (ex.getClass().equals(NotFoundException.class)) {
            NotFoundException e = (NotFoundException) ex;
            errors.put(e.getFieldName(), e.getMessage());
            log.warn("NotFoundException on handle /films");
        }

        return errors;
    }


    private Long getNextId() {
        log.trace("Getting next id for object film");
        Long maxId = films.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++maxId;
    }
}
