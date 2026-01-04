package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validation.validator.LocalDateValidator;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocalDateValidator.class)
public @interface ValidateLocalDate {
    String message() default "Дата релиза не может быть раньше 28 декабря 1895 года или null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
