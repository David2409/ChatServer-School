#----------------------------------INSERT----------------------------------
#message
	INSERT INTO messages (`room_id`, `user_id`, `sendedat`, `msg`) VALUES ('{room_id}', '{user_id}', '{sendedat}', '{msg}');
#User to ChatServer / Room
	INSERT INTO server_user (`server_id`,`user_id`) VALUES ('{server_id}','{user_id}');
#Create User
	INSERT INTO user (`name`,`password`) VALUES ('{username}','{hashedPassword}');
#room_role
	INSERT INTO room_role (`room_id`,`role_id`,`cansee`,`canwrite`,`canread`) VALUES ('{room_id}','{role_id}','{cansee}','{canwrite}','{canread}');
#Server
	INSERT INTO server (`name`, `owner`) VALUES ('{name}', '{user.id}');
#Room
	INSERT INTO room (`server_id`, `name`) VALUES ('{server_id}','{name}');
#Role
	INSERT INTO role (`server_id`, `name`) VALUES ('{server_id}', '{name}');

#----------------------------------SELECT----------------------------------
#priveleges to send
	SELECT FALSE FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id AND rr.room_id = '{room_id}' AND ur.user_id = '{user_id}') GROUP BY rr.room_id HAVING SUM(rr.canwrite) = '0';
#priveleges to read
	SELECT FALSE FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id AND rr.room_id = '{room_id}' AND ur.user_id = '{user_id}') GROUP BY rr.room_id HAVING SUM(rr.canread) = '0';
#privelegs to see
	SELECT FALSE FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id AND rr.room_id = '{room_id}' AND ur.user_id = '{user_id}') GROUP BY rr.room_id HAVING SUM(rr.cansee) = '0';
#priveleges to modivy
	SELECT TRUE FROM user_role ur JOIN role r ON (r.id = ur.role_id) WHERE r.server_id = '{server_id}' AND ur.user_id = '{user_id}' AND r.canchange = TRUE;
#priveleges to invite
	SELECT TRUE FROM user_role ur JOIN role r ON (r.id = ur.role_id) WHERE r.server_id = '{server_id}' AND ur.user_id = '{user_id}' AND r.caninvite = TRUE;
#Load ChatRoom/Server Settings
	SELECT id FROM room WHERE server_id = 'server_id' ORDER BY id;
#Get Rooms for a user in which he is not
	SELECT DISTINCT rr.room_id FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id) JOIN role r ON (rr.role_id = r.id) WHERE ur.user_id = '{user_id}' AND r.server_id = '{server_id}' GROUP BY rr.room_id HAVING SUM(rr.canread) = '0' ORDER BY r.id;
#User
	Select id FROM user WHERE name = '{username}' AND password = '{hashedPassword}';
#Get Servers from User
	Select server_id FROM server_user WHERE user_id = '{userId}';
#room_role
	SELECT room_id, role_id FROM room_role WHERE room_id in ('{room_id}') AND role_id in ('role_id') ORDER BY room_id, role_id;
#username
	SELECT name FROM user WHERE id = '{user_id}';
#notification Room
	SELECT notificationroom FROM server WHERE '{server_id}' AND notificationroom IS NOT NULL;
#Servername
	SELECT name FROM server WHERE id = '{server_id}';
#room name
	SELECT name FROM room WHERE id = '{room_id}';
#messages in room
	SELECT r.server_id, m.room_id, m.id, m.user_id, m.sendedat, m.msg, u.name FROM messages m LEFT JOIN user u ON(m.user_id = u.id) JOIN room r ON(r.id = m.room_id) WHERE room_id = '{room_id}' ORDER BY m.sendedat DESC;
	#before
		SELECT r.server_id, m.room_id, m.id, m.user_id, m.sendedat, m.msg, u.name FROM messages m LEFT JOIN user u ON(m.user_id = u.id) JOIN room r ON(r.id = m.room_id) WHERE room_id = '{room_id}' AND m.sendedat < '{date}' ORDER BY m.sendedat DESC;
	#after
		SELECT r.server_id, m.room_id, m.id, m.user_id, m.sendedat, m.msg, u.name FROM messages m LEFT JOIN user u ON(m.user_id = u.id) JOIN room r ON(r.id = m.room_id) WHERE room_id = '{room_id}' AND m.sendedat > '{date}' ORDER BY m.sendedat DESC;
#get user expect
	SELECT u.id, u.name FROM server_user su JOIN user u ON(u.id = su.user_id) WHERE su.server_id = '{user_id}' AND u.id NOT IN {expect_user_id};
#user
	SELECT name FROM user WHERE id = '{user_id}';
#is owner
	SELECT TRUE FROM server WHERE id='{server_id}' AND owner = '{user_id}';

#----------------------------------DELETE----------------------------------
#Remove user from Server
	DELETE FROM server_user WHERE server_id = '{server_id}' AND user_id = '{user_id}';
#User
	DELETE FROM user WHERE id = '{user_id}';
#Role
	DELETE FROM role WHERE id = '{role_id}';
#Room
	DELETE FROM room WHERE id = '{role_id}';
#Server
	DELETE FROM server WHERE id = '{server_id}';

#----------------------------------UPDATE----------------------------------
#Privileges
	UPDATE room_role SET cansee = '{valueCanSee}' AND canwrite = '{valueCanWrite}' AND canread = '{valueCanRead}' WHERE role_id = '{role_id}' AND room_id = '{room_id}';
#lastlogout
	UPDATE user SET lastlogout = NOW() WHERE id = '{user_id}';