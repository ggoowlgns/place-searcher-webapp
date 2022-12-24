DROP TABLE IF EXISTS tb_search_count;
CREATE TABLE tb_search_count (
    keyword VARCHAR(256) not null PRIMARY KEY,
    count INT
);
