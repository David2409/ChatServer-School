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
            default:
                throw new InvalidMessageException();
        }
    }
}
