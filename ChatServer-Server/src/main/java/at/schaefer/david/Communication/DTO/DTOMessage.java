package at.schaefer.david.Communication.DTO;
import at.schaefer.david.Exceptions.InvalidMessageException;
import at.schaefer.david.General.Global;
import at.schaefer.david.General.Room;
import at.schaefer.david.General.Server;
import at.schaefer.david.General.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

public class DTOMessage {
    public long serverId;
    public long roomId;
    private String userId;
    public String username;
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
            username = "SERVER MESSAGE";
        }
        else{
            userId = Long.toString(user.GetId());
            username = user.name;
        }
        msg = iMsg;
        sendedAt = Global.GetDateTime();
    }

    public String toJSON() throws JsonProcessingException {
        return OW.writeValueAsString(this);
    }

    public void InsertIntoDTB() throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("INSERT INTO messages (`room_id`, `user_id`, `sendedat`, `msg`) VALUES ('" + roomId + "', '" + userId + "', '" + sendedAt + "', '" + msg + "');");
        statement.close();
    }

    public static DTOMessage GetDTOMessage(LinkedHashMap map) throws InvalidMessageException {
        DTOMessage erg = new DTOMessage();
        erg.serverId = (long) map.get("serverId");
        erg.userId = (String) map.get("userId");
        erg.roomId = (long) map.get("roomId");
        erg.username = (String) map.get("username");
        erg.msg = (String) map.get("msg");
        erg.sendedAt = (String) map.get("sendetat");

        if(Global.CheckString(erg.msg) == false){
            throw new InvalidMessageException();
        }
        return erg;
    }
}
