package at.schaefer.david.General;

import at.schaefer.david.Communication.DTO.DTOMessage;
import at.schaefer.david.Communication.Responses.DTOResponse;
import at.schaefer.david.Communication.Responses.ResponseType;
import at.schaefer.david.Exceptions.InvalidMessageException;
import at.schaefer.david.Exceptions.InvalidOperationException;
import at.schaefer.david.Exceptions.InvalidUserException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Room {
    public long id;
    public String name;
    public List<User> activeUsers;
    private Server server;

    protected Room(long iId, Server iServer){
        id = iId;
        server = iServer;
        activeUsers = new ArrayList<User>();
    }

    private void init() throws SQLException, InvalidOperationException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT name FROM room WHERE id = '" + id + "';");
        if(rs.next()){
            name = rs.getString(1);
        }else{
            throw new InvalidOperationException();
        }
    }

    public static Room Get(long id, Server server) throws SQLException, InvalidOperationException {
        Room room = new Room(id, server);
        room.init();
        return room;
    }

    public void Emit(User from, String msg) throws SQLException, InvalidMessageException, JsonProcessingException, InvalidOperationException {
        if(from == null){
            throw new InvalidOperationException();
        }
        DTOMessage message = new DTOMessage(server, this, from, msg);
        message.InsertIntoDTB();
        DTOResponse response = new DTOResponse(ResponseType.NEW_MESSAGE, message);
        synchronized (activeUsers){
            for(User user : activeUsers){
                user.GetConnection().send(response.toJSON());
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

    public DTOMessage[] GetMessagesBefore(String time) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT r.server_id, m.room_id, m.id, m.user_id, m.sendedat, m.msg, u.name FROM messages m LEFT JOIN user u ON(m.user_id = u.id) JOIN room r ON(r.id = m.room_id) WHERE room_id = '" + this.id + "' AND m.sendedat < '" + time + "' ORDER BY m.sendedat DESC LIMIT 100;");
        DTOMessage[] erg = DTOMessage.GetArray(rs);
        statement.close();
        return erg;
    }

    public DTOMessage[] GetMessagesAfter(String time) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT r.server_id, m.room_id, m.id, m.user_id, m.sendedat, m.msg, u.name FROM messages m LEFT JOIN user u ON(m.user_id = u.id) JOIN room r ON(r.id = m.room_id) WHERE room_id = '" + this.id + "' AND m.sendedat > '" + time + "' ORDER BY m.sendedat DESC");
        DTOMessage[] erg = DTOMessage.GetArray(rs);
        statement.close();
        return erg;
    }
}