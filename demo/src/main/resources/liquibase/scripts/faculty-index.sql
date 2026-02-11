--liquibase formatted sql

--changeset student:idx-faculty-name-color
CREATE INDEX IF NOT EXISTS idx_faculty_name_color ON faculty (name, color);
