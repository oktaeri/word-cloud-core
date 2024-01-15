ALTER TABLE user_tokens
    ADD CONSTRAINT unique_token_constraint UNIQUE (token);
