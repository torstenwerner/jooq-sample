CREATE TABLE author
(
  id SERIAL PRIMARY KEY NOT NULL,
  first_name TEXT NOT NULL,
  last_name TEXT NOT NULL
);

CREATE TABLE amount
(
  id SERIAL NOT NULL,
  customer_id INTEGER NOT NULL,
  amount NUMERIC(9,2) NOT NULL,
  CONSTRAINT amount_pk PRIMARY KEY (id, customer_id)
);

INSERT INTO amount (id, customer_id, amount) VALUES (1, 1, 9.99);
INSERT INTO amount (id, customer_id, amount) VALUES (2, 1, 42.23);
INSERT INTO amount (id, customer_id, amount) VALUES (3, 1, -5.00);
INSERT INTO amount (id, customer_id, amount) VALUES (2, 2, 49.99);
INSERT INTO amount (id, customer_id, amount) VALUES (1, 2, -17.50);

INSERT INTO author (first_name, last_name) VALUES ('Hildegunst', 'von Mythenmetz');
INSERT INTO author (first_name, last_name) VALUES ('Danzelot', 'von Silbendrechsler');
