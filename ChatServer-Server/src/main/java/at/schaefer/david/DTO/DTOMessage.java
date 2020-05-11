package at.schaefer.david.DTO;
import at.schaefer.david.Exceptions.InvalidMessageException;
import at.schaefer.david.General.Global;
import at.schaefer.david.General.Room;
import at.schaefer.david.General.Server;
import at.schaefer.david.General.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class DTOMessage {
    public String serverId;
    public String roomId;
    public String userId;
    public String msg;
    public String sendedAt;
    protected static final ObjectWriter OW = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public DTOMessage(long iServerId, long iRoomId, long iUserId, String iMsg) throws InvalidMessageException {
        if(Global.CheckString(iMsg) == false){
            throw new InvalidMessageException();
        }
        serverId = Long.toString(iServerId);
        roomId = Long.toString(iRoomId);
        userId = Long.toString(iUserId);
        msg = iMsg;
        sendedAt = Global.GetDateTime();
    }

    public DTOMessage(Server server, Room room, User user, String iMsg) throws InvalidMessageException {
        if(Global.CheckString(iMsg) == false){
            throw new InvalidMessageException();
        }
        serverId = Long.toString(server.id);
        roomId = Long.toString(room.id);
        if(user == null){
            userId = "null";
        }
        else{
            userId = Long.toString(user.id);
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
}
