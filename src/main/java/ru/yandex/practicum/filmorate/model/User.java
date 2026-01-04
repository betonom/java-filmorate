package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private Long id;

    @NotNull(message = "Имейл не может быть null")
    @Email(message = "Имейл не может быть пустой и должен содержать символ @")
    private String email;

    @NotBlank(message = "Логин не может быть пустой или null")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$",
            message = "Логин должен быть от 4 до 12 символов и не должен содержать пробелы")
    private String login;

    private String name;

    @Past(message = "День рождения не может быть в будущем")
    private LocalDate birthday;
}
