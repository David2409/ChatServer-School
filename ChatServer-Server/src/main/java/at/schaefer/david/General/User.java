package at.schaefer.david.General;

import at.schaefer.david.Exceptions.InvalidUserException;
import org.java_websocket.WebSocket;
import sun.security.action.GetLongAction;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    public Server[] servers;
    public WebSocket connection;
    public String name;
    public long id;

    protected User(WebSocket iConnection, long iId, String iName) throws SQLException {
        connection = iConnection;
        id = iId;
        name = iName;
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("Select server_id FROM server_user WHERE user_id = '" + id + "';");
        rs.last();
        servers = new Server[rs.getRow()];
        rs.first();
        for(int i = 0; i < servers.length; i++){
            servers[i] = Server.GetServer(rs.getLong(1));
            servers[i].JoinSession(this);
            rs.next();
        }
    }

    public static User GetUser(WebSocket connection, String username, String password) throws SQLException, InvalidUserException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT id FROM user WHERE name = '" + username + "' AND password = '" + Global.HashPassword(password) + "';");
        boolean result = rs.next();
        long id = 0;
        if(result){
            id = rs.getLong(1);
        }
        statement.close();
        if(result){
            return new User(connection, id, username);
        }
        throw new InvalidUserException();
    }

    public static User CreateUser(WebSocket connection, String username, String password) throws NoSuchAlgorithmException, SQLException, InvalidUserException, UnsupportedEncodingException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("INSERT INTO user (`name`,`password`) VALUES ('" + username + "','" + Global.HashPassword(password) + "');");
        statement.close();
        return GetUser(connection, username, password);
    }

    public void Disconnect(){
        for (Server server: servers) {
            server.RemoveFromSession(this);
        }
        connection.close();
    }

    public void DeleteUser() throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("DELETE FROM user WHERE id = '" + id + "';");
        statement.close();
    }

    public static boolean CanModify(long userId, long serverId) throws SQLException { //do to
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id) WHERE ur.user_id = '" + userId + "' AND rr.room_id = '" + serverId + "' AND rr.canwrite = TRUE;");
        rs.next();
        boolean can = rs.getInt(1) != 0;
        statement.close();
        return can;
    }

    public static boolean CanSend(long userId, long roomId) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT FALSE FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id AND rr.room_id = '" + roomId + "' AND ur.user_id = '" + userId + "') GROUP BY rr.room_id HAVING SUM(rr.canwrite) = '0';");
        boolean can = !rs.next();
        statement.close();
        return can;
    }

    public static void main(String[] args) throws SQLException, IOException, NoSuchAlgorithmException, InvalidUserException {
        Global.init();
        System.out.println(Global.HashPassword("Hallo").length());
        User user = User.CreateUser(null, "testuser", "test");
        Server server = Server.CreateServer("TestServer");
        server.AddUser(user.id);
        long roleId = Role.Create("TestRole", server.id);
        long roomId = Room.Create(server.id, "Test Room");
        Role.Change(server.id, new String[] {roleId + "," + roomId + ",TRUE,TRUE,TRUE"});
        server.JoinSession(user);
    }
}