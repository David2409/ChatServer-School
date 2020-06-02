package at.schaefer.david.Communication.DTO;

import at.schaefer.david.General.Global;
import at.schaefer.david.General.Room;
import at.schaefer.david.General.Server;
import at.schaefer.david.General.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

public class DTORole {
    public String id;
    public String name;
    public boolean caninvite;
    public boolean canchange;
    public RoleOperation operation;

    public DTORole(){

    }

    private static DTORole GetDTORole(ResultSet rs) throws SQLException {
        DTORole erg = new DTORole();
        erg.id = rs.getString(1);
        erg.name = rs.getString(2);
        erg.caninvite = rs.getBoolean(3);
        erg.canchange = rs.getBoolean(4);
        return erg;
    }

    public static DTORole[] GetDTORoles(Server s) throws SQLException {
        DTORole[] erg;
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT id, name, caninvite, canchange FROM role WHERE server_id = '" + s.id + "';");
        rs.last();
        erg = new DTORole[rs.getRow()];
        rs.first();
        for(int i = 0; i < erg.length; i++){
            erg[i] = GetDTORole(rs);
            rs.next();
        }
        return erg;
    }

    public static DTORole GetDTORole(LinkedHashMap map){
        DTORole data = new DTORole();
        data.id = (String) map.get("id");
        data.name = (String) map.get("name");
        data.canchange = (boolean) map.get("canchange");
        data.caninvite = (boolean) map.get("caninvite");
        data.operation = RoleOperation.values()[(int) map.get("operation")];
        return data;
    }

    /*
    public static DTORole[] GetDTORoles(Room r) throws SQLException {
        DTORole[] erg;
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT role_id as id, name, cansee, canwrite, canread FROM room_role WHERE room_id = '" + r.id + "';");
        rs.last();
        erg = new DTORole[rs.getRow()];
        rs.first();
        for(int i = 0; i < erg.length; i++){
            erg[i] = GetDTORole(rs);
            rs.next();
        }
        return erg;
    }*/
}