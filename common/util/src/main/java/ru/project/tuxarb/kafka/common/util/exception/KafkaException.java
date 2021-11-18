package ru.project.tuxarb.kafka.common.util.exception;

/**
 * Ошибка, выстреливающая в случае ошибок Kafka.
 */
public class KafkaException extends RuntimeException {

    public KafkaException() {
        super();
    }

    public KafkaException(String message) {
        super(message);
    }

    public KafkaException(String message, Throwable cause) {
        super(message, cause);
    }

    public KafkaException(Throwable cause) {
        super(cause);
    }
}
