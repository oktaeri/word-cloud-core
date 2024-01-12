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
public class UploadController {
    private final RabbitMQProducer producer;
    private final UserTokenService tokenService;

    public UploadController(RabbitMQProducer producer, UserTokenService tokenService) {
        this.producer = producer;
        this.tokenService = tokenService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam(value = "minimum", required = false, defaultValue = "0") Integer minimumCount) throws IOException {

        byte[] fileContent = file.getBytes();
        String token = tokenService.generateAndSaveToken();

        UploadDto uploadDto = new UploadDto(token, fileContent, minimumCount);

        producer.sendMessage(uploadDto);

        return ResponseEntity.ok(token);
    }
}
