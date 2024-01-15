CREATE TABLE user_tokens (
                             id UUID PRIMARY KEY,
                             token VARCHAR(6) NOT NULL,
                             expiration_date DATE
);

CREATE TABLE word_count (
                            id UUID PRIMARY KEY,
                            count INT NOT NULL,
                            user_token_id UUID REFERENCES user_tokens(id),
                            word VARCHAR(255) NOT NULL
);
