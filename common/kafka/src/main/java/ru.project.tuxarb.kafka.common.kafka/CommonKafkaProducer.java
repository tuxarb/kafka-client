package ru.project.tuxarb.kafka.common.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import ru.project.tuxarb.kafka.common.util.JsonUtil;
import ru.project.tuxarb.kafka.common.util.exception.KafkaException;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static ru.project.tuxarb.kafka.common.util.CommonUtil.convertMapValuesToString;

/**
 * Класс для отправки сообщений в Kafka.
 */
@Component
public class CommonKafkaProducer {

    private static final Logger LOG = LoggerFactory.getLogger(CommonKafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, Object> jsonKafkaTemplate;

    /**
     * Метод отправки сообщения в топик Kafka.
     *
     * @param data    тело сообщения
     * @param headers мапа с заголовками
     * @param topic   имя топика
     * @param <T>     тип данных
     */
    public <T> void sendMessage(T data, Map<String, Object> headers, String topic) {
        LOG.info("Sending the message with data -> {} and headers -> {} to the kafka topic={}", JsonUtil.toJson(data), convertMapValuesToString(headers), topic);
        Message<T> message = MessageBuilder.withPayload(data)
                .copyHeaders(headers)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
        ListenableFuture<SendResult<String, Object>> listenableFuture = jsonKafkaTemplate.send(message);
        try {
            listenableFuture.get(60, TimeUnit.SECONDS); // выставим максимальное время ожидания результата в 60 секунд на всякий случай
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new KafkaException(e.getMessage(), e);
        }
    }
}
