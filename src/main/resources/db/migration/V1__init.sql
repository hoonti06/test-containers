CREATE TABLE IF NOT EXISTS member (
    id bigint auto_increment primary key,
    name varchar(30) not null,
    address varchar(300) null
);
