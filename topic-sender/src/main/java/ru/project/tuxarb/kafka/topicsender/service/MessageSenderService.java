package ru.project.tuxarb.kafka.topicsender.service;

import java.util.Map;

/**
 * Интерфейс для отправки сообщений куда-нибудь.
 */
public interface MessageSenderService {

    /**
     * Метод отправки сообщения.
     *
     * @param payload тело сообщения (не может быть {@code null}
     * @param headers заголовки сообщения
     * @param <T>     тип тела сообщения
     */
    <T> void sendMessage(T payload, Map<String, Object> headers);
}
