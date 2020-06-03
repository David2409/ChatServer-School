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

import java.sql.PreparedStatement;
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
            statement.close();
            throw new InvalidOperationException();
        }
        statement.close();
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
        PreparedStatement statement = Global.conDatabase.prepareStatement("INSERT INTO room (`server_id`, `name`) VALUES (?,?);", Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, serverId);
        statement.setString(2, name);
        statement.execute();
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
        if(name != data.name){
            this.name = data.name;
            PreparedStatement statement = Global.conDatabase.prepareStatement("Update room Set name = ? WHERE id = ?;");
            statement.setString(1, this.name);
            statement.setLong(2, this.id);
            statement.execute();
            statement.close();
            server.Emit(new DTOResponse<DTOUpdateValue>(ResponseType.CHANGED_ROOM_NAME, DTOUpdateValue.GetDTOUpdateValue(Long.toString(server.id), Long.toString(this.id), this.name)));
        }

        for (DTORoleRoom role: data.roles) {
            PreparedStatement statementHas = Global.conDatabase.prepareStatement("SELECT * FROM room_role WHERE room_id = ? AND role_id = ?;");
            statementHas.setLong(1, this.id);
            statementHas.setLong(2, Long.valueOf(role.id));
            ResultSet rs = statementHas.executeQuery();
            if(rs.next()){
                PreparedStatement statement = Global.conDatabase.prepareStatement("Update room_role SET cansee = ?, canwrite = ?, canread = ? WHERE room_id = ? AND role_id = ?;");
                statement.setBoolean(1, role.cansee);
                statement.setBoolean(2, role.canwrite);
                statement.setBoolean(3, role.canread);
                statement.setLong(4, this.id);
                statement.setLong(5, Long.valueOf(role.id));
                statement.execute();
                statement.close();
            }
            else{
                PreparedStatement statement = Global.conDatabase.prepareStatement("INSERT INTO room_role (`room_id`,`role_id`,`cansee`,`canwrite`,`canread`) VALUES (?,?,?,?,?);");
                statement.setLong(1,this.id);
                statement.setLong(2,Long.valueOf(role.id));
                statement.setBoolean(3,role.cansee);
                statement.setBoolean(4,role.canwrite);
                statement.setBoolean(5,role.canread);
                statement.execute();
            }
            statementHas.close();
        }
        Reload();
    }

    public void Reload() throws SQLException, JsonProcessingException {
        /*for (User u: this.activeUsers) {
            if(!u.CanSee(this.server.id, this.id)){
                u.connection.send(new DTOResponse<DTOGeneral>(ResponseType.DELETED_ROOM, DTOGeneral.GetDTORemoveRoom(Long.toString(this.server.id), Long.toString(this.id))).toJSON());
            }
        }*/
        for (int i = 0; i < this.activeUsers.size(); i++) {
            User u = this.activeUsers.get(i);
            if(!u.CanRead(this.server.id, this.id)){
                this.activeUsers.remove(u);
                i--;
            }
        }

        for (User u: this.server.onlineUsers) {
            if(u.CanSee(this.server.id, this.id)){
                u.connection.send(new DTOResponse<DTORoom>(ResponseType.NEW_ROOM, DTORoom.GetDTORoom(this, Long.toString(this.server.id))).toJSON());
            } else {
                u.connection.send(new DTOResponse<DTOGeneral>(ResponseType.DELETED_ROOM, DTOGeneral.GetDTORemoveRoom(Long.toString(this.server.id), Long.toString(this.id))).toJSON());
            }
        }
        for (int i = 0; i < this.server.onlineUsers.size(); i++) {
            User u = this.server.onlineUsers.get(i);
            if(u.CanRead(this.server.id, this.id) && !this.activeUsers.contains(u)){
                Add(u);
            }
        }
    }

    public DTOMessage[] GetMessagesBefore(String time) throws SQLException {
        PreparedStatement statement = Global.conDatabase.prepareStatement("SELECT r.server_id, m.room_id, m.id, m.user_id, m.sendedat, m.msg, u.name FROM messages m LEFT JOIN user u ON(m.user_id = u.id) JOIN room r ON(r.id = m.room_id) WHERE room_id = ? AND m.sendedat < ? ORDER BY m.sendedat DESC LIMIT 100;");
        statement.setLong(1, this.id);
        statement.setString(2, time);
        ResultSet rs = statement.executeQuery();
        DTOMessage[] erg = DTOMessage.GetArray(rs);
        statement.close();
        ArrayUtils.reverse(erg);
        return erg;
    }

    public DTOMessage[] GetMessagesAfter(String time) throws SQLException {
        PreparedStatement statement = Global.conDatabase.prepareStatement("SELECT r.server_id, m.room_id, m.id, m.user_id, m.sendedat, m.msg, u.name FROM messages m LEFT JOIN user u ON(m.user_id = u.id) JOIN room r ON(r.id = m.room_id) WHERE room_id = ? AND m.sendedat > ? ORDER BY m.sendedat DESC");
        statement.setLong(1, this.id);
        statement.setString(2, time);
        ResultSet rs = statement.executeQuery();
        DTOMessage[] erg = DTOMessage.GetArray(rs);
        statement.close();
        ArrayUtils.reverse(erg);
        return erg;
    }
}