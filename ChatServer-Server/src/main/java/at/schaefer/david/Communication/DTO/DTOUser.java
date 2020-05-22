package at.schaefer.david.Communication.DTO;

import at.schaefer.david.Communication.Responses.DTOResponse;
import at.schaefer.david.General.Global;
import at.schaefer.david.General.Server;
import at.schaefer.david.General.User;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;

public class DTOUser {
    public long id;
    public String username;
    public String password;

    public DTOUser(){

    }

    public DTOUser(String iUsername){
        username = iUsername;
    }

    public DTOUser(long iId, String iUsername) { id = iId; username = iUsername; }

    public static DTOUser GetDTOUser(LinkedHashMap map){
        DTOUser erg = new DTOUser();
        erg.password = (String) map.get("password");
        erg.username = (String) map.get("username");
        return erg;
    }

    public static DTOUser GetDTOUser(User u){
        DTOUser erg = new DTOUser();
        erg.id = u.id;
        erg.username = u.name;
        return erg;
    }

    public static DTOUser[] GetDTOUsers(User[] users){
        DTOUser[] erg = new DTOUser[users.length];
        for(int i = 0; i < erg.length; i++){
            erg[i] = GetDTOUser(users[i]);
        }
        return erg;
    }

    public static DTOUser[] GetDTOUsers(Server server, User[] except){
        DTOUser[] erg = new DTOUser[0];
        try{
            Statement statement = Global.conDatabase.createStatement();
            ResultSet rs = statement.executeQuery("SELECT u.id, u.name FROM server_user su JOIN user u ON(u.id = su.user_id) WHERE su.server_id = '" + server.id + "' AND u.id NOT IN " + GetStringArray(except) + ";");
            rs.last();
            erg = new DTOUser[rs.getRow()];
            rs.first();
            for(int i = 0; i < erg.length; i++){
                erg[i] = new DTOUser(rs.getLong(1), rs.getString(2));
                rs.next();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return erg;
    }

    private static String GetStringArray(User[] users){
        String erg = "(";
        for(int i = 0; i < users.length; i++){
            erg += users[i].id;
            if(i != users.length-1){
                erg += ",";
            }
        }
        erg += ")";
        return erg;
    }
}
