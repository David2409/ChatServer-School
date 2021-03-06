package at.schaefer.david.Communication.Requests;

import at.schaefer.david.Communication.DTO.*;
import at.schaefer.david.Exceptions.InvalidMessageException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;

public class DTORequest<T> {
    public RequestType type;
    public T obj;

    protected static final ObjectMapper OM = new ObjectMapper();

    public DTORequest(){

    }

    public static DTORequest GetRequest(String json) throws JsonProcessingException, InvalidMessageException {
        DTORequest request = (DTORequest) OM.readValue(json, DTORequest.class);
        request.obj = GetObj(request.type, (LinkedHashMap) request.obj);
        return request;
    }

    private static Object GetObj(RequestType type, LinkedHashMap map) throws InvalidMessageException {
        switch (type){
            case LOGIN:
                return DTOUser.GetDTOUser(map);
            case CREATE_USER:
                return DTOUser.GetDTOUser(map);
            case SEND_MESSAGE:
                return DTOMessage.GetDTOMessage(map);
            case CREATE_SERVER:
                return DTOServer.GetDTOServer(map);
            case CREATE_ROOM:
                return DTORoom.GetDTORoom(map);
            case INVITE_USER:
                return DTOInvite.GetDTOInvite(map);
            case REMOVE_USER:
                return DTOGeneral.GetDTOGeneral(map);
            case DELETE_ROOM:
                return DTOGeneral.GetDTOGeneral(map);
            case DELETE_SERVER:
                return DTOGeneral.GetDTOGeneral(map);
            case DATA_USER:
                return DTOGeneral.GetDTOGeneral(map);
            case DATA_ROOM:
                return DTOGeneral.GetDTOGeneral(map);
            case DATA_SERVER:
                return DTOGeneral.GetDTOGeneral(map);
            case CHANGE_SERVER:
                return DTODataServer.GetDTOData(map);
            case CHANGE_ROOM:
                return DTODataRoom.GetDTODataRoom(map);
            case CHANGE_USER:
                return DTODataUser.GetDTODataUser(map);
            default:
                throw new InvalidMessageException();
        }
    }
}
