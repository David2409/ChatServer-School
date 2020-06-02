package at.schaefer.david.Communication.DTO;

import at.schaefer.david.General.Global;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DTODataUser {
    public String serverId;
    public String userId;
    public String name;
    public DTORoleUser[] roles;

    public DTODataUser(){

    }

    public static DTODataUser GetDTODataUser(long serverId, long userId) throws SQLException {
        DTODataUser data = new DTODataUser();
        data.serverId = Long.toString(serverId);
        data.userId = Long.toString(userId);
        data.roles = DTORoleUser.GetDTORoleUsers(serverId, userId);
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT name FROM user WHERE id = '" + userId + "';");
        rs.first();
        data.name = rs.getString(1);
        statement.close();
        return data;
    }

    public static DTODataUser GetDTODataUser(LinkedHashMap map){
        DTODataUser data = new DTODataUser();
        data.serverId = (String) map.get("serverId");
        data.userId = (String) map.get("userId");
        data.name = (String) map.get("name");
        ArrayList<LinkedHashMap> mapRoles = (ArrayList<LinkedHashMap>) map.get("roles");
        data.roles = new DTORoleUser[mapRoles.size()];
        for(int i = 0; i < data.roles.length; i++){
            data.roles[i] = DTORoleUser.GetDTORoleUser(mapRoles.get(i));
        }
        return data;
    }
}
