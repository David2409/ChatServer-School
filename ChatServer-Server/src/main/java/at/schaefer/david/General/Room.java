package at.schaefer.david.General;

import at.schaefer.david.DTO.DTOMessage;
import at.schaefer.david.DTO.DTOResponse;
import at.schaefer.david.DTO.ResponseType;
import at.schaefer.david.Exceptions.InvalidMessageException;
import at.schaefer.david.Exceptions.InvalidOperationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Room {
    public long id;
    private List<User> activeUsers;
    private Server server;

    protected Room(long iId, Server iServer){
        id = iId;
        server = iServer;
        activeUsers = new ArrayList<User>();
    }

    public static Room Get(long id, Server server) throws SQLException {
        return new Room(id, server);
    }

    public void Emit(User from, String msg) throws SQLException, InvalidMessageException, JsonProcessingException, InvalidOperationException {
        if(from == null || User.CanSend(from.id, this.id) == false){
            throw new InvalidOperationException();
        }
        DTOMessage message = new DTOMessage(server, this, from, msg);
        message.InsertIntoDTB();
        DTOResponse response = new DTOResponse(ResponseType.NEW_MESSAGE, message);
        synchronized (activeUsers){
            for(User user : activeUsers){
                user.connection.send(response.toJSON());
            }
        }
    }

    public synchronized void Add(User u){
        activeUsers.add(u);
    }

    public synchronized void Remove(User u){
        activeUsers.remove(u);
    }

    public static long Create(long serverId, String name) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("INSERT INTO room (`server_id`, `name`) VALUES ('" + serverId + "','" + name + "');", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        long id = rs.getLong(1);
        statement.close();
        return id;
    }

    public void Delete() throws SQLException, InvalidOperationException {
        int index = server.GetRoomIndex(this.id);
        if(index == -1){
            throw new InvalidOperationException();
        }
        server.RemoveRoom(index);
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("DELETE FROM room WHERE id = '" + this.id + "';");
        statement.close();
    }
}