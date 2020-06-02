package at.schaefer.david.Communication.DTO;

import at.schaefer.david.General.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DTODataRoom {
    public String serverId;
    public String id;
    public String name;
    public DTORoleRoom[] roles;

    public DTODataRoom(){

    }

    public static DTODataRoom GetDTODataRoom(Room r) throws SQLException {
        DTODataRoom data = new DTODataRoom();
        data.id = Long.toString(r.id);
        data.name = r.name;
        data.serverId = Long.toString(r.server.id);
        data.roles = DTORoleRoom.GetDTORoleRoom(r);
        return data;
    }

    public static DTODataRoom GetDTODataRoom(LinkedHashMap map){
        DTODataRoom data = new DTODataRoom();
        data.serverId = (String) map.get("serverId");
        data.name = (String) map.get("name");
        data.id = (String) map.get("id");
        ArrayList<LinkedHashMap> mapRoles = (ArrayList<LinkedHashMap>) map.get("roles");
        data.roles = new DTORoleRoom[mapRoles.size()];
        for(int i = 0; i < data.roles.length; i++){
            data.roles[i] = DTORoleRoom.GetDTORoleRoom(mapRoles.get(i));
        }
        return data;
    }
}
