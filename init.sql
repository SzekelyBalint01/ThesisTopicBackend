DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS items CASCADE;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS groups_connect_to_user CASCADE;
DROP TABLE IF EXISTS item_connect_to_user CASCADE;
DROP TABLE IF EXISTS item_connect_to_groups CASCADE;



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
                          paid int
    );

    CREATE TABLE groups(
                           id SERIAL PRIMARY KEY,
                           group_name varchar(30)
    );

    CREATE TABLE groups_connect_to_user(
                                       group_id bigint,
                                       user_id bigint
    );

    CREATE TABLE item_connect_to_user(
                                      user_id bigint,
                                      item_id bigint
    );

    CREATE TABLE item_connect_to_groups(
                            group_id bigint,
                            item_id bigint
    );


