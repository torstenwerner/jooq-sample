-- CREATE USER C##test identified by test;
-- GRANT CONNECT, RESOURCE TO C##test;
-- GRANT UNLIMITED TABLESPACE TO C##test;

create table author
(
    id         NUMBER(9)     not null
        constraint AUTHOR_PK primary key,
    first_name VARCHAR2(255) not null,
    last_name  VARCHAR2(255) not null
);

create table amount
(
    id          NUMBER(9)    not null,
    customer_id NUMBER(9)    not null,
    amount      NUMBER(9, 2) not null,
    CONSTRAINT amount_pk PRIMARY KEY (id, customer_id)
);

INSERT INTO amount (id, customer_id, amount)
VALUES (1, 1, 9.99);
INSERT INTO amount (id, customer_id, amount)
VALUES (2, 1, 42.23);
INSERT INTO amount (id, customer_id, amount)
VALUES (3, 1, -5.00);
INSERT INTO amount (id, customer_id, amount)
VALUES (2, 2, 49.99);
INSERT INTO amount (id, customer_id, amount)
VALUES (1, 2, -17.50);

INSERT INTO author (id, first_name, last_name)
VALUES (1, 'Hildegunst', 'von Mythenmetz');
INSERT INTO author (id, first_name, last_name)
VALUES (2, 'Danzelot', 'von Silbendrechsler');
