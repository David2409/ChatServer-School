package at.schaefer.david.General;

import at.schaefer.david.Exceptions.InvalidMessageException;
import at.schaefer.david.Exceptions.InvalidOperationException;
import at.schaefer.david.Structure.IIndex;
import at.schaefer.david.Structure.Tree;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Server implements IIndex {
    public long id;
    public boolean init;
    protected Room[] rooms;
    protected static Tree servers  = new Tree(8);
    protected List<User> onlineUsers;

    protected Server(long iId)
    {
        id = iId;
        onlineUsers = new ArrayList<User>(1);
    }

    public static Server GetServer(long id) throws SQLException {
        Server server = (Server) servers.Add(new Server(id));
        synchronized (server){
            if(server.init == false){
                server.init();
            }
        }
        return server;
    }

    public static Server CreateServer(String name) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("INSERT INTO server (`name`) VALUES ('" + name + "');", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        long id = rs.getLong(1);
        statement.close();
        return GetServer(id);
    }

    protected void init() throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rsRooms = statement.executeQuery("SELECT id FROM room WHERE server_id = '" + id + "' ORDER BY id;");
        rsRooms.last();
        rooms = new Room[rsRooms.getRow()];
        rsRooms.first();
        for(int i = 0; i < rooms.length; i++){
            rooms[i] = new Room(rsRooms.getLong(1), this);
            rsRooms.next();
        }
        init = true;
        statement.close();
    }

    public void JoinSession(User user) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rsRooms = statement.executeQuery("SELECT DISTINCT r.id FROM user_role ur JOIN room_role rr ON(ur.role_id = rr.role_id) JOIN role r ON (rr.room_id = r.id) WHERE ur.user_id = '" + user.id + "' AND r.server_id = '" + this.id + "' GROUP BY rr.room_id HAVING SUM(rr.cansee) = '0' ORDER BY r.id;");
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

    public static void Add(long serverId, long userId) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("INSERT INTO server_user (`server_id`,`user_id`) VALUES ('" + serverId + "','" + userId + "');");
        statement.close();
    }

    public static void Remove(long serverId, long userId) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("DELETE FROM server_user WHERE server_id = '" + serverId + "' AND user_id = '" + userId + "';");
        statement.close();
    }

    public static void CreateRole(String name, long serverId) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("INSERT INTO role (`server_id`, `name`) VALUES ('" + serverId + "', '" + name + "');");
        statement.close();
    }

    public static void RemoveRole(long id) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("DELETE FROM role WHERE id = '" + id + "';");
        statement.close();
    }

    public static void ChangeRole(long serverId, String[] args) throws SQLException { //roleId, roomId, canSee, canWrite, canRead, ...
            long[][] ids = GetLongArray(args);
            Statement statement = Global.conDatabase.createStatement();
            ResultSet rs = statement.executeQuery("SELECT room_id, role_id FROM room_role WHERE room_id in ('" + GetStringFromArray(ids, 1) + "') AND role_id in ('" + GetStringFromArray(ids, 0) + "') ORDER BY room_id, role_id;");
            long[][] rsIds = GetLongArray(rs,new int[]{1,2});
            String sqlStatUpdate = "";
            String sqlStatInsert = "INSERT INTO room_role (`room_id`,`role_id`,`cansee`,`canwrite`,`canread`) VALUES";
            boolean hasInsertValues = false;
            for(int i = 0; i < args.length; i++){
                String[] input = args[i].split(",");
                if(Has(rsIds, new long[]{Long.getLong(input[0]), Long.getLong(input[1])})){
                    sqlStatUpdate += "UPDATE room_role SET cansee = '" + input[2] + "' AND canwrite = '" + input[3] + "' AND canread = '" + input[4] + "' WHERE role_id = '" + input[0] + "' AND room_id = '" + input[1] + "';";
                }
                else{
                    hasInsertValues = true;
                    sqlStatInsert += "('" + input[0] + "','" + input[1] + "','" + input[2] + "','" + input[3] + "','" + input[4] + "'),";
                }
            }
            if(hasInsertValues){
                sqlStatInsert = sqlStatInsert.substring(0, sqlStatInsert.length()-1);
                sqlStatInsert += ";";
                sqlStatUpdate += sqlStatInsert;
            }
            statement.execute(sqlStatUpdate);
            statement.close();
    }

    public void Emit(User from, long roomId, String msg) throws SQLException, InvalidMessageException, JsonProcessingException, InvalidOperationException {
        GetRoom(roomId).Emit(from, msg);
    }

    public int[] GetIndex() {
        return CalculateIndex(id);
    }

    public static int[] CalculateIndex(long from){
        int[] index = new int[Long.SIZE / Byte.SIZE];
        ByteBuffer buffer = ByteBuffer.allocate(index.length);
        buffer.putLong(from);
        ByteBuffer temp = ByteBuffer.allocate(4);
        for(int i = 0; i < index.length; i++){
            temp.put(3,buffer.get(i));
            index[i] = temp.getInt(0);
        }
        return index;
    }

    protected Room GetRoom(long id){
        int left = 0;
        int right = rooms.length;
        int center = 0;
        do {
            center = (left + right) / 2;
            if(rooms[center].id == id){
                return rooms[center];
            }
            else if(rooms[center].id < id){
                left = center;
            }
            else{
                right = center;
            }
        } while(center != (left + right) / 2);
        return null;
    }

    protected void AddToRooms(User user, long[] except){
        int blocked = 0;
        for(int i = 0; i < rooms.length; i++){
            if(rooms[i].id != except[blocked]){
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

    private static long[][] GetLongArray(ResultSet rs, int[] index) throws SQLException {
        rs.last();
        long[][] erg = new long[rs.getRow()][];
        for(int i = 0; i < erg.length; i++){
            erg[i] = new long[index.length];
            for(int i2 = 0; i2 < index.length; i2++){
                erg[i][i2] = rs.getLong(index[i2]);
            }
        }
        return erg;
    }

    private static long[][] GetLongArray(String[] array) {
        long[][] erg = new long[array.length][];
        for(int i = 0; i < erg.length; i++){
            erg[i] = new long[2];
            erg[i][0] = Long.getLong(array[0]);
            erg[i][1] = Long.getLong(array[1]);
        }
        return erg;
    }

    private static String GetStringFromArray(long[][] array, int index){
        String erg = "";
        for(int i = 0; i < array.length; i++){
            erg += array[i][index];
            if(i != array.length-1){
                erg += ",";
            }
        }
        return erg;
    }

    private static boolean Has(long[][] array, long[] value){
        for(int i = 0; i < array.length; i++){
            boolean isSame = true;
            for(int i2 = 0; i2 < array[i].length; i2++){
                if(array[i][i2] != value[i2]){
                    isSame = false;
                    break;
                }
            }
            if(isSame){
                return true;
            }
        }
        return false;
    }
}