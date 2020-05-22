create database chatserver;
use chatserver;
CREATE USER 'chatserver_server'@'localhost' IDENTIFIED BY 'goodPassword';
GRANT DELETE ON chatserver.* TO 'chatserver_server'@'localhost';
GRANT SELECT ON chatserver.* TO 'chatserver_server'@'localhost';
GRANT INSERT ON chatserver.* TO 'chatserver_server'@'localhost';
GRANT UPDATE ON chatserver.* TO 'chatserver_server'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE server(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL DEFAULT 'NEW SERVER',
    since DATETIME NOT NULL DEFAULT NOW(),
    notificationroom BIGINT DEFAULT NULL
    #FK after room
);

CREATE TABLE user(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL NOT NULL,
    password CHAR(128) NOT NULL,
    since DATETIME NOT NULL DEFAULT NOW()
);

CREATE TABLE room(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    server_id BIGINT NOT NULL,
    name VARCHAR(40) NOT NULL DEFAULT 'NEW ROOM',
    since DATETIME NOT NULL DEFAULT NOW(),
    FOREIGN KEY fk_server_id (server_id) REFERENCES server(id) ON DELETE CASCADE
);

ALTER TABLE server ADD CONSTRAINT FOREIGN KEY fk_notificationroom (notificationroom) REFERENCES room(id) ON DELETE SET null;

CREATE TABLE role(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    server_id BIGINT NOT NULL,
    name VARCHAR(40) NOT NULL,
    caninvite BOOL NOT NULL DEFAULT FALSE,
    canchange BOOL NOT NULL DEFAULT FALSE,
    since DATETIME NOT NULL DEFAULT NOW(),
    FOREIGN KEY fk_server_id (server_id) REFERENCES server(id) ON DELETE CASCADE
);

CREATE TABLE messages(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    room_id BIGINT NOT NULL,
    user_id BIGINT,
    sendedat DATETIME NOT NULL DEFAULT NOW(),
    msg VARCHAR(2000) NOT NULL,
	FOREIGN KEY fk_room_id (room_id) REFERENCES room(id) ON DELETE CASCADE,
	FOREIGN KEY fk_user_id (user_id) REFERENCES user(id) ON DELETE SET NULL
);

CREATE TABLE loglevel(
	id TINYINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40)
);

CREATE TABLE logs(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    server_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    loglevel_id TINYINT NOT NULL,
    date DATETIME NOT NULL DEFAULT NOW(),
    msg TEXT NOT NULL,
	FOREIGN KEY fk_server_id (server_id) REFERENCES server(id) ON DELETE CASCADE,
	FOREIGN KEY fk_user_id (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY fk_loglevel_id (loglevel_id) REFERENCES loglevel(id)
);

CREATE TABLE server_user(
	server_id BIGINT NOT NULL, 
    user_id BIGINT NOT NULL,
	FOREIGN KEY fk_server_id (server_id) REFERENCES server(id) ON DELETE CASCADE,
	FOREIGN KEY fk_user_id (user_id) REFERENCES user(id) ON DELETE CASCADE,
    PRIMARY KEY (server_id, user_id)
);

CREATE TABLE room_role(
	room_id BIGINT NOT NULL, 
    role_id BIGINT NOT NULL,
    cansee BOOL NOT NULL DEFAULT TRUE,
    canwrite BOOL NOT NULL DEFAULT TRUE,
    canread BOOL NOT NULL DEFAULT TRUE,
	FOREIGN KEY fk_room_id (room_id) REFERENCES room(id) ON DELETE CASCADE,
    FOREIGN KEY fk_role_id (role_id) REFERENCES role(id) ON DELETE CASCADE,
    PRIMARY KEY(room_id, role_id)
);

CREATE TABLE user_role(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY fk_role_id (role_id) REFERENCES role(id) ON DELETE CASCADE,
	FOREIGN KEY fk_user_id (user_id) REFERENCES user(id) ON DELETE CASCADE,
    PRIMARY KEY(role_id, user_id)
);