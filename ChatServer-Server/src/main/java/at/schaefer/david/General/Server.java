package at.schaefer.david.General;

import at.schaefer.david.Communication.DTO.DTORemove;
import at.schaefer.david.Communication.DTO.DTORoom;
import at.schaefer.david.Communication.DTO.DTOUser;
import at.schaefer.david.Communication.Responses.DTOResponse;
import at.schaefer.david.Communication.Responses.ResponseType;
import at.schaefer.david.Exceptions.InvalidMessageException;
import at.schaefer.david.Exceptions.InvalidOperationException;
import at.schaefer.david.Exceptions.InvalidUserException;
import at.schaefer.david.Structure.IIndex;
import at.schaefer.david.Structure.Tree;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.nio.ByteBuffer;
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

    public static Server CreateServer(String name) throws SQLException, InvalidOperationException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("INSERT INTO server (`name`) VALUES ('" + name + "');", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        long id = rs.getLong(1);
        statement.close();
        return GetServer(id);
    }

    protected void init() throws SQLException, InvalidOperationException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rsRooms = statement.executeQuery("SELECT id FROM room WHERE server_id = '" + id + "' ORDER BY id;");
        rsRooms.last();
        rooms = new Room[rsRooms.getRow()];
        rsRooms.first();
        for(int i = 0; i < rooms.length; i++){
            rooms[i] = Room.Get(rsRooms.getLong(1), this);
            rsRooms.next();
        }
        ResultSet rs =statement.executeQuery("SELECT notificationroom FROM server WHERE '" + this.id + "' AND notificationroom IS NOT NULL;");
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
        rs = statement.executeQuery("SELECT name FROM server WHERE id = '" + id + "';");
        rs.next();
        name = rs.getString(1);
        statement.close();
        init = true;
    }

    public void JoinSession(User user) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rsRooms = statement.executeQuery("SELECT DISTINCT r.id FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id) JOIN role r ON (rr.room_id = r.id) WHERE ur.user_id = '" + user.id + "' AND r.server_id = '" + this.id + "' GROUP BY rr.room_id HAVING SUM(rr.canread) = '0' ORDER BY r.id;");
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
            }
        }
    }

    public void AddUser(User user) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("INSERT INTO server_user (`server_id`,`user_id`) VALUES ('" + this.id + "','" + user.id + "');");
        ResultSet rs = statement.executeQuery("SELECT name FROM user WHERE id = '" + user.id + "';");
        rs.next();
        String username = rs.getString(1);
        statement.close();
        EmitServerMessage("User '" + username + "' has join the Server");
        Emit(new DTOResponse<DTOUser>(ResponseType.ADDED_USER, DTOUser.GetDTOUser(user)));
    }

    public void RemoveUser(long userId) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("DELETE FROM server_user WHERE server_id = '" + this.id + "' AND user_id = '" + userId + "';");
        ResultSet rs = statement.executeQuery("SELECT name FROM user WHERE id = '{user_id}';");
        rs.next();
        String username = rs.getString(1);
        statement.close();
        EmitServerMessage("User '" + username + "' has left the Server");
        Emit(new DTOResponse<DTORemove>(ResponseType.REMOVED_USER, DTORemove.GetDTORemoveUser(Long.toString(this.id), Long.toString(userId))));
    }

    public void CreateRoom(String name) throws SQLException, InvalidOperationException {
        long roomId = Room.Create(this.id, name);
        Room room = Room.Get(roomId, this);
        PutRoom(room);
        if(rooms.length == 1){
            notificationRoomId = rooms[0].id;
        }
        Emit(new DTOResponse<DTORoom>(ResponseType.NEW_ROOM, DTORoom.GetDTORoom(room, Long.toString(this.id))));
        EmitServerMessage("A Room has been created");
    }

    public void DeleteRoom(long roomId) throws SQLException, InvalidOperationException {
        Room room = rooms[GetRoomIndex(roomId)];
        Emit(new DTOResponse<DTORoom>(ResponseType.DELETED_Room, DTORoom.GetDTORoom(room, Long.toString(this.id))));
        room.Delete();
        if(roomId == notificationRoomId){
            if(rooms.length != 0){
                notificationRoomId = rooms[0].id;
            }
        }
        EmitServerMessage("A Room has been delete");
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
                u.GetConnection().send(message);
            }
        } catch (Exception e) {}
    }

    public void EmitServerMessage(String notification) {
        try{
            if(rooms.length > 0){
                Emit(null, notificationRoomId, notification);
            }
        } catch (Exception e) { }
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

    protected void PutRoom(Room room){
        rooms = Arrays.copyOf(rooms, rooms.length+1);
        int index = GetRoomPos(room.id);
        PutRoom(room, index);
    }

    protected void AddToRooms(User user, long[] except){
        int blocked = 0;
        for(int i = 0; i < rooms.length; i++){
            if(except.length == 0 || rooms[i].id != except[blocked]){
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
}