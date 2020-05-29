package at.schaefer.david.Communication.DTO;

import at.schaefer.david.Communication.Responses.DTOResponse;

public class DTORemove {
    public String serverId;
    public String roomId;
    public String userId;

    public static DTORemove GetDTORemoveServer(String id){
        DTORemove dtoRemove = new DTORemove();
        dtoRemove.serverId = id;
        return dtoRemove;
    }

    public static DTORemove GetDTORemoveRoom(String iServerId, String id){
        DTORemove dtoRemove = new DTORemove();
        dtoRemove.serverId = iServerId;
        dtoRemove.roomId = id;
        return dtoRemove;
    }

    public static DTORemove GetDTORemoveUser(String iServerId, String id){
        DTORemove dtoRemove = new DTORemove();
        dtoRemove.serverId = iServerId;
        dtoRemove.userId = id;
        return dtoRemove;
    }
}
