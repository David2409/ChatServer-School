package at.schaefer.david.General;

import at.schaefer.david.Exceptions.InvalidUserException;
import org.java_websocket.WebSocket;
import sun.security.action.GetLongAction;

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
        ResultSet rs = statement.executeQuery("Select id FROM server_user WHERE user_id = '" + id + "';");
        rs.last();
        servers = new Server[rs.getRow()];
        rs.first();
        for(int i = 0; i < servers.length; i++){
            servers[i] = Server.GetServer(rs.getLong(1));
            servers[i].JoinSession(this);
            rs.next();
        }
    }

    public static User GetUser(WebSocket connection, String username, String password) throws SQLException, InvalidUserException, NoSuchAlgorithmException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("Select id FROM user WHERE name = '" + username + "' AND password = '" + Global.HashString(password) + "';");
        if(rs.next()){
            return new User(connection, rs.getLong(1), username);
        }
        throw new InvalidUserException();
    }

    public static User CreateUser(WebSocket connection, String username, String password) throws NoSuchAlgorithmException, SQLException, InvalidUserException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("Select id FROM user WHERE name = '" + username + "' AND password = '" + Global.HashString(password) + "';");
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
}