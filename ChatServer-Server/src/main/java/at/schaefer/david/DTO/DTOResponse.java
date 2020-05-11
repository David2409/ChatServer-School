package at.schaefer.david.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class DTOResponse {

    public ResponseType type;
    public Object data;

    protected static final ObjectWriter OW = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public DTOResponse(ResponseType iType, Object iData){
        type = iType;
        data = iData;
    }

    public String toJSON() throws JsonProcessingException {
        return OW.writeValueAsString(this);
    }
}
