package com.wordcloud.core.service;

import com.wordcloud.core.model.UserToken;
import com.wordcloud.core.repository.UserTokenRepository;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserTokenService {
    private final UserTokenRepository userTokenRepository;

    public UserTokenService(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    public String generateAndSaveToken() {
        String generatedToken;

        do {
            generatedToken = generateRandomToken();
        } while (tokenExists(generatedToken));

        UserToken userToken = new UserToken(generatedToken, LocalDate.now().minusDays(3));
        userTokenRepository.save(userToken);
        return userToken.getToken();
    }

    private boolean tokenExists(String token) {
        UserToken existingToken = userTokenRepository.findByToken(token);
        return existingToken != null;
    }

    private String generateRandomToken() {
        RandomStringGenerator generator = new RandomStringGenerator
                .Builder()
                .withinRange(new char[]{'0', '9'}, new char[]{'a', 'z'})
                .build();

        return generator.generate(6);
    }

}
