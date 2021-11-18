package ru.project.tuxarb.kafka.common.util.exception;

/**
 * Ошибка, выстреливающая при обработке некорректного json.
 */
public class JsonException extends RuntimeException {

    public JsonException() {
    }

    public JsonException(String message) {
        super(message);
    }

    public JsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonException(Throwable cause) {
        super(cause);
    }
}
