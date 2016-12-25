CREATE DATABASE `product` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
use product;
create table user (
userId varchar(32) not null unique,
userName varchar(128) not  null,
userPassword varchar(128) not null
);

insert into user values ("admin","admin","admin");