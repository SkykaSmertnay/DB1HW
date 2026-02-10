CREATE TABLE car (
    id BIGSERIAL PRIMARY KEY,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    price NUMERIC(12, 2) NOT NULL
);

CREATE TABLE person (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    age INT NOT NULL,
    has_driver_license BOOLEAN NOT NULL,

    car_id BIGINT NOT NULL,
    CONSTRAINT fk_person_car
        FOREIGN KEY (car_id)
        REFERENCES car(id)
);
