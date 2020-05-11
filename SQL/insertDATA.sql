INSERT INTO user (`name`, `password`) VALUES ('testUser', '1000');
INSERT INTO server (`name`) VALUES ('testServer');
INSERT INTO role (`server_id`, `name`) VALUES ('1','testRole');
INSERT INTO server_user (`server_id`, `user_id`) VALUES ('1', '1');
INSERT INTO room (`server_id`,`name`) VALUES ('1','testRoom');
INSERT INTO room_role (`room_id`,`role_id`) VALUES ('1','1');
INSERT INTO user_role (`role_id`,`user_id`) VALUES ('1','1');
INSERT INTO messages (`room_id`,`user_id`,`msg`) VALUES ('1','1','This is a message');

INSERT INTO role (`server_id`, `name`, `caninvite`, `canchange`) VALUES ('1','Some Test Role', FALSE, FALSE);
INSERT INTO room_role (`room_id`,`role_id`,`cansee`,`canwrite`,`canread`) VALUES ('1','2', FALSE, FALSE, FALSE);
INSERT INTO user_role (`role_id`,`user_id`) VALUES ('2','1');


INSERT INTO room (`server_id`,`name`) VALUES ('1','testRoomCantAccess');
INSERT INTO room_role (`room_id`,`role_id`,`cansee`,`canwrite`,`canread`) VALUES ('2','2', FALSE, FALSE, FALSE);