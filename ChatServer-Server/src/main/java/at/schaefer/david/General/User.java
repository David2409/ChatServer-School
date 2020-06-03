package at.schaefer.david.General;

import at.schaefer.david.Communication.DTO.DTOServer;
import at.schaefer.david.Communication.DTO.DTOUser;
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
import java.sql.*;

public class User {
    public Server[] servers;
    public WebSocket connection;
    public String name;
    public String lastLogout;
    public long id;

    protected User(WebSocket iConnection, long iId, String iName, String iLastLogout) throws SQLException, InvalidOperationException, JsonProcessingException {
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
        connection.send(new DTOResponse<DTOUser>(ResponseType.LOGGED_IN, new DTOUser(name)).toJSON());
        connection.send(new DTOResponse<DTOServer[]>(ResponseType.SERVER_MAP, DTOServer.GetDTOServerArray(servers, this)).toJSON());
    }

    public static User GetUser(WebSocket connection, String username, String password) throws SQLException, InvalidUserException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidOperationException, JsonProcessingException {
        PreparedStatement statement = Global.conDatabase.prepareStatement("SELECT id, lastlogout FROM user WHERE name = ? AND password = ?;");
        statement.setString(1, username);
        statement.setString(2, Global.HashPassword(password));
        ResultSet rs = statement.executeQuery();
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

    public void CreateServer(String name) throws InvalidOperationException, SQLException, JsonProcessingException {
        Server server = Server.CreateServer(name, this);
        server.AddUser(this);
        server.JoinSession(this);
        AddServer(server);
        connection.send(new DTOResponse<DTOServer>(ResponseType.NEW_SERVER, DTOServer.GetDTOServer(server, this)).toJSON());
    }

    public void CreateRoom(long serverId, String name) throws SQLException, InvalidOperationException {
        Server server = GetServer(serverId);
        if(CanModify(serverId)){
            server.CreateRoom(name);
        }
        else{
            throw new InvalidOperationException();
        }
    }

    public static User CreateUser(WebSocket connection, String username, String password) throws NoSuchAlgorithmException, SQLException, InvalidUserException, UnsupportedEncodingException, InvalidOperationException, JsonProcessingException {
        PreparedStatement statement = Global.conDatabase.prepareStatement("INSERT INTO user (`name`,`password`) VALUES (?,?);");
        statement.setString(1, username);
        statement.setString(2, Global.HashPassword(password));
        statement.execute();
        statement.close();
        return GetUser(connection, username, password);
    }

    public void LogOut() throws SQLException, JsonProcessingException {
        for (Server server: servers) {
            server.RemoveFromSession(this);
        }
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("UPDATE user SET lastlogout = NOW() WHERE id = '" + this.id + "';");
        statement.close();
        connection.send(new DTOResponse(ResponseType.LOGGED_OUT, null).toJSON());
    }

    public void DeleteUser() throws SQLException, JsonProcessingException {
        LogOut();
        for (Server server: servers) {
            server.RemoveUser(this.id);
        }
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("DELETE FROM user WHERE id = '" + id + "';");
        statement.close();
    }

    public boolean CanModify(long serverId) throws SQLException { //do to
        if(IsOwner(serverId)){
            return true;
        }
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT TRUE FROM user_role ur JOIN role r ON (r.id = ur.role_id) WHERE r.server_id = '" + serverId + "' AND ur.user_id = '" + this.id +"' AND r.canchange = TRUE;");
        boolean can = rs.next();
        statement.close();
        return can;
    }

    public boolean CanInvite(long serverId) throws SQLException {
        if(IsOwner(serverId)){
            return true;
        }
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT TRUE FROM user_role ur JOIN role r ON (r.id = ur.role_id) WHERE r.server_id = '" + serverId + "' AND ur.user_id = '" + this.id +"' AND r.caninvite = TRUE;");
        boolean can = rs.next();
        statement.close();
        return can;
    }

    public boolean CanSend(long serverId, long roomId) throws SQLException {
        if(IsOwner(serverId)){
            return true;
        }
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT FALSE FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id AND rr.room_id = '" + roomId + "' AND ur.user_id = '" + this.id + "') GROUP BY rr.room_id HAVING SUM(rr.canwrite) = '0';");
        boolean can = !rs.next();
        statement.close();
        return can;
    }

    public boolean CanRead(long serverId, long roomId) throws SQLException {
        if(IsOwner(serverId)){
            return true;
        }
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT FALSE FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id AND rr.room_id = '" + roomId + "' AND ur.user_id = '" + this.id + "') GROUP BY rr.room_id HAVING SUM(rr.canread) = '0';");
        boolean can = !rs.next();
        statement.close();
        return can;
    }

    public boolean CanSee(long serverId, long roomId) throws SQLException {
        if(IsOwner(serverId)){
            return true;
        }
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT FALSE FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id AND rr.room_id = '" + roomId + "' AND ur.user_id = '" + this.id + "') GROUP BY rr.room_id HAVING SUM(rr.cansee) = '0';");
        boolean can = !rs.next();
        statement.close();
        return can;
    }

    public boolean IsOwner(long serverId) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT TRUE FROM server WHERE id='" + serverId + "' AND owner = '" + this.id + "';");
        boolean erg = rs.next();
        statement.close();
        return erg;
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


    private int GetInsertionPoint(long insertId){
        if(servers.length == 0){
            return 0;
        }
        for(int i = 0; i < servers.length; i++){
            if(servers[i].id > insertId){
                return i;
            }
        }
        return servers.length;
    }

    private void AddServer(Server s){
        synchronized (servers){
            Server[] newServers = new Server[servers.length+1];
            int insertPoint = GetInsertionPoint(s.id);
            int pointOld = 0;
            for(int i = 0; i < newServers.length; i++) {
                if(i == insertPoint) {
                    newServers[i] = s;
                }
                else {
                    newServers[i] = servers[pointOld];
                    pointOld++;
                }
            }
            servers = newServers;
        }
    }

    public void RemoveServer(Server s){
        synchronized (servers){
            Server[] newServers = new Server[servers.length-1];
            for(int i = 0; i < newServers.length; i++) {
                if(servers[i].id == s.id){
                    i--;
                } else{
                    servers[i] = servers[i];
                }
            }
            servers = newServers;
        }
    }
}