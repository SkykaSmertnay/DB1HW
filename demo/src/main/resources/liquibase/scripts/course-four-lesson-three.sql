--liquibase formatted sql

--changeset student:1-create-test-table
CREATE TABLE IF NOT EXISTS liquibase_test (
    id BIGSERIAL PRIMARY KEY
);
