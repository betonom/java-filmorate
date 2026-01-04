package ru.yandex.practicum.filmorate.exception;

public class NotFoundException extends RuntimeException {
    private String fieldName;

    public NotFoundException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
