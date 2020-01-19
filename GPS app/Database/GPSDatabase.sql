Create DATABASE LocationDatabase;
Use LocationDatabase;

CREATE TABLE Users(
user_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
userName varchar(20) NOT NULL,
password varchar(20) NOT NULL,
userKey varchar(20) NOT NULL
);

CREATE TABLE Location(
location_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
user_id int,
latitude FLOAT NOT NULL,
longitude FLOAT NOT NULL,
FOREIGN KEY (user_id) REFERENCES Users(user_id)
);