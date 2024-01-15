package com.wordcloud.core.controller;

import com.wordcloud.core.dto.ResultDto;
import com.wordcloud.core.model.UserToken;
import com.wordcloud.core.repository.UserTokenRepository;
import com.wordcloud.core.service.WordCloudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wordcloud")
public class WordCloudController {
    private final WordCloudService wordCloudService;
    private final UserTokenRepository userTokenRepository;

    public WordCloudController(WordCloudService wordCloudService, UserTokenRepository userTokenRepository) {
        this.wordCloudService = wordCloudService;
        this.userTokenRepository = userTokenRepository;
    }

    @GetMapping("/{userToken}")
    public ResponseEntity<?> getWordCounts(@PathVariable String userToken) {
        UserToken existingToken = userTokenRepository.findByToken(userToken);
        if (existingToken == null) {
            String errorMessage = "Token not found: " + userToken;
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }

        List<ResultDto> wordCounts = wordCloudService.getUserResult(userToken);

        return ResponseEntity.ok(wordCounts);
    }
}