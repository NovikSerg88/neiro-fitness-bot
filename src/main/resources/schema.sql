DROP TABLE IF EXISTS clients CASCADE;

CREATE TABLE IF NOT EXISTS clients
(
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    chat_id       BIGINT        NOT NULL UNIQUE,
    message_id    BIGINT,
    first_name    VARCHAR(100) NOT NULL,
    user_name     VARCHAR(100) NOT NULL,
    start_date    TIMESTAMP WITHOUT TIME ZONE,
    end_date      TIMESTAMP WITHOUT TIME ZONE,
    subscription  VARCHAR(16),
    is_subscribed BOOLEAN
);