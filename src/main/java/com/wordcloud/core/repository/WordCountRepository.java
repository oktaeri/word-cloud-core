package com.wordcloud.core.repository;

import com.wordcloud.core.model.UserToken;
import com.wordcloud.core.model.WordCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WordCountRepository extends JpaRepository<WordCount, UUID> {
    List<WordCount> findByUserTokenToken(String userToken);
    void deleteByUserToken(UserToken userToken);
}
