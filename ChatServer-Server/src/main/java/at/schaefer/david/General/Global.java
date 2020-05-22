package at.schaefer.david.General;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Global {
    public static Connection conDatabase;
    public static Settings settings;

    protected static final DateFormat DATEFORMAT = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");

    public static void init() throws IOException, SQLException {
        settings = Settings.GetSettings();
        conDatabase = DriverManager.getConnection(settings.database, settings.databaseUser, settings.databasePassword);
    }

    public static boolean CheckString(String string){
        for (char character: settings.invalidCharacters) {
            if(string.contains(Character.toString(character))){
                return false;
            }
        }
        return true;
    }

    public static String GetDateTime(){
        Date date = new Date();
        return DATEFORMAT.format(date);
    }

    public static String ReplaceCharacters(String string, char[] characters, char with){
        for (char c : characters) {
            string.replaceAll(String.valueOf(c), String.valueOf(with));
        }
        return string;
    }

    public static String HashPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return HashString(HashString(password) + password);
    }

    private static String HashString(String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        return String.format("%0128x", new BigInteger(1,digest.digest(string.getBytes("UTF-8"))));
    }
}
