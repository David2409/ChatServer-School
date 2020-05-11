package at.schaefer.david.General;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Role {

    public static long Create(String name, long serverId) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("INSERT INTO role (`server_id`, `name`) VALUES ('" + serverId + "', '" + name + "');", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        long id = rs.getLong(1);
        statement.close();
        return id;
    }

    public static void Delete(long id) throws SQLException {
        Statement statement = Global.conDatabase.createStatement();
        statement.execute("DELETE FROM role WHERE id = '" + id + "';");
        statement.close();
    }

    public static void Change(long serverId, String[] args) throws SQLException { //roleId, roomId, canSee, canWrite, canRead, ...
        long[][] ids = GetLongArray(args);
        Statement statement = Global.conDatabase.createStatement();
        ResultSet rs = statement.executeQuery("SELECT room_id, role_id FROM room_role WHERE room_id in ('" + GetStringFromArray(ids, 1) + "') AND role_id in ('" + GetStringFromArray(ids, 0) + "') ORDER BY room_id, role_id;");
        long[][] rsIds = GetLongArray(rs,new int[]{1,2});
        String sqlStatUpdate = "";
        String sqlStatInsert = "INSERT INTO room_role (`room_id`,`role_id`,`cansee`,`canwrite`,`canread`) VALUES";
        boolean hasInsertValues = false;
        for(int i = 0; i < args.length; i++){
            String[] input = args[i].split(",");
            if(Has(rsIds, new long[]{Long.getLong(input[0]), Long.getLong(input[1])})){
                sqlStatUpdate += "UPDATE room_role SET cansee = '" + input[2] + "' AND canwrite = '" + input[3] + "' AND canread = '" + input[4] + "' WHERE role_id = '" + input[0] + "' AND room_id = '" + input[1] + "';";
            }
            else{
                hasInsertValues = true;
                sqlStatInsert += "('" + input[0] + "','" + input[1] + "','" + input[2] + "','" + input[3] + "','" + input[4] + "'),";
            }
        }
        if(hasInsertValues){
            sqlStatInsert = sqlStatInsert.substring(0, sqlStatInsert.length()-1);
            sqlStatInsert += ";";
            sqlStatUpdate += sqlStatInsert;
        }
        statement.execute(sqlStatUpdate);
        statement.close();
    }

    private static long[][] GetLongArray(ResultSet rs, int[] index) throws SQLException {
        rs.last();
        long[][] erg = new long[rs.getRow()][];
        for(int i = 0; i < erg.length; i++){
            erg[i] = new long[index.length];
            for(int i2 = 0; i2 < index.length; i2++){
                erg[i][i2] = rs.getLong(index[i2]);
            }
        }
        return erg;
    }

    private static long[][] GetLongArray(String[] array) {
        long[][] erg = new long[array.length][];
        for(int i = 0; i < erg.length; i++){
            erg[i] = new long[2];
            erg[i][0] = Long.getLong(array[0]);
            erg[i][1] = Long.getLong(array[1]);
        }
        return erg;
    }

    private static String GetStringFromArray(long[][] array, int index){
        String erg = "";
        for(int i = 0; i < array.length; i++){
            erg += array[i][index];
            if(i != array.length-1){
                erg += ",";
            }
        }
        return erg;
    }

    private static boolean Has(long[][] array, long[] value){
        for(int i = 0; i < array.length; i++){
            boolean isSame = true;
            for(int i2 = 0; i2 < array[i].length; i2++){
                if(array[i][i2] != value[i2]){
                    isSame = false;
                    break;
                }
            }
            if(isSame){
                return true;
            }
        }
        return false;
    }
}
