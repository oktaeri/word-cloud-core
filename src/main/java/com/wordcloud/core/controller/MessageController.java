package com.wordcloud.core.controller;

import com.wordcloud.core.dto.UploadDto;
import com.wordcloud.core.publisher.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class MessageController {
    private RabbitMQProducer producer;

    public MessageController(RabbitMQProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        byte[] fileContent = file.getBytes();
        UploadDto uploadDto = new UploadDto("meow123", fileContent);

        producer.sendMessage(uploadDto);

        return ResponseEntity.ok("Message sent to RabbitMQ :)");
    }
}
