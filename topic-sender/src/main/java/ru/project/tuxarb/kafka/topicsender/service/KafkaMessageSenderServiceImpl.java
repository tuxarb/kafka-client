package ru.project.tuxarb.kafka.topicsender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.project.tuxarb.kafka.common.kafka.CommonKafkaProducer;

import java.util.Map;

import static ru.project.tuxarb.kafka.common.util.KafkaUtil.TOPIC_HEADER_NAME;

import static java.util.Objects.requireNonNull;

/**
 * Реализация интерфейса для отправки сообщений в топик Kafka.
 */
@Service
public class KafkaMessageSenderServiceImpl implements MessageSenderService {

    @Autowired
    private CommonKafkaProducer commonKafkaProducer;

    @Override
    public <T> void sendMessage(T payload, Map<String, Object> headers) {
        requireNonNull(payload, "message body is null!");
        String topicName = (String) headers.get(TOPIC_HEADER_NAME);
        requireNonNull(topicName, "topic is null!");

        commonKafkaProducer.sendMessage(payload, headers, topicName);
    }
}
