package com.wordcloud.core.service;

import com.wordcloud.core.model.UserToken;
import com.wordcloud.core.repository.UserTokenRepository;
import com.wordcloud.core.repository.WordCountRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CleanupService {
    // Guide: https://www.baeldung.com/registration-token-cleanup
    private final UserTokenRepository userTokenRepository;
    private final WordCountRepository wordCountRepository;

    public CleanupService(UserTokenRepository userTokenRepository, WordCountRepository wordCountRepository) {
        this.userTokenRepository = userTokenRepository;
        this.wordCountRepository = wordCountRepository;
    }

    @Scheduled(cron = "${purge.cron.expression}")
    public void cleanupExpiredTokens() {
        LocalDate currentDate = LocalDate.now();
        List<UserToken> expiredTokens = userTokenRepository.findByExpirationDateBefore(currentDate);

        for (UserToken expiredToken : expiredTokens) {
            wordCountRepository.deleteByUserToken(expiredToken);

            userTokenRepository.delete(expiredToken);
        }
    }
}
