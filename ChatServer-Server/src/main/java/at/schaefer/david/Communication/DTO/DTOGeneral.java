package at.schaefer.david.Communication.DTO;

import at.schaefer.david.Communication.Responses.DTOResponse;

import java.util.LinkedHashMap;

public class DTOGeneral {
    public String serverId;
    public String roomId;
    public String userId;

    public static DTOGeneral GetDTORemoveServer(String id){
        DTOGeneral dtoRemove = new DTOGeneral();
        dtoRemove.serverId = id;
        return dtoRemove;
    }

    public static DTOGeneral GetDTORemoveRoom(String iServerId, String id){
        DTOGeneral dtoRemove = new DTOGeneral();
        dtoRemove.serverId = iServerId;
        dtoRemove.roomId = id;
        return dtoRemove;
    }

    public static DTOGeneral GetDTORemoveUser(String iServerId, String id){
        DTOGeneral dtoRemove = new DTOGeneral();
        dtoRemove.serverId = iServerId;
        dtoRemove.userId = id;
        return dtoRemove;
    }

    public static DTOGeneral GetDTOGeneral(LinkedHashMap map){
        DTOGeneral dtoGeneral = new DTOGeneral();
        if(map.containsKey("serverId")){
            dtoGeneral.serverId = (String) map.get("serverId");
        }
        if(map.containsKey("roomId")){
            dtoGeneral.roomId = (String) map.get("roomId");
        }
        if(map.containsKey("userId")){
            dtoGeneral.userId = (String) map.get("userId");
        }
        return dtoGeneral;
    }
}
