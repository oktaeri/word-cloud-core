CREATE TABLE word_count (
                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                            count INT NOT NULL,
                            user_token_id UUID REFERENCES user_tokens(id),
                            word_id UUID REFERENCES words(id)
);
