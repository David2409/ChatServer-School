INSERT INTO user (`name`, `password`) VALUES ('testUser', '1000');
INSERT INTO server (`name`) VALUES ('testServer');
INSERT INTO role (`server_id`, `name`) VALUES ('1','testRole');
INSERT INTO server_user (`server_id`, `user_id`) VALUES ('1', '1');
INSERT INTO room (`server_id`,`name`) VALUES ('1','testRoom');
INSERT INTO room_role (`room_id`,`role_id`) VALUES ('1','1');
INSERT INTO user_role (`role_id`,`user_id`) VALUES ('1','1');
INSERT INTO messages (`room_id`,`user_id`,`msg`) VALUES ('1','1','This is a message');