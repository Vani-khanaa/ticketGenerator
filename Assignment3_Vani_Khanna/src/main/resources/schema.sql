CREATE TABLE tickets (
	id INT NOT NULL Primary Key AUTO_INCREMENT, 
	name VARCHAR(255), 
	price double, 
	event VARCHAR(255), 
	gate int,
	tt VARCHAR(255), 
	eventCity VARCHAR(255),
	eventDescription VARCHAR(255)
	);
	
CREATE TABLE users (
   userId INT NOT NULL Primary Key AUTO_INCREMENT,
   userName VARCHAR(255),
   encryptedPassword VARCHAR(128) NOT NULL,
   ENABLED           BIT NOT NULL  
);

create table ROLES
(
  roleId   BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  roleName VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE user_role(
	 ID  BIGINT NOT NULL Primary Key AUTO_INCREMENT,
     userId BIGINT NOT NULL,
     roleId BIGINT NOT NULL
  );
 

  

  
  
  





