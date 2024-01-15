CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE words (
                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             word VARCHAR(255) NOT NULL
);
