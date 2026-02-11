--liquibase formatted sql

--changeset student:idx-student-name
CREATE INDEX IF NOT EXISTS idx_student_name ON student (name);
