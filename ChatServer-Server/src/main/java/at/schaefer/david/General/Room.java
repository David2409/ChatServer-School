package at.schaefer.david.General;

import at.schaefer.david.Communication.DTO.*;
import at.schaefer.david.Communication.Responses.DTOResponse;
import at.schaefer.david.Communication.Responses.ResponseType;
import at.schaefer.david.Exceptions.InvalidMessageException;
import at.schaefer.david.Exceptions.InvalidOperationException;
import at.schaefer.david.Exceptions.InvalidUserException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.deploy.util.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room {
    public long id;
    public String name;
    public List<User> activeUsers;
    public Server server;

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

    public void Update(DTODataRoom data) throws SQLException, JsonProcessingException {
        Statement statement = Global.conDatabase.createStatement();
        if(name != data.name){
            this.name = data.name;
            statement.execute("Update room Set name = '" + this.name + "' WHERE id = '" + this.id + "';");
            server.Emit(new DTOResponse<DTOUpdateValue>(ResponseType.CHANGED_ROOM_NAME, DTOUpdateValue.GetDTOUpdateValue(Long.toString(server.id), Long.toString(this.id), this.name)));
        }

        for (DTORoleRoom role: data.roles) {
            ResultSet rs = statement.executeQuery("SELECT * FROM room_role WHERE room_id = '" + this.id + "' AND role_id = '" + role.id + "';");
            if(rs.next()){
                statement.execute("Update room_role SET cansee = " + role.cansee + ", canwrite = " + role.canwrite + ", canread = " + role.canread + " WHERE room_id = '" + this.id + "' AND role_id = '" + role.id + "';");
            }
            else{
                statement.execute("INSERT INTO room_role (`room_id`,`role_id`,`cansee`,`canwrite`,`canread`) VALUES ('" + this.id + "','" + role.id + "'," + role.cansee + "," + role.canwrite + "," + role.canread + ");");
            }
        }
        Reload();
    }

    public void Reload() throws SQLException, JsonProcessingException {
        for (User u: this.activeUsers) {
            if(!u.CanSee(this.server.id, this.id)){
                u.connection.send(new DTOResponse<DTOGeneral>(ResponseType.DELETED_ROOM, DTOGeneral.GetDTORemoveRoom(Long.toString(this.server.id), Long.toString(this.id))).toJSON());
            }
        }
        for (int i = 0; i < this.activeUsers.size(); i++) {
            User u = this.activeUsers.get(i);
            if(!u.CanRead(this.server.id, this.id)){
                this.activeUsers.remove(u);
                i--;
            }
        }

        for (User u: this.server.onlineUsers) {
            if(u.CanSee(this.server.id, this.id) && !this.activeUsers.contains(u)){
                u.connection.send(new DTOResponse<DTORoom>(ResponseType.NEW_ROOM, DTORoom.GetDTORoom(this, Long.toString(this.server.id))).toJSON());
            }
        }
        for (int i = 0; i < this.server.onlineUsers.size(); i++) {
            User u = this.activeUsers.get(i);
            if(u.CanRead(this.server.id, this.id) && !this.activeUsers.contains(u)){
                Add(u);
            }
        }
    }

    public DTOMessage[] GetMessagesBefore(String time) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT r.server_id, m.room_id, m.id, m.user_id, m.sendedat, m.msg, u.name FROM messages m LEFT JOIN user u ON(m.user_id = u.id) JOIN room r ON(r.id = m.room_id) WHERE room_id = '" + this.id + "' AND m.sendedat < '" + time + "' ORDER BY m.sendedat DESC LIMIT 100;");
        DTOMessage[] erg = DTOMessage.GetArray(rs);
        statement.close();
        ArrayUtils.reverse(erg);
        return erg;
    }

    public DTOMessage[] GetMessagesAfter(String time) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT r.server_id, m.room_id, m.id, m.user_id, m.sendedat, m.msg, u.name FROM messages m LEFT JOIN user u ON(m.user_id = u.id) JOIN room r ON(r.id = m.room_id) WHERE room_id = '" + this.id + "' AND m.sendedat > '" + time + "' ORDER BY m.sendedat DESC");
        DTOMessage[] erg = DTOMessage.GetArray(rs);
        statement.close();
        ArrayUtils.reverse(erg);
        return erg;
    }
}