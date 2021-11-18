package ru.project.tuxarb.kafka.topicsender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.project.tuxarb.kafka.common.util.JsonUtil;

import javax.annotation.PostConstruct;

/**
 * Класс-контейнер для хранения внешних атрибутов модуля.
 */
@Component
public class ExternalPropertiesContainer {

    private static final Logger LOG = LoggerFactory.getLogger(ExternalPropertiesContainer.class);

    @Value("${kafka_servers}")
    private String kafkaServers;
    @Value("${kafka_max_block_ms}")
    private String kafkaMaxBlockMs;

    @PostConstruct
    private void init() {
        LOG.info("initialized with the properties: {}", JsonUtil.toJson(this));
    }

    public String getKafkaServers() {
        return kafkaServers;
    }

    public void setKafkaServers(String kafkaServers) {
        this.kafkaServers = kafkaServers;
    }

    public String getKafkaMaxBlockMs() {
        return kafkaMaxBlockMs;
    }

    public void setKafkaMaxBlockMs(String kafkaMaxBlockMs) {
        this.kafkaMaxBlockMs = kafkaMaxBlockMs;
    }
}
