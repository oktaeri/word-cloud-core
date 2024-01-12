package com.wordcloud.core.repository;

import com.wordcloud.core.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserTokenRepository extends JpaRepository<UserToken, UUID> {
    UserToken findByToken(String token);
}