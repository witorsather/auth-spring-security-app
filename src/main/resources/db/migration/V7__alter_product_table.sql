alter table product
alter column PRICE TYPE NUMERIC(10, 2) USING price::NUMERIC(10,2);