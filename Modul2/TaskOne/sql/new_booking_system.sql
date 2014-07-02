DROP DATABASE new_booking_system;
CREATE DATABASE IF NOT EXISTS new_booking_system;

USE New_Booking_System;

DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Ticket;
DROP TABLE IF EXISTS Event;

CREATE TABLE IF NOT EXISTS Users(
	id BIGINT(18) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	email VARCHAR(50),
	UNIQUE(id),
	UNIQUE(email)
);

CREATE TABLE IF NOT EXISTS Event (
	id BIGINT(18) PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
	title VARCHAR(50),
	event_date DATE,
	UNIQUE(id)
);


CREATE TABLE IF NOT EXISTS Ticket(
	id BIGINT(18) PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
	eventId BIGINT(18),
	userId BIGINT(18),
	category ENUM('STANDARD', 'PREMIUM', 'BAR'),
	place INT,
	UNIQUE(id),
	FOREIGN KEY (eventId) 
   				REFERENCES Event(id),
   				
	FOREIGN KEY (userId) 
   				REFERENCES Users(id)
);

insert into Users values(1, 'John Smith', 'smith@gmail.com');
insert into Users values(2, 'Mark Martin', 'martin@gmail.com');
insert into Users values(3, 'Ricky Jones', 'jones@gmail.com');

insert into Event values(1, 'Home Alone', '2013/12/24 19:00');
insert into Event values(2, 'Home Alone 1', '2013/12/27 16:30');
insert into Event values(3, 'Home Alone 2', '2013/12/29 16:30');
insert into Event values(4, 'Home Alone 3', '2014/1/16 16:30');

insert into Ticket values(1,  1, 1, 'PREMIUM', 12);
insert into Ticket values(2,  1, 2, 'STANDARD', 15);
insert into Ticket values(3,  1, 3, 'BAR', 5);
insert into Ticket values(4,  2, 1, 'PREMIUM', 17);
insert into Ticket values(5,  2, 2, 'BAR', 2);
insert into Ticket values(6,  2, 3, 'BAR', 8);
insert into Ticket values(7,  3, 1, 'PREMIUM', 12);
insert into Ticket values(8,  3, 2, 'PREMIUM', 15);
insert into Ticket values(9,  3, 3, 'PREMIUM', 5);
insert into Ticket values(10, 4, 1, 'PREMIUM', 12);
insert into Ticket values(11, 4, 2, 'BAR', 15);
insert into Ticket values(12, 4, 3, 'BAR', 5);
