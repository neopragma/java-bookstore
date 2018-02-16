create database bookstore;
create user 'bookstore'@'localhost' identified by 'bookstore';
grant all privileges on *.* to 'bookstore'@'localhost' identified by 'bookstore';