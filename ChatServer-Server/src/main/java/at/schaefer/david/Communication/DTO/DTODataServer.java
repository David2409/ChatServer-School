package at.schaefer.david.Communication.DTO;

import at.schaefer.david.Communication.Responses.DTOResponse;
import at.schaefer.david.Communication.Responses.ResponseType;
import at.schaefer.david.Exceptions.InvalidOperationException;
import at.schaefer.david.General.Global;
import at.schaefer.david.General.Server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DTODataServer {
    public String serverId;
    public String name;
    public DTORole[] roles;

    public DTODataServer(){

    }

    public static DTODataServer GetDTOData(Server server) throws SQLException {
        DTODataServer data = new DTODataServer();
        data.serverId = Long.toString(server.id);
        data.name = server.name;
        data.roles = DTORole.GetDTORoles(server);
        return data;
    }

    public static DTODataServer GetDTOData(LinkedHashMap map) {
        DTODataServer data = new DTODataServer();
        data.serverId = (String) map.get("serverId");
        data.name = (String) map.get("name");
        ArrayList<LinkedHashMap> dataRoles = (ArrayList<LinkedHashMap>) map.get("roles");
        data.roles = new DTORole[dataRoles.size()];
        for(int i = 0; i < data.roles.length; i++){
            data.roles[i] = DTORole.GetDTORole(dataRoles.get(i));
        }
        return data;
    }

    public static void main(String[] args) throws IOException, SQLException, InvalidOperationException {
        Global.init();
        Server s = Server.GetServer(1);
        DTODataServer data = DTODataServer.GetDTOData(s);
        System.out.println(new DTOResponse<DTODataServer>(ResponseType.DATA_SERVER, data).toJSON());
        System.out.println("Finished");
    }
}
