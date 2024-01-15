package com.wordcloud.core.service;

import com.wordcloud.core.dto.ResultDto;
import com.wordcloud.core.model.WordCount;
import com.wordcloud.core.repository.WordCountRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordCloudService {
    private final WordCountRepository wordCountRepository;

    public WordCloudService(WordCountRepository wordCountRepository) {
        this.wordCountRepository = wordCountRepository;
    }

    public List<ResultDto> getUserResult(String userToken) {
        List<WordCount> wordCounts = wordCountRepository.findByUserTokenToken(userToken);

        return wordCounts.stream()
                .map(this::mapToResultDto)
                .sorted(Comparator.comparing(ResultDto::getCount, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private ResultDto mapToResultDto(WordCount wordCount) {
        return new ResultDto(wordCount.getWord(), wordCount.getCount());
    }
}
