package at.schaefer.david.Communication.Responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class DTOResponse<T> {

    public ResponseType type;
    public T obj;

    protected static final ObjectWriter OW = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public DTOResponse(ResponseType iType, T iData){
        type = iType;
        obj = iData;
    }

    public String toJSON() throws JsonProcessingException {
        return OW.writeValueAsString(this);
    }
}
