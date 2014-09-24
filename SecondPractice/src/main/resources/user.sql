DROP TABLE IF EXISTS tbl_user;

CREATE TABLE tbl_user ( 
	userId          varchar(12)		NOT NULL, 
	password		varchar(12)		NOT NULL,
	name			varchar(20)		NOT NULL,
	email			varchar(50),	
  	
	PRIMARY KEY               (userId)
);

INSERT INTO tbl_user VALUES('testUserId', 'testPassword', 'testName', 'testEmail');