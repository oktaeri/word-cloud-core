package com.wordcloud.core.controller;

import com.wordcloud.core.dto.ResultDto;
import com.wordcloud.core.service.WordCloudService;
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

    public WordCloudController(WordCloudService wordCloudService) {
        this.wordCloudService = wordCloudService;
    }

    @GetMapping("/{userToken}")
    public ResponseEntity<List<ResultDto>> getWordCounts(@PathVariable String userToken) {
        List<ResultDto> wordCounts = wordCloudService.getUserResult(userToken);

        return ResponseEntity.ok(wordCounts);
    }
}