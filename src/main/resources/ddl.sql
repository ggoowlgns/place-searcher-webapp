DROP TABLE IF EXISTS tb_search_count;
CREATE TABLE tb_search_count (
    seq INT auto_increment PRIMARY KEY,
    keyword VARCHAR(256) not null ,
    count INT
);
