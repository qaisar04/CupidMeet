--changeset polskiy:1
CREATE TABLE IF NOT EXISTS complain
(
    id BIGSERIAL PRIMARY KEY ,
    username VARCHAR(255) NOT NULL UNIQUE ,
    comment TEXT NOT NULL,
    status VARCHAR(255) NOT NULL
);
--changeset polskiy:2
CREATE TABLE IF NOT EXISTS Review
(
    id BIGSERIAL PRIMARY KEY ,
    username VARCHAR(255) NOT NULL,
    comment TEXT NOT NULL
);