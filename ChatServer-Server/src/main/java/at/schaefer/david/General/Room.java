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

    public void Emit(User from, String msg) throws SQLException, InvalidMessageException, JsonProcessingException, InvalidOperationException {
        if(CanSend(from.id) == false){
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

    private boolean CanSend(long userId) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id) WHERE ur.user_id = '" + userId + "' AND rr.room_id = '" + this.id + "' AND rr.canwrite = TRUE;");
        rs.next();
        boolean can = rs.getInt(1) != 0;
        statement.close();
        return can;
    }
}