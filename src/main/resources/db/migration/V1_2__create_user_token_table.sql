CREATE TABLE user_tokens (
                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             token VARCHAR(6) NOT NULL
);
