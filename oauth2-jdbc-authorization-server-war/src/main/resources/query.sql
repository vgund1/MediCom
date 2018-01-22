------------------- tables for JDBCTokenStore - Spring Oauth2 ------------------------------
create table if not exists oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255)
);

create table if not exists oauth_client_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);

create table if not exists oauth_access_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication LONG VARBINARY,
  refresh_token VARCHAR(255)
);

create table if not exists oauth_refresh_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication LONG VARBINARY
);

create table if not exists oauth_code (
  code VARCHAR(255), authentication LONG VARBINARY
);

create table if not exists oauth_approvals (
    userId VARCHAR(255),
    clientId VARCHAR(255),
    scope VARCHAR(255),
    status VARCHAR(10),
    expiresAt TIMESTAMP,
    lastModifiedAt TIMESTAMP
);

create table if not exists ClientDetails (
  appId VARCHAR(255) PRIMARY KEY,
  resourceIds VARCHAR(255),
  appSecret VARCHAR(255),
  scope VARCHAR(255),
  grantTypes VARCHAR(255),
  redirectUrl VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(255)
);


-- "secret-key-for-api1" is encoded to $2a$10$m/.NHeG6jbuuJ3m40xBJ6u.ec6LOQXOPyKw3Tfif5h2reoHcQpfMK
-- "secret-key-for-api2" is encoded to $2a$10$D2duax8CLpKXZtgN1QguzuZPdZvxEgxgNJD8MJkxwPrlyVUsk7Dla
-- "secret-key-for-api3" is encoded to $2a$10$gxuPR58zbc0wqousqfjHLeTbeY7999.rEzwEjXJ1zIflYYVH5F2Ke
INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	("api1ClientId", "$2a$10$m/.NHeG6jbuuJ3m40xBJ6u.ec6LOQXOPyKw3Tfif5h2reoHcQpfMK", "read,write,api1",
	"password,authorization_code,refresh_token", null, null, 600, 3600, null, true);
INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	("api2ClientId", "$2a$10$D2duax8CLpKXZtgN1QguzuZPdZvxEgxgNJD8MJkxwPrlyVUsk7Dla", "read,write,api2",
	"implicit", null, null, 600, 3600, null, false);
INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	("api3ClientId", "$2a$10$gxuPR58zbc0wqousqfjHLeTbeY7999.rEzwEjXJ1zIflYYVH5F2Ke", "read,write,api3",
	"password,authorization_code,refresh_token", null, null, 600, 3600, null, true);



------------------- tables for Authentication ------------------------------
CREATE  TABLE sec_user (
	username VARCHAR(100) NOT NULL ,
	password VARCHAR(100) NOT NULL ,
	enabled TINYINT NOT NULL DEFAULT 1 ,
	PRIMARY KEY (username));

CREATE TABLE sec_user_role (
	user_role_id int(11) NOT NULL AUTO_INCREMENT,
	username varchar(45) NOT NULL,
	role varchar(45) NOT NULL,
	PRIMARY KEY (user_role_id),
	UNIQUE KEY uni_username_role (role,username),
	KEY fk_username_idx (username),
	CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES sec_user (username));

INSERT INTO sec_user(username,password,enabled)
VALUES ('admin','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1); -- the raw password is 123456
INSERT INTO sec_user(username,password,enabled)
VALUES ('user','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1); -- the raw password is 123456
INSERT INTO sec_user(username,password,enabled)
VALUES ('quynhcn','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1); -- the raw password is 123456


INSERT INTO sec_user_role (username, role)
VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO sec_user_role (username, role)
VALUES ('user', 'ROLE_USER');
INSERT INTO sec_user_role (username, role)
VALUES ('quynhcn', 'ROLE_USER');
INSERT INTO sec_user_role (username, role)
VALUES ('quynhcn', 'ROLE_ADMIN');

-------------------------------------------------------------
/*
Note:
oauth_client_details table is used by jdbcClientDetailService()
oauth_* tables are used by jdbcTokenStore()
sec_user & sec_user_role tables are used by jdbcAuthentication() or customize the userDetailService()
*/