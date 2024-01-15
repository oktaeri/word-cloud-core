package com.wordcloud.core.repository;

import com.wordcloud.core.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface UserTokenRepository extends JpaRepository<UserToken, UUID> {
    UserToken findByToken(String token);
    List<UserToken> findByExpirationDateBefore(LocalDate date);
    boolean existsByToken(String token);
}