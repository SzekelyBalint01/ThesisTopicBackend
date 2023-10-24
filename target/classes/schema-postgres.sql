DROP TABLE IF EXISTS users;
DROP TABLE  IF EXISTS items;
DROP TABLE  IF EXISTS groups;
DROP TABLE  IF EXISTS groupsConnectToUser;
DROP TABLE  IF EXISTS itemConnectToUser;
DROP TABLE  IF EXISTS dues;
DROP TABLE  IF EXISTS itemConnectToGroups;

CREATE TABLE users(
                      id SERIAL PRIMARY KEY,
                      username varchar(30),
                      email varchar(50),
                      password varchar(30)
);

CREATE TABLE items(
                      id SERIAL PRIMARY KEY,
                      itemName varchar(30),
                      price int,
                      currency varchar(4),
                      mapUrl varchar(150),
                      description varchar(250)
);

CREATE TABLE groups(
                       id SERIAL PRIMARY KEY,
                       groupName varchar(30)
);

CREATE TABLE groupsConnectToUser(
                                   groupID int,
                                   userID int
);

CREATE TABLE itemConnectToUser(
                                  userID int,
                                  itemID int
);

CREATE TABLE dues (
                      id SERIAL PRIMARY KEY,
                      oweUser int,
                      price int,
                      currency varchar (4)
);

CREATE TABLE itemConnectToGroups(
                        groupID SERIAL,
                        userId SERIAL
);