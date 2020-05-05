#----------------------------------INSERT----------------------------------
#message
INSERT INTO messages (`room_id`, `user_id`, `sendedat`, `msg`) VALUES ('{room_id}', '{user_id}', '{sendedat}', '{msg}');
#User to ChatServer / Room
INSERT INTO server_user (`server_id`,`user_id`) VALUES ('{server_id}','{user_id}');
#Create User
INSERT INTO user (`name`,`password`) VALUES ('{username}','{hashedPassword}');
#room_role
INSERT INTO room_role (`room_id`,`role_id`,`cansee`,`canwrite`,`canread`) VALUES ('{room_id}','{role_id}','{cansee}','{canwrite}','{canread}');

#----------------------------------SELECT----------------------------------
#priveleges to send
SELECT COUNT(*) FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id) WHERE ur.user_id = '{user_id}' AND rr.room_id = '{room_id}' AND rr.canwrite = TRUE;
#Load ChatRoom/Server Settings
SELECT id FROM room WHERE server_id = 'server_id' ORDER BY server_id;
#Get Rooms for a user in which he is not
SELECT r.id FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id) JOIN role r ON (rr.room_id = r.id) WHERE ur.user_id = '{user_id}' AND r.server_id = '{server_id}' AND rr.cansee = FALSE OR rr.canread = FALSE ORDER BY r.id;
#User
Select id FROM user WHERE name = '{username}' AND password = '{hashedPassword}';
#Get Servers from User
Select id FROM server_user WHERE user_id = '{userId}';
#room_role
SELECT room_id, role_id FROM room_role WHERE room_id in ('{room_id}') AND role_id in ('role_id') ORDER BY room_id, role_id;

#----------------------------------DELETE----------------------------------
#Remove user from Server
DELETE FROM server_user WHERE server_id = '{server_id}' AND user_id = '{user_id}';
#User
DELETE FROM user WHERE id = '{user_id}';

#----------------------------------UPDATE----------------------------------
#Privileges
UPDATE room_role SET cansee = '{valueCanSee}' AND canwrite = '{valueCanWrite}' AND canread = '{valueCanRead}' WHERE role_id = '{role_id}' AND room_id = '{room_id}';