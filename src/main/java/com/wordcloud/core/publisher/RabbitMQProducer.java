package com.wordcloud.core.publisher;

import com.wordcloud.core.dto.UploadDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(UploadDto uploadDto) {
        LOGGER.info(String.format("Message sent -> Token: %s MinCount: %s",
                uploadDto.getUserToken(),
                uploadDto.getMinimumCount()));
        rabbitTemplate.convertAndSend(exchangeName, routingKey, uploadDto);
    }
}
