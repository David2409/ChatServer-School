package at.schaefer.david.General;

import at.schaefer.david.Communication.DTO.*;
import at.schaefer.david.Communication.Responses.DTOResponse;
import at.schaefer.david.Communication.Responses.ResponseType;
import at.schaefer.david.Exceptions.InvalidMessageException;
import at.schaefer.david.Exceptions.InvalidOperationException;
import at.schaefer.david.Exceptions.InvalidUserException;
import at.schaefer.david.Structure.IIndex;
import at.schaefer.david.Structure.Tree;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.nio.ByteBuffer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server implements IIndex {
    public long id;
    public String name;
    public Room[] rooms;
    private boolean init;
    protected static Tree servers  = new Tree(8);
    public List<User> onlineUsers;
    protected long notificationRoomId;
    protected long ownerId;

    protected Server(long iId)
    {
        id = iId;
        onlineUsers = new ArrayList<User>(1);
    }

    public static Server GetServer(long id) throws SQLException, InvalidOperationException {
        Server server = (Server) servers.Add(new Server(id));
        synchronized (server){
            if(server.init == false){
                server.init();
            }
        }
        return server;
    }

    public static Server CreateServer(String name, User user) throws SQLException, InvalidOperationException {
        PreparedStatement statement = Global.conDatabase.prepareStatement("INSERT INTO server (`name`, `owner`) VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, name);
        statement.setLong(2, user.id);
        statement.execute();
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        long id = rs.getLong(1);
        statement.close();
        return GetServer(id);
    }

    protected void init() throws SQLException, InvalidOperationException {
        PreparedStatement statementRooms = Global.conDatabase.prepareStatement("SELECT id FROM room WHERE server_id = ? ORDER BY id;");
        statementRooms.setLong(1, this.id);
        ResultSet rsRooms = statementRooms.executeQuery();
        rsRooms.last();
        rooms = new Room[rsRooms.getRow()];
        rsRooms.first();
        for(int i = 0; i < rooms.length; i++){
            rooms[i] = Room.Get(rsRooms.getLong(1), this);
            rsRooms.next();
        }
        statementRooms.close();
        PreparedStatement statementNotification = Global.conDatabase.prepareStatement("SELECT notificationroom FROM server WHERE id = ? AND notificationroom IS NOT NULL;");
        statementNotification.setLong(1, this.id);
        ResultSet rs = statementNotification.executeQuery();
        if(rs.next()){
            notificationRoomId = rs.getLong(1);
        }
        else{
            if(rooms.length != 0){
                notificationRoomId = rooms[0].id;
            }
            else{
                notificationRoomId = 0;
            }
        }
        statementNotification.close();
        PreparedStatement statement = Global.conDatabase.prepareStatement("SELECT name, owner FROM server WHERE id = ?;");
        statement.setLong(1, this.id);
        rs = statement.executeQuery();
        rs.next();
        name = rs.getString(1);
        ownerId = rs.getLong(2);
        statement.close();
        init = true;
    }

    public void Delete() throws SQLException {
        PreparedStatement statement = Global.conDatabase.prepareStatement("DELETE FROM server WHERE id = ?;");
        statement.setLong(1, this.id);
        statement.execute();
        statement.close();
        Emit(new DTOResponse<DTOGeneral>(ResponseType.DELETED_SERVER, DTOGeneral.GetDTORemoveServer(Long.toString(this.id))));
    }

    public void JoinSession(User user) throws SQLException {
        PreparedStatement statement = Global.conDatabase.prepareStatement("SELECT DISTINCT r.id FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id) JOIN role r ON (rr.room_id = r.id) WHERE ur.user_id = ? AND r.server_id = ? GROUP BY rr.room_id HAVING SUM(rr.canread) = '0' ORDER BY r.id;");
        statement.setLong(1, user.id);
        statement.setLong(2, this.id);
        ResultSet rsRooms = statement.executeQuery();
        Emit(new DTOResponse<DTOGeneral>(ResponseType.ONLINE_USER, DTOGeneral.GetDTORemoveUser(Long.toString(this.id), Long.toString(user.id))));
        synchronized (onlineUsers){
            onlineUsers.add(user);
        }
        AddToRooms(user, GetLongArray(rsRooms, 1));
    }

    public void RemoveFromSession(User u){
        for (Room room: rooms) {
            room.Remove(u);
        }
        synchronized (onlineUsers){
            onlineUsers.remove(u);
            if(onlineUsers.size() == 0){
                servers.Remove(this);
                u.RemoveServer(this);
            }
        }
        Emit(new DTOResponse<DTOGeneral>(ResponseType.OFFLINE_USER, DTOGeneral.GetDTORemoveUser(Long.toString(this.id), Long.toString(u.id))));
    }

    public void AddUser(User user) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("INSERT INTO server_user (`server_id`,`user_id`) VALUES ('" + this.id + "','" + user.id + "');");
        ResultSet rs = statement.executeQuery("SELECT name FROM user WHERE id = '" + user.id + "';");
        rs.next();
        String username = rs.getString(1);
        statement.close();
        EmitServerMessage("User :" + username + ": has join the Server");
        Emit(new DTOResponse<DTOUserContainer>(ResponseType.ADDED_USER, DTOUserContainer.GetDTOUserContainer(Long.toString(this.id), user.id)));
    }

    public void AddUser(long userId) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("INSERT INTO server_user (`server_id`,`user_id`) VALUES ('" + this.id + "','" + userId + "');");
        ResultSet rs = statement.executeQuery("SELECT name FROM user WHERE id = '" + userId + "';");
        rs.next();
        String username = rs.getString(1);
        statement.close();
        EmitServerMessage("User :" + username + ": has join the Server");
        Emit(new DTOResponse<DTOUserContainer>(ResponseType.ADDED_USER, DTOUserContainer.GetDTOUserContainer(Long.toString(this.id), userId)));
    }

    public void AddUser(String username) throws InvalidUserException, SQLException {
        PreparedStatement statement = Global.conDatabase.prepareStatement("SELECT id FROM user WHERE name = ?;");
        statement.setString(1, username);
        ResultSet rs = statement.executeQuery();
        if(!rs.next()){
            throw new InvalidUserException();
        }
        long id = rs.getLong(1);
        statement.close();
        AddUser(id);
    }

    public void RemoveUser(long userId) throws SQLException, JsonProcessingException {
        User removedUser = GetUser(userId);
        if(removedUser != null){
            RemoveFromSession(removedUser);
            removedUser.connection.send(new DTOResponse<DTOGeneral>(ResponseType.DELETED_SERVER, DTOGeneral.GetDTORemoveServer(Long.toString(this.id))).toJSON());
        }
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("DELETE FROM server_user WHERE server_id = '" + this.id + "' AND user_id = '" + userId + "';");
        ResultSet rs = statement.executeQuery("SELECT name FROM user WHERE id = '" + userId + "';");
        rs.next();
        String username = rs.getString(1);
        statement.close();
        EmitServerMessage("User :" + username + ": has left the Server");
        Emit(new DTOResponse<DTOGeneral>(ResponseType.REMOVED_USER, DTOGeneral.GetDTORemoveUser(Long.toString(this.id), Long.toString(userId))));
    }

    public void CreateRoom(String name) throws SQLException, InvalidOperationException {
        long roomId = Room.Create(this.id, name);
        Room room = Room.Get(roomId, this);
        for(User user: onlineUsers){
            room.Add(user);
        }
        PutRoom(room);
        if(rooms.length == 1){
            notificationRoomId = rooms[0].id;
        }
        Emit(new DTOResponse<DTORoom>(ResponseType.NEW_ROOM, DTORoom.GetDTORoom(room, Long.toString(this.id))));
        EmitServerMessage("A Room has been created");
    }

    public void DeleteRoom(long roomId) throws SQLException, InvalidOperationException {
        Room room = rooms[GetRoomIndex(roomId)];
        room.Delete();
        if(roomId == notificationRoomId){
            if(rooms.length != 0){
                notificationRoomId = rooms[0].id;
            }
        }
        Emit(new DTOResponse<DTORoom>(ResponseType.DELETED_ROOM, DTORoom.GetDTORoom(room, Long.toString(this.id))));
        EmitServerMessage("A Room has been delete");
    }

    public void Update(DTODataServer data) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        if(name != data.name){
            PreparedStatement statementUpdateName = Global.conDatabase.prepareStatement("UPDATE server SET name = ? WHERE id = ?;");
            statementUpdateName.setString(1, data.name);
            statementUpdateName.setLong(2, this.id);
            statementUpdateName.execute();
            statementUpdateName.close();
            name = data.name;
            Emit(new DTOResponse(ResponseType.CHANGED_SERVER_NAME, DTOUpdateValue.GetDTOUpdateValue(Long.toString(id), name)));
        }
        for (DTORole role: data.roles) {
            if(role.operation == RoleOperation.DELETE){
                statement.execute("DELETE FROM role WHERE id = '" + role.id + "'; ");
            } else if(role.operation == RoleOperation.UPDATE){
                PreparedStatement statementUpdate = Global.conDatabase.prepareStatement("UPDATE role SET name = ?, caninvite = ?, canchange = ? WHERE id = ?;");
                statementUpdate.setString(1, role.name);
                statementUpdate.setBoolean(2, role.caninvite);
                statementUpdate.setBoolean(3, role.canchange);
                statementUpdate.setLong(4, Long.valueOf(role.id));
                statementUpdate.execute();
                statementUpdate.close();
            } else if(role.operation == RoleOperation.NEW){
                statement.execute("INSERT INTO role (`server_id`, `name`, `caninvite`, `canchange`) VALUES ('" + data.serverId + "', '" + role.name + "'," + role.caninvite + "," + role.canchange + "); ");
            }
        }
        statement.close();
    }

    public void Update(DTODataRoom data) throws SQLException, JsonProcessingException {
        this.GetRoom(Long.valueOf(data.id)).Update(data);
    }

    public void Update(DTODataUser data) throws SQLException, JsonProcessingException {
        Statement statement = Global.conDatabase.createStatement();
        for (DTORoleUser role: data.roles) {
            ResultSet rs = statement.executeQuery("SELECT * FROM user_role WHERE role_id = '" + role.roleId + "' AND user_id = '" + data.userId + "'; ");
            if(rs.next()){
                if(role.has == false){
                    statement.execute("DELETE FROM user_role WHERE user_id = '" + data.userId + "' AND '" + role.roleId + "';");
                }
            }else{
                if(role.has == true){
                    statement.execute("INSERT INTO user_role (`role_id`,`user_id`) VALUES ('" + role.roleId + "', '" + data.userId + "');");
                }
            }
        }
        User u = GetUser(Long.valueOf(data.userId));
        if (u != null){
            UpdateUser(u);
        }
    }

    private void UpdateUser(User u) throws SQLException, JsonProcessingException {
        for (Room room: rooms) {
            if(room.activeUsers.contains(u)){
                if(!u.CanRead(room.id, u.id)){
                    room.Remove(u);
                }
            } else{
                if(u.CanRead(room.id, u.id)){
                    room.Add(u);
                }
            }
            if(!u.CanSee(this.id, room.id)){
                u.connection.send(new DTOResponse<DTOGeneral>(ResponseType.DELETED_ROOM, DTOGeneral.GetDTORemoveRoom(Long.toString(this.id), Long.toString(room.id))).toJSON());
            } else{
                u.connection.send(new DTOResponse<DTORoom>(ResponseType.NEW_ROOM, DTORoom.GetDTORoom(room, u, Long.toString(this.id))).toJSON());
            }
        }
    }

    public void Emit(User from, long roomId, String msg) throws SQLException, InvalidMessageException, JsonProcessingException, InvalidOperationException {
        int index = GetRoomIndex(roomId);
        if(index != -1){
            rooms[index].Emit(from, msg);
        }
        else {
            throw new InvalidOperationException();
        }
    }

    public void Emit(DTOResponse response) {
        try{
            String message = response.toJSON();
            for (User u: onlineUsers) {
                u.connection.send(message);
            }
        } catch (Exception e) {}
    }

    public void EmitServerMessage(String notification) {
        try{
            if(rooms.length > 0){
                Emit(null, notificationRoomId, notification);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public DTOResponse GetMap(){
        return new DTOResponse(ResponseType.SERVER_MAP, this);
    }

    public int[] GetIndex() {
        int[] index = new int[Long.SIZE / Byte.SIZE];
        ByteBuffer buffer = ByteBuffer.allocate(index.length);
        buffer.putLong(id);
        ByteBuffer temp = ByteBuffer.allocate(4);
        for(int i = 0; i < index.length; i++){
            temp.put(3,buffer.get(i));
            index[i] = temp.getInt(0);
        }
        return index;
    }

    protected int GetRoomIndex(long id){
        int left = 0;
        int right = rooms.length;
        int center = 0;
        do {
            center = (left + right) / 2;
            if(rooms[center].id == id){
                return center;
            }
            else if(rooms[center].id < id){
                left = center;
            }
            else{
                right = center;
            }
        } while(center != (left + right) / 2);
        return -1;
    }

    public Room GetRoom(long id){
        return rooms[GetRoomIndex(id)];
    }

    protected void PutRoom(Room room){
        rooms = Arrays.copyOf(rooms, rooms.length+1);
        int index = GetRoomPos(room.id);
        PutRoom(room, index);
    }

    protected void AddToRooms(User user, long[] except){
        int blocked = 0;
        for(int i = 0; i < rooms.length; i++){
            if(except.length <= blocked || rooms[i].id != except[blocked]){
                rooms[i].Add(user);
            }
            else{
                blocked++;
            }
        }
    }

    private static long[] GetLongArray(ResultSet rs, int index) throws SQLException {
        rs.last();
        long[] erg = new long[rs.getRow()];
        for(int i = 0; i < erg.length; i++){
            erg[i] = rs.getLong(index);
        }
        return erg;
    }

    protected int GetRoomPos(long roomId){  //optimize
        for(int i = 0; i < rooms.length; i++){
            if(rooms[i] == null || rooms[i].id > roomId){
                return i;
            }
        }
        return rooms.length-1;
    }

    protected void PutRoom(Room room, int pos){
        Room temp = room;
        for(int i = pos; i < rooms.length; i++){
            Room temp2 = rooms[i];
            rooms[i] = temp;
            temp = temp2;
        }
    }

    protected void RemoveRoom(int index){
        for(int i = index; i < rooms.length-1; i++){
            rooms[i] = rooms[i+1];
        }
        Arrays.copyOf(rooms, rooms.length-1);
    }

    protected User GetUser(long id){
        for (User u: onlineUsers) {
            if(u.id == id){
                return u;
            }
        }
        return null;
    }
}