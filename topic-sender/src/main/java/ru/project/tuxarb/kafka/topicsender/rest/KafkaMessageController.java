package ru.project.tuxarb.kafka.topicsender.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.project.tuxarb.kafka.topicsender.service.MessageSenderService;

import java.util.Map;

import static ru.project.tuxarb.kafka.common.util.CommonUtil.HEADER_NOT_FOUND_MSG;
import static ru.project.tuxarb.kafka.common.util.KafkaUtil.TOPIC_HEADER_NAME;

import static java.lang.String.format;

/**
 * Rest-контроллер между внешним клиентом и Kafka-брокером
 */
@RestController
@RequestMapping("/kafka")
public class KafkaMessageController {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageController.class);

    @Autowired
    private MessageSenderService messageSenderService;

    @PostMapping(value = "/message/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendMessage(@RequestBody String message, @RequestHeader HttpHeaders headers) {
        LOG.info("sendMessage: {}, headers: {}", message, headers);
        if (!headers.containsKey(TOPIC_HEADER_NAME)) {
            throw new IllegalArgumentException(format(HEADER_NOT_FOUND_MSG, TOPIC_HEADER_NAME));
        }
        messageSenderService.sendMessage(message, (Map) headers.toSingleValueMap());
    }
}
