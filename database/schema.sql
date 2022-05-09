drop database if exists sgbuses;
create database sgbuses;

use sgbuses;

create table if not exists users(
    username varchar(32) PRIMARY KEY not null,
    password varchar(256) not null,
);

create table if not exists bookmarks (
    bookmark_id int not null primary key auto_increment,
    bus_stop_code int(5) not null,
    description varchar(128) not null,
    username varchar(32) not null,
    foreign key(username) references users(username),
    foreign key(bus_stop_code) references bus_stops(bus_stop_code)
);

create table bus_stops (
    bus_stop_code int(5) PRIMARY KEY not null,
    description varchar(128) not null,
);
