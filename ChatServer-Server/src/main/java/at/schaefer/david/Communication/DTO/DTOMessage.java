package at.schaefer.david.Communication.DTO;
import at.schaefer.david.Exceptions.InvalidMessageException;
import at.schaefer.david.General.Global;
import at.schaefer.david.General.Room;
import at.schaefer.david.General.Server;
import at.schaefer.david.General.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

public class DTOMessage {
    public long id;
    public long serverId;
    public long roomId;
    private String userId;
    public String from;
    public String msg;
    public String sendedAt;

    protected static final ObjectWriter OW = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public DTOMessage(){

    }

    public DTOMessage(Server server, Room room, User user, String iMsg) throws InvalidMessageException {
        if(Global.CheckString(iMsg) == false){
            throw new InvalidMessageException();
        }
        serverId = server.id;
        roomId = room.id;
        if(user == null){
            userId = "NULL";
            from = "SERVER";
        }
        else{
            userId = Long.toString(user.id);
            from = user.name;
        }
        msg = iMsg;
        sendedAt = Global.GetDateTime();
    }

    public DTOMessage(long iId, long iServerId, long iRoomId, String iUserid, String iUsername, String iMsg, String iSendedAt){
        iId = id;
        serverId = iServerId;
        roomId = iRoomId;
        userId = iUserid;
        from = iUsername;
        msg = iMsg;
        sendedAt = iSendedAt;
    }

    public String toJSON() throws JsonProcessingException {
        return OW.writeValueAsString(this);
    }

    public void InsertIntoDTB() throws SQLException {
        PreparedStatement statement = Global.conDatabase.prepareStatement("INSERT INTO messages (`room_id`, `user_id`, `sendedat`, `msg`) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, roomId);
        statement.setLong(2, Long.valueOf(userId));
        statement.setString(3, sendedAt);
        statement.setString(4, msg);
        statement.execute();
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        id = rs.getLong(1);
        statement.close();
    }

    public static DTOMessage GetDTOMessage(LinkedHashMap map) throws InvalidMessageException {
        DTOMessage erg = new DTOMessage();
        erg.serverId = Long.valueOf((String)map.get("serverId"));
        //erg.userId = (String) map.get("userId");
        erg.roomId = Long.valueOf((String)map.get("roomId"));
        //erg.username = (String) map.get("username");
        erg.msg = (String) map.get("msg");
        //erg.sendedAt = (String) map.get("sendetat");

        if(Global.CheckString(erg.msg) == false){
            throw new InvalidMessageException();
        }
        return erg;
    }

    public static DTOMessage[] GetArray(ResultSet rs) throws SQLException {
        // r.server_id, m.room_id, m.id, m.user_id, m.sendedat, m.msg, u.name
        DTOMessage[] erg;
        rs.last();
        erg = new DTOMessage[rs.getRow()];
        rs.first();
        String username;
        for(int i = 0; i < erg.length; i++){
            username = rs.getString(7);
            if(rs.wasNull()){
                username = "SERVER";
            }
            erg[i] = new DTOMessage(rs.getLong(3), rs.getLong(1), rs.getLong(2), rs.getString(4), username,  rs.getString(6), rs.getString(5));
            rs.next();
        }
        return erg;
    }
}
