package com.wordcloud.core.service;

import com.wordcloud.core.model.UserToken;
import com.wordcloud.core.repository.UserTokenRepository;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;

@Service
public class UserTokenService {
    private final UserTokenRepository userTokenRepository;

    public UserTokenService(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    public String generateAndSaveToken() {
        String generatedToken = generateRandomToken();
        UserToken userToken = new UserToken(generatedToken);
        userTokenRepository.postToken(userToken);
        return userToken.getToken();
    }

    private String generateRandomToken() {
        RandomStringGenerator generator = new RandomStringGenerator
                .Builder()
                .withinRange('a', 'z')
                .build();

        return generator.generate(6);
    }

}
