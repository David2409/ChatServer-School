package at.schaefer.david.Communication.DTO;

import at.schaefer.david.General.Room;
import at.schaefer.david.General.User;

import java.sql.SQLException;
import java.sql.Statement;

public class DTORoom {
    public String id;
    public String name;
    public DTOMessage[] messages;
    public DTOMessage[] newMessages;

    private static DTORoom GetDTORoom(Room room ,User u) {
        DTORoom dtoRoom = new DTORoom();
        dtoRoom.id = Long.toString(room.id);
        dtoRoom.name = room.name;
        dtoRoom.messages = new DTOMessage[0];
        dtoRoom.newMessages = new DTOMessage[0];
        try{
            if(u.CanRead(room.id)){
                dtoRoom.messages = room.GetMessagesBefore(u.lastLogout);
                dtoRoom.newMessages = room.GetMessagesAfter(u.lastLogout);
            }
        }
        catch (Exception e){ }
        return dtoRoom;
    }

    public static DTORoom[] GetDTORooms(Room[] rooms, User u){
        DTORoom[] erg = new DTORoom[rooms.length];
        for(int i = 0; i < erg.length; i++){
            erg[i] = GetDTORoom(rooms[i], u);
        }
        return erg;
    }
}
