package com.wordcloud.core.controller;

import com.wordcloud.core.dto.UploadDto;
import com.wordcloud.core.publisher.RabbitMQProducer;
import com.wordcloud.core.service.UserTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class MessageController {
    private RabbitMQProducer producer;
    private UserTokenService tokenService;

    public MessageController(RabbitMQProducer producer, UserTokenService tokenService) {
        this.producer = producer;
        this.tokenService = tokenService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        byte[] fileContent = file.getBytes();
        String token = tokenService.generateAndSaveToken();

        UploadDto uploadDto = new UploadDto(token, fileContent);

        producer.sendMessage(uploadDto);

        return ResponseEntity.ok("Message sent to RabbitMQ :)");
    }
}
