package at.schaefer.david.Communication.DTO;

import java.util.LinkedHashMap;

public class DTOUser {
    public String username;
    public String password;

    public DTOUser(){

    }

    public DTOUser(String iUsername){
        username = iUsername;
    }

    public static DTOUser GetDTOLogin(LinkedHashMap map){
        DTOUser erg = new DTOUser();
        erg.password = (String) map.get("password");
        erg.username = (String) map.get("username");
        return erg;
    }
}
