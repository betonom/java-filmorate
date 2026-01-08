package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public ArrayList<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(Long id) {
        return films.get(id);
    }

    @Override
    public Film add(Film film) {
        film.setId(getNextId());
        log.trace("Put film into hashMap films");
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан", "id");
        }
        if (films.containsKey(newFilm.getId())) {
            log.trace("Updating old film object's fields");
            Film oldFilm = films.get(newFilm.getId());
            if (newFilm.getName() != null)
                oldFilm.setName(newFilm.getName());

            if (newFilm.getDescription() != null)
                oldFilm.setDescription(newFilm.getDescription());

            if (newFilm.getReleaseDate() != null)
                oldFilm.setReleaseDate(newFilm.getReleaseDate());

            if (newFilm.getDuration() != null)
                oldFilm.setDuration(newFilm.getDuration());

            return oldFilm;
        }
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден", "id");
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
