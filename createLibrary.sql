DROP TABLE IF EXISTS
	borrowed_by,
	publisher_phone,
	author_phone,
	written_by,
	locatedAt,
	Book,
	Publisher,
	Phone,
	Author,
	Library,
	Audit,
	Member;

-- MEMBER -> BOOK = borrowers
-- BOOK -> PUBLISHER = published_by
-- PUBLISHER -> PHONE = publisher_phone
-- PHONE -> AUTHOR = author_phone
-- BOOK -> AUTHOR = written_by

CREATE TABLE Member (
	MemberID INT	NOT NULL,
	first_name VARCHAR(20)	NOT NULL,
	last_name VARCHAR(20)	NOT NULL,
	gender ENUM ('M', 'F')	NOT NULL,
	DOB DATE		NOT NULL,
	PRIMARY KEY (MemberID)
);

CREATE TABLE Publisher (
	PubID INT		NOT NULL,
	Pub_name VARCHAR(50)	NOT NULL,
	PRIMARY KEY (PubID)
);


CREATE TABLE Book (
	ISBN CHAR(14)		NOT NULL,
	Title VARCHAR(50)	NOT NULL,
	PubID INT		NOT NULL,
	date_published DATE	NOT NULL,
	FOREIGN KEY (PubID) REFERENCES Publisher (PubID) ON DELETE CASCADE,
	PRIMARY KEY (ISBN)
);

CREATE TABLE Author (
	AuthorID INT		NOT NULL,
	first_name VARCHAR(20)	NOT NULL,
	last_name VARCHAR(20)	NOT NULL,
	PRIMARY KEY(AuthorID)
);

CREATE TABLE Phone (
	PNumber CHAR(12)			NOT NULL,
	num_type ENUM('(o)', '(c)', '(h)') 	NOT NULL,
	PRIMARY KEY (PNumber)
);

CREATE TABLE Audit(
	table_name VARCHAR(50)				NOT NULL,
	action ENUM  ("INSERT", "DELETE", "UPDATE")	NOT NULL,
	time TIME					NOT NULL,
	audit_id INT					NOT NULL auto_increment,
	PRIMARY KEY (audit_id)
);


-- LIBRARY
CREATE TABLE Library (
	name VARCHAR(50)	NOT NULL,
	street VARCHAR(50)	NOT NULL,
	city VARCHAR(50)	NOT NULL,
	state CHAR(2)		NOT NULL,
	PRIMARY KEY (name)
);
-- TODO foriegn key?? good enough for now??


CREATE TABLE locatedAt(
	name VARCHAR(50)	NOT NULL,
	ISBN CHAR(14)		NOT NULL,
	total_copies INT	NOT NULL,
	num_not_checked_out INT NOT NULL,
	shelf_num INT		NOT NULL,
	floor_num INT 		NOT NULL,
	PRIMARY KEY(ISBN, name),
	FOREIGN KEY (ISBN) REFERENCES Book (ISBN) ON DELETE CASCADE,
	FOREIGN KEY (name) REFERENCES Library (name) ON DELETE CASCADE
);
-- TODO is primary key right?	

CREATE TABLE author_phone (
	AuthorID INT	 	NOT NULL,
	PNumber CHAR(12)	NOT NULL,
	PRIMARY KEY (PNumber, AuthorID),
	FOREIGN KEY (AuthorID) REFERENCES Author (AuthorID) ON DELETE CASCADE,
	FOREIGN KEY (PNumber) REFERENCES Phone (PNumber) ON DELETE CASCADE
);

CREATE TABLE publisher_phone (
	PubID INT 		NOT NULL,
	PNumber CHAR(12)	NOT NULL,
	PRIMARY KEY (PNumber, PubID),
	FOREIGN KEY (PubID) REFERENCES Publisher (PubID) ON DELETE CASCADE,
	FOREIGN KEY (PNumber) REFERENCES Phone (PNumber) ON DELETE CASCADE
);


CREATE TABLE written_by (
	ISBN CHAR(14)		NOT NULL,
	AuthorID INT		NOT NULL,
	PRIMARY KEY (AuthorID, ISBN),
	FOREIGN KEY (ISBN) REFERENCES Book (ISBN) ON DELETE CASCADE,
	FOREIGN KEY (AuthorID) REFERENCES Author (AuthorID) ON DELETE CASCADE
);


-- Member -> Book
CREATE TABLE borrowed_by (
	ISBN CHAR(14)		NOT NULL,
	MemberID INT		NOT NULL,
	checkout_date DATE	NOT NULL,
	checkin_date DATE,
	PRIMARY KEY (ISBN, MemberID, checkout_date),
	FOREIGN KEY (ISBN) REFERENCES Book(ISBN) ON DELETE CASCADE,
	FOREIGN KEY (MemberID) REFERENCES Member (MemberID) ON DELETE CASCADE
);


	

