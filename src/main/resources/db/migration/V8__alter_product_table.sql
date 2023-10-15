DROP TABLE IF EXISTS PRODUCT;

CREATE TABLE product
(
    id    TEXT PRIMARY KEY UNIQUE NOT NULL,
    name  TEXT                    NOT NULL,
    price NUMERIC(10, 2)          NOT NULL
);
