package com.wordcloud.core.service;

import com.wordcloud.core.dto.ResultDto;
import com.wordcloud.core.model.WordCount;
import com.wordcloud.core.repository.UserTokenRepository;
import com.wordcloud.core.repository.WordCountRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordCloudService {
    private final WordCountRepository wordCountRepository;
    private final UserTokenRepository userTokenRepository;

    public WordCloudService(WordCountRepository wordCountRepository, UserTokenRepository userTokenRepository) {
        this.wordCountRepository = wordCountRepository;
        this.userTokenRepository = userTokenRepository;
    }

    public List<ResultDto> getUserResult(String userToken) {
        List<WordCount> wordCounts = wordCountRepository.findByUserTokenToken(userToken);

        if (!userTokenRepository.existsByToken(userToken)) {
            return null;
        }

        return wordCounts.stream()
                .map(this::mapToResultDto)
                .sorted(Comparator.comparing(ResultDto::getValue, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private ResultDto mapToResultDto(WordCount wordCount) {
        return new ResultDto(wordCount.getWord(), wordCount.getCount());
    }
}
