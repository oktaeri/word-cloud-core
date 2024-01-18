package com.wordcloud.core.controller;

import com.wordcloud.core.dto.ResultDto;
import com.wordcloud.core.model.UserToken;
import com.wordcloud.core.repository.UserTokenRepository;
import com.wordcloud.core.service.WordCloudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WordCloudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WordCloudService mockWordCloudService;

    @MockBean
    private UserTokenRepository mockUserTokenRepository;

    @Test
    void getWordCounts_ValidToken_ReturnsWordCounts() throws Exception {
        LocalDate date = LocalDate.now();
        UserToken existingToken = new UserToken("newtok", date, false);
        when(mockUserTokenRepository.findByToken("newtok")).thenReturn(existingToken);
        when(mockWordCloudService.getUserResult("newtok")).thenReturn(List.of(new ResultDto("word1", 5), new ResultDto("word2", 3)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wordcloud/newtok"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getWordCounts_InvalidToken_ReturnsNotFound() throws Exception {
        when(mockUserTokenRepository.findByToken(anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wordcloud/nontok"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getWordCounts_NullToken_ReturnsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wordcloud/"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}