package at.schaefer.david.Communication.DTO;

public class DTOUpdateValue {
    public String serverId;
    public String roomId;
    public String userId;
    public String value;

    public static DTOUpdateValue GetDTOUpdateValue(String serverId, String value){
        DTOUpdateValue data = new DTOUpdateValue();
        data.serverId = serverId;
        data.value = value;
        return data;
    }

    public static DTOUpdateValue GetDTOUpdateValue(String serverId, String roomId, String value){
        DTOUpdateValue data = new DTOUpdateValue();
        data.serverId = serverId;
        data.roomId = roomId;
        data.value = value;
        return data;
    }
}
