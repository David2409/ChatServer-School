package at.schaefer.david.General;

import at.schaefer.david.DTO.DTOMessage;
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

    public Room(long iId, Server iServer){
        id = iId;
        server = iServer;
        activeUsers = new ArrayList<User>();
    }

    public static Room Create(String name, Server server) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("INSERT INTO room (`server_id`, `name`) VALUES ('" + server.id + "','" + name + "');", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        long id = rs.getLong(1);
        statement.close();
        return new Room(id, server);
    }

    public void Emit(User from, String msg) throws SQLException, InvalidMessageException, JsonProcessingException, InvalidOperationException {
        if(User.CanSend(from.id, this.id) == false){
            throw new InvalidOperationException();
        }
        DTOMessage message = new DTOMessage(server.id, this.id, from.id, msg);
        message.InsertIntoDTB();
        String toSend = message.toJSON();
        synchronized (activeUsers){
            for(User user : activeUsers){
                user.connection.send(toSend);
            }
        }
    }

    public synchronized void Add(User u){
        activeUsers.add(u);
    }

    public synchronized void Remove(User u){
        activeUsers.remove(u);
    }
}