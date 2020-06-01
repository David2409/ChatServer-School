package at.schaefer.david.Communication.DTO;

import at.schaefer.david.General.Room;
import at.schaefer.david.General.Server;
import at.schaefer.david.General.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DTOServer {
    public String id;
    public String name;
    public DTORoom[] rooms;
    public DTOUser[] onlineUser;
    public DTOUser[] offlineUser;

    public static DTOServer GetDTOServer(Server server, User u){
        DTOServer dtoServer = new DTOServer();
        dtoServer.id = Long.toString(server.id);
        dtoServer.name = server.name;
        List<Room> temp = new ArrayList<Room>(server.rooms.length);
        try{
            for (Room r: server.rooms) {
                if(u.CanSee(server.id, r.id)){
                    temp.add(r);
                }
            }
        } catch (Exception e){ e.printStackTrace(); }
        Room[] temp2 = new Room[temp.size()];
        temp.toArray(temp2);
        dtoServer.rooms = DTORoom.GetDTORooms(temp2, u, Long.toString(server.id));
        User[] online;
        synchronized (server.onlineUsers){
            online = new User[server.onlineUsers.size()];
            server.onlineUsers.toArray(online);
        }
        dtoServer.onlineUser = DTOUser.GetDTOUsers(online);
        dtoServer.offlineUser = DTOUser.GetDTOUsers(server, online);
        return dtoServer;
    }

    public static DTOServer[] GetDTOServerArray(Server[] servers, User u){
        DTOServer[] erg = new DTOServer[servers.length];
        for(int i = 0; i < erg.length; i++){
            erg[i] = GetDTOServer(servers[i], u);
        }
        return erg;
    }

    public static DTOServer GetDTOServer(LinkedHashMap linkedHashMap){
        DTOServer dtoServer = new DTOServer();
        dtoServer.name = (String) linkedHashMap.get("name");
        return dtoServer;
    }
}