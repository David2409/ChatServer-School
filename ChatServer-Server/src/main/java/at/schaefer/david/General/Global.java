package at.schaefer.david.General;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import javafx.util.converter.CharacterStringConverter;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Global {
    public static Connection conDatabase;
    public static Settings settings;

    protected static final DateFormat DATEFORMAT = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");

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

    public static String HashString(String string) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return new String(digest.digest(string.getBytes()));
    }
}
