package at.schaefer.david.General;

import at.schaefer.david.Communication.Requests.DTORequest;
import at.schaefer.david.Communication.Responses.DTOResponse;
import at.schaefer.david.Communication.Responses.ResponseType;
import at.schaefer.david.Exceptions.InvalidMessageException;
import at.schaefer.david.Exceptions.InvalidOperationException;
import at.schaefer.david.Exceptions.InvalidUserException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.java_websocket.WebSocket;
import sun.security.action.GetLongAction;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    public Server[] servers;
    private WebSocket connection;
    public String name;
    public String lastLogout;
    public long id;

    protected User(WebSocket iConnection, long iId, String iName, String iLastLogout) throws SQLException, InvalidOperationException {
        connection = iConnection;
        id = iId;
        name = iName;
        lastLogout = iLastLogout;
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

    public static User GetUser(WebSocket connection, String username, String password) throws SQLException, InvalidUserException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidOperationException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT id, lastlogout FROM user WHERE name = '" + username + "' AND password = '" + Global.HashPassword(password) + "';");
        boolean result = rs.next();
        long id = 0;
        String lastlogout = "";
        if(result){
            id = rs.getLong(1);
            lastlogout = rs.getString(2);
        }
        statement.close();
        if(result){
            return new User(connection, id, username, lastlogout);
        }
        throw new InvalidUserException();
    }

    public static User CreateUser(WebSocket connection, String username, String password) throws NoSuchAlgorithmException, SQLException, InvalidUserException, UnsupportedEncodingException, InvalidOperationException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("INSERT INTO user (`name`,`password`) VALUES ('" + username + "','" + Global.HashPassword(password) + "');");
        statement.close();
        return GetUser(connection, username, password);
    }

    public void LogOut() throws SQLException {
        for (Server server: servers) {
            server.RemoveFromSession(this);
        }
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("UPDATE user SET lastlogout = NOW() WHERE id = '" + this.id + "';");
        statement.close();
    }

    public void DeleteUser() throws SQLException {
        for (Server server: servers) {
            server.RemoveUser(this.id);
        }
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("DELETE FROM user WHERE id = '" + id + "';");
        statement.close();
    }

    public boolean CanModify(long serverId) throws SQLException { //do to
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT FALSE FROM user_role ur JOIN role r ON(ur.role_id = r.id AND r.server_id = '" + serverId + "' AND ur.user_id = '" + this.id + "') HAVING SUM(r.canchange) = '0';");
        rs.next();
        boolean can = !rs.next();
        statement.close();
        return can;
    }

    public boolean CanSend(long roomId) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT FALSE FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id AND rr.room_id = '" + roomId + "' AND ur.user_id = '" + this.id + "') GROUP BY rr.room_id HAVING SUM(rr.canwrite) = '0';");
        boolean can = !rs.next();
        statement.close();
        return can;
    }

    public boolean CanRead(long roomId) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT FALSE FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id AND rr.room_id = '" + roomId + "' AND ur.user_id = '" + this.id + "') GROUP BY rr.room_id HAVING SUM(rr.canread) = '0';");
        boolean can = !rs.next();
        statement.close();
        return can;
    }

    public boolean CanSee(long roomId) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT FALSE FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id AND rr.room_id = '" + roomId + "' AND ur.user_id = '" + this.id + "') GROUP BY rr.room_id HAVING SUM(rr.cansee) = '0';");
        boolean can = !rs.next();
        statement.close();
        return can;
    }

    public WebSocket GetConnection(){
        return connection;
    }

    public void SendMessage(long serverId, long RoomId, String msg) throws InvalidOperationException, JsonProcessingException, SQLException, InvalidMessageException {
        Server server = GetServer(serverId);
        if(server == null){
            throw new InvalidOperationException();
        }
        server.Emit(this, RoomId, msg);
    }

    public void JoinServer(Server s) throws SQLException {
        s.AddUser(this);
        s.JoinSession(this);
    }

    public Server GetServer(long id){
        int left = 0;
        int right = servers.length;
        int center = 0;
        do {
            center = (left + right) / 2;
            if(servers[center].id == id){
                return servers[center];
            }
            else if(servers[center].id < id){
                left = center;
            }
            else{
                right = center;
            }
        } while(center != (left + right) / 2);
        return null;
    }

    /*
    private int GetInsertionPoint(){
        for(int i = 0; i < servers.length){
            if(servers[i])
        }
    }*/

    private void AddServer(Server s){

        synchronized (servers){
        }
    }

    public static void main(String[] args) throws SQLException, IOException, NoSuchAlgorithmException, InvalidUserException, InvalidOperationException {
        Global.init();
        System.out.println(Global.HashPassword("Hallo").length());
        User user = User.CreateUser(null, "testuser", "test");
        Server server = Server.CreateServer("TestServer");
        System.out.println(user.id);
        System.out.println(server.id);
        server.AddUser(user);
        long roleId = Role.Create("TestRole", server.id);
        long roomId = Room.Create(server.id, "Test Room");
        Role.Change(server.id, new String[] {roleId + "," + roomId + ",1,1,1"});
        server.JoinSession(user);
        DTOResponse response = new DTOResponse(ResponseType.SERVER_MAP, user.servers);
        System.out.println(response.toJSON());
        System.out.println("Finished");
    }
}