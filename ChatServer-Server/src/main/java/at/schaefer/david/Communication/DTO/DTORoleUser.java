package at.schaefer.david.Communication.DTO;

import at.schaefer.david.General.Global;
import at.schaefer.david.General.Server;
import at.schaefer.david.General.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

public class DTORoleUser {
    public String roleId;
    public String roleName;
    public boolean has;

    private static DTORoleUser GetDTORoleUser(ResultSet rs) throws SQLException {
        DTORoleUser data = new DTORoleUser();
        data.roleId = rs.getString(1);
        data.roleName = rs.getString(2);
        data.has = rs.getBoolean(3);
        return data;
    }

    public static DTORoleUser[] GetDTORoleUsers(long serverId, long userId) throws SQLException {
        DTORoleUser[] erg;
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT r.id, r.name, !ISNULL(user_id) FROM user_role ur RIGHT JOIN role r ON(r.id = ur.role_id AND ur.user_id = '" + userId + "') WHERE r.server_id = '" + serverId + "';");
        rs.last();
        erg = new DTORoleUser[rs.getRow()];
        rs.first();
        for(int i = 0; i < erg.length; i++){
            erg[i] = GetDTORoleUser(rs);
            rs.next();
        }
        return erg;
    }

    public static DTORoleUser GetDTORoleUser(LinkedHashMap map){
        DTORoleUser data = new DTORoleUser();
        data.roleId = (String) map.get("roleId");
        data.roleName = (String) map.get("roleName");
        data.has = (boolean) map.get("has");
        return data;
    }
}
