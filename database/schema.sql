drop database if exists sgbuses;
create database sgbuses;

use sgbuses;

create table users (
    username varchar(32) not null,
    password varchar(256) not null,
    primary key(username)
);

create table bookmarks (
    bookmark_id int not null auto_increment,
    bus_stop_code int(5) not null,
    description varchar,
    link varchar,
    username varchar(32) not null,

    primary key(bookmark_id)
    constraint fk_username
        foreign keye(username)
        references users(username)
);

