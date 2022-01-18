package ru.project.tuxarb.kafka.topicsender;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static ru.project.tuxarb.kafka.common.util.JsonUtil.toJson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = KafkaMessageControllerTest.class)
@Ignore
public class KafkaMessageControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageControllerTest.class);

    private static final String SEND_TO_KAFKA_TOPIC_HEADER_NAME = "topic";

    private static final String SEND_TO_KAFKA_TEST_MESSAGE_1 = "{\"key\":\"value\"}";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void callSendMessage() {
        HttpHeaders headers = new HttpHeaders();
        // обязательные хедеры для вызова сервиса
        headers.set(SEND_TO_KAFKA_TOPIC_HEADER_NAME, "main_router_topic");
        headers.setContentType(MediaType.APPLICATION_JSON);
        // доп. хедеры уже для клиента топика, указанного выше
        headers.set("sender_code", "SENDER");
        headers.set("receiver_code", "DEFAULT_RECEIVER");
        headers.set("command", "DEFAULT_RESPONSE_COMMAND");

        HttpEntity<String> entity = new HttpEntity<>(SEND_TO_KAFKA_TEST_MESSAGE_1, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:1111/topicSenderApp/kafka/message/send",
                HttpMethod.POST,
                entity,
                Void.class);
        LOG.info("response: {}", toJson(response.getBody()));
    }

    @Test
    public void callSendMessageWithCount() {
        int duplicateCount = 1; // сколько раз в цикле вызывать сервис отправки сообщения в кафку
        for (int i = 0; i < duplicateCount; i++) {
            callSendMessage();
        }
        LOG.info("All {} messages were sent!", duplicateCount);
    }
}
