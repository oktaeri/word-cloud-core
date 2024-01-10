package com.wordcloud.core.controller;

import com.wordcloud.core.dto.TestDto;
import com.wordcloud.core.publisher.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MessageController {
    private RabbitMQProducer producer;

    public MessageController(RabbitMQProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestBody TestDto testDto) {
        producer.sendMessage(testDto);
        return ResponseEntity.ok("Message sent to RabbitMQ :)");
    }
}
