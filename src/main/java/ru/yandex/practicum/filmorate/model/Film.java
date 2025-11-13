package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.time.DurationMin;
import ru.yandex.practicum.filmorate.validation.ValidateLocalDate;

import java.time.Duration;
import java.time.LocalDate;

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

    @NotNull(message = "Дата не может быть null")
    @Past(message = "Дата не может быть в будущем")
    @ValidateLocalDate
    private LocalDate releaseDate;

    @NotNull(message = "Продолжительность не может быть null")
    @DurationMin(nanos = 1, message = "Продолжительность должна быть положительной")
    private Duration duration;
}
