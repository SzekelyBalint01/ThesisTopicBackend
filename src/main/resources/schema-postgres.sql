
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS groups_connect_to_user;
DROP TABLE IF EXISTS item_connect_to_user;
DROP TABLE IF EXISTS dues;
DROP TABLE IF EXISTS item_connect_to_groups;



    CREATE TABLE users(
                          id SERIAL PRIMARY KEY,
                          username varchar(30),
                          email varchar(50),
                          password_salt bytea,
                          password_hash bytea
    );

    CREATE TABLE items(
                          id SERIAL PRIMARY KEY,
                          name varchar(30),
                          price int,
                          currency varchar(4),
                          map_url varchar(16384),
                          description varchar(250),
                          payed int
    );

    CREATE TABLE groups(
                           id SERIAL PRIMARY KEY,
                           group_name varchar(30)
    );

    CREATE TABLE groups_connect_to_user(
                                       group_id int,
                                       user_id int
    );

    CREATE TABLE item_connect_to_user(
                                      user_id int,
                                      item_id int
    );

    CREATE TABLE debt_connect_to_user(


    );

    CREATE TABLE debt (
                          id SERIAL PRIMARY KEY,
                          price int,
                          currency varchar (4)
    );

    CREATE TABLE item_connect_to_groups(
                            group_id SERIAL,
                            item_id SERIAL
    );
