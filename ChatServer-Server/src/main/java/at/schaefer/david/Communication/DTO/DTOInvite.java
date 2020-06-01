package at.schaefer.david.Communication.DTO;

import java.util.LinkedHashMap;

public class DTOInvite {
    public String username;
    public String serverId;

    public static DTOInvite GetDTOInvite(LinkedHashMap map){
        DTOInvite erg = new DTOInvite();
        erg.username = (String) map.get("username");
        erg.serverId = (String) map.get("serverId");
        return erg;
    }
}
