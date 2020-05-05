package at.schaefer.david.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class DTOResponse {
    public ResponseType type;
    public String[] args;

    protected static final ObjectMapper OM = new ObjectMapper();

    public DTOResponse(){

    }

    public static DTOResponse GetResponse(String json) throws JsonProcessingException {
        return (DTOResponse) OM.readValue(json, DTOResponse.class);
    }
}
