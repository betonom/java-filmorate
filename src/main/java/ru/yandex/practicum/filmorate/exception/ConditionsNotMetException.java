package ru.yandex.practicum.filmorate.exception;

public class ConditionsNotMetException extends RuntimeException {
    private String fieldName;

    public ConditionsNotMetException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
