package at.schaefer.david.Communication.DTO;

import at.schaefer.david.General.Global;
import at.schaefer.david.General.Room;

import javax.management.relation.Role;
import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

public class DTORoleRoom {
    public String id;
    public String name;
    public boolean cansee;
    public boolean canwrite;
    public boolean canread;

    private static DTORoleRoom GetDTORoleRoom(ResultSet rs) throws SQLException {
        DTORoleRoom data = new DTORoleRoom();
        data.id = rs.getString(1);
        data.name = rs.getString(2);
        data.cansee = rs.getBoolean(3);
        data.canwrite = rs.getBoolean(4);
        data.canread = rs.getBoolean(5);
        return data;
    }

    public static DTORoleRoom[] GetDTORoleRoom(Room r) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT r.id, r.name, rr.cansee, rr.canwrite, rr.canread FROM room_role rr RIGHT JOIN role r ON (r.id = rr.role_id AND rr.room_id = '" + r.id + "') WHERE r.server_id = '" + r.server.id + "';");
        rs.last();
        DTORoleRoom[] erg = new DTORoleRoom[rs.getRow()];
        rs.first();
        for(int i = 0; i < erg.length; i++){
            erg[i] = DTORoleRoom.GetDTORoleRoom(rs);
            rs.next();
        }
        statement.close();
        return erg;
    }

    public static DTORoleRoom GetDTORoleRoom(LinkedHashMap map) {
        DTORoleRoom data = new DTORoleRoom();
        data.id = (String) map.get("id");
        data.name = (String) map.get("name");
        data.cansee = (boolean) map.get("cansee");
        data.canread = (boolean) map.get("canread");
        data.canwrite = (boolean) map.get("canwrite");
        return data;
    }
}
