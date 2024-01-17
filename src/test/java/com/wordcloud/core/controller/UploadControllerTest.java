package com.wordcloud.core.controller;

import com.wordcloud.core.service.UserTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserTokenService mockTokenService;

    @Test
    void uploadProvidingFile_shouldReturnOK() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test file hello".getBytes());

        when(mockTokenService.generateAndSaveToken()).thenReturn("fakeTk");

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/upload")
                        .file(file)
                        .param("minimum", "5")
                        .param("filterCommon", "true")
                        .param("customWords", "word1,word2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void uploadFile_shouldReturnBadRequestForNonTextPlainFiles() throws Exception {
        String expectedResponseString = "Only .txt files are allowed.";
        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", MediaType.APPLICATION_PDF_VALUE, "File content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/upload")
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponseString));
    }

    @Test
    void uploadFile_shouldGenerateAndSaveToken() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "My awesome test file".getBytes());

        when(mockTokenService.generateAndSaveToken()).thenReturn("fake12");

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/upload")
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("fake12"));

        verify(mockTokenService, times(1)).generateAndSaveToken();
    }

    @Test
    void uploadFile_shouldReturnBadRequestForEmptyFiles() throws Exception {
        String expectedResponseString = "Uploaded file has no content.";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/upload")
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponseString));
    }
}
