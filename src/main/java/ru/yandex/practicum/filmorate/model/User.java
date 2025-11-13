package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ValidateLocalDate;

import java.time.LocalDate;

@Data
public class User {
    private Long id;

    @NotNull(message = "Имейл не может быть null")
    @Email(message = "Имейл не может быть пустой и должен содержать символ @")
    private String email;

    @NotBlank(message = "Логин не может быть пустой или null")
    @Pattern(regexp = "^[^0-9][a-zA-Z0-9]{3,11}$",
            message = "Логин должен быть от 4 до 12 символов, не должен содержать пробелы и начинаться с цифры")
    private String login;

    private String name;

    @Past(message = "День рождения не может быть в будущем")
    private LocalDate birthday;
}
