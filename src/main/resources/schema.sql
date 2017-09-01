drop table if exists customer;

create table customer (
  id numeric identity primary key,
  name varchar(100) not null,
  email varchar(100) not null,
  created_date date not null);