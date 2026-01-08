package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ValidateLocalDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Data
public class Film {
    private Long id;

    @NotBlank(message = "Название не может быть пустым или null")
    private String name;

    @NotBlank(message = "Описание не может быть пустым или null")
    @Size(max = 200, message = "Описание должно быть не более 200 символов")
    private String description;

    @Past(message = "Дата не может быть в будущем")
    @ValidateLocalDate//Здесь проходит проверка на null
    private LocalDate releaseDate;

    @NotNull(message = "Продолжительность не может быть null")
    @Min(value = 0, message = "Продолжительность должна быть положительной")
    private Integer duration;

    //Здесь хранятся id тех, кто поставил лайк
    private Set<Long> likes = new HashSet<>();
}
