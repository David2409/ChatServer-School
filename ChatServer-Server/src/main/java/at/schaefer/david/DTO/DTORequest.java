package at.schaefer.david.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class DTORequest {
    public RequestType type;
    public String[] args;

    protected static final ObjectMapper OM = new ObjectMapper();

    public DTORequest(){

    }

    public static DTORequest GetRequest(String json) throws JsonProcessingException {
        return (DTORequest) OM.readValue(json, DTORequest.class);
    }
}
