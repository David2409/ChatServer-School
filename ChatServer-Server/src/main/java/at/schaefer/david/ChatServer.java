package at.schaefer.david;

import at.schaefer.david.Communication.DTO.*;
import at.schaefer.david.Communication.Requests.DTORequest;
import at.schaefer.david.Communication.Responses.DTOResponse;
import at.schaefer.david.Communication.Responses.ResponseType;
import at.schaefer.david.Exceptions.InvalidMessageException;
import at.schaefer.david.Exceptions.InvalidOperationException;
import at.schaefer.david.Exceptions.InvalidUserException;
import at.schaefer.david.General.Global;
import at.schaefer.david.General.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ChatServer extends WebSocketServer {

    public static void main(String[] args){
        ChatServer sws = new ChatServer();
        sws.start();
        System.out.println("Started");
        try{
            Global.init();
        } catch (SQLException e) {
            System.err.println("Can't Connect to the database\n\tplease check if the database is started or if the settings are correct");
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Can't read settings");
            System.exit(-1);
        }
    }

    private static int TCP_PORT = 4444;

    private Set<WebSocket> conns;

    public ChatServer() {
        super(new InetSocketAddress(TCP_PORT));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conns.add(conn);
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
        /*try {
            conn.setAttachment(User.GetUser(conn, "Chrascher", "test"));
            System.out.println("Logged IN");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        try{
            User user = conn.getAttachment();
            if(user != null){
                user.LogOut();
            }
        } catch(Exception e) {}
        conns.remove(conn);
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message from client: " + message);
        DTORequest request;
        try{
             request = DTORequest.GetRequest(message);
        } catch (Exception e){
            e.printStackTrace();
            return;
        }

        try {
            ExecuteRequest(request, conn);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        //ex.printStackTrace();
        if (conn != null) {
            conns.remove(conn);
            // do some thing if required
        }
        System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    public void onStart() {
        conns = new HashSet<WebSocket>();
    }

    private void ExecuteRequest(DTORequest request, WebSocket conn) throws SQLException, NoSuchAlgorithmException, InvalidUserException, UnsupportedEncodingException, InvalidMessageException, InvalidOperationException, JsonProcessingException {
        User user = conn.getAttachment();
        switch (request.type){
            case CREATE_USER:
                if(user != null){
                    throw new InvalidOperationException();
                }
                DTOUser createU = (DTOUser) request.obj;
                user = User.CreateUser(conn, createU.username, createU.password);
                conn.setAttachment(user);
                break;

            case DELETE_USER:
                if(user != null){
                    throw new InvalidOperationException();
                }
                user.DeleteUser();
                break;

            case LOGIN:
                if(user != null){
                    throw new InvalidOperationException();
                }
                DTOUser login = (DTOUser) request.obj;
                user = User.GetUser(conn, login.username, login.password);
                conn.setAttachment(user);
                break;

            case LOGOUT:
                if(user == null){
                    throw new InvalidOperationException();
                }
                user.LogOut();
                conn.setAttachment(null);
                break;

            case SEND_MESSAGE:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTOMessage message = (DTOMessage) request.obj;
                if(user.CanSend(message.serverId, message.roomId) == true){
                    user.SendMessage(message.serverId, message.roomId, message.msg);
                }
                break;

            case CREATE_SERVER:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTOServer dtoserver = (DTOServer) request.obj;
                user.CreateServer(dtoserver.name);
                break;

            case CREATE_ROOM:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTORoom dtoRoom = (DTORoom) request.obj;
                user.CreateRoom(Long.valueOf(dtoRoom.serverId), dtoRoom.name);
                break;

            case INVITE_USER:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTOInvite dtoInvite = (DTOInvite) request.obj;
                if(user.CanInvite(Long.valueOf(dtoInvite.serverId))){
                    try{
                        user.GetServer(Long.valueOf(dtoInvite.serverId)).AddUser(dtoInvite.username);
                    } catch (Exception e){
                        throw new InvalidOperationException();
                    }
                }
                break;

            case REMOVE_USER:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTOGeneral dtoGeneral = (DTOGeneral) request.obj;
                if(!user.CanModify(Long.valueOf(dtoGeneral.serverId))) {
                    throw new InvalidOperationException();
                }
                user.GetServer(Long.valueOf(dtoGeneral.serverId)).RemoveUser(Long.valueOf(dtoGeneral.userId));
                break;

            case DELETE_ROOM:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTOGeneral dtoRemoveRoom = (DTOGeneral) request.obj;
                if(!user.CanModify(Long.valueOf(dtoRemoveRoom.serverId))){
                    throw new InvalidOperationException();
                }
                user.GetServer(Long.valueOf(dtoRemoveRoom.serverId)).DeleteRoom(Long.valueOf(dtoRemoveRoom.roomId));
                break;

            case DELETE_SERVER:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTOGeneral dtoDeleteServer = (DTOGeneral) request.obj;
                if(!user.CanModify(Long.valueOf(dtoDeleteServer.serverId))){
                    throw new InvalidOperationException();
                }
                user.GetServer(Long.valueOf(dtoDeleteServer.serverId)).Delete();
                break;

            case DATA_SERVER:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTOGeneral dtoDataServer = (DTOGeneral) request.obj;
                if(user.CanModify(Long.valueOf(dtoDataServer.serverId))){
                    conn.send(new DTOResponse(ResponseType.DATA_SERVER, DTODataServer.GetDTOData(user.GetServer(Long.valueOf(dtoDataServer.serverId)))).toJSON());
                }
                break;

            case DATA_ROOM:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTOGeneral dtoDataRoom = (DTOGeneral) request.obj;
                if(user.CanModify(Long.valueOf(dtoDataRoom.serverId))){
                    conn.send(new DTOResponse(ResponseType.DATA_ROOM, DTODataRoom.GetDTODataRoom(user.GetServer(Long.valueOf(dtoDataRoom.serverId)).GetRoom(Long.valueOf(dtoDataRoom.roomId)))).toJSON());
                }
                break;

            case DATA_USER:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTOGeneral dtoDataUser = (DTOGeneral) request.obj;
                conn.send(new DTOResponse(ResponseType.DATA_USER, DTODataUser.GetDTODataUser(Long.valueOf(dtoDataUser.serverId), Long.valueOf(dtoDataUser.userId))).toJSON());
                break;

            case CHANGE_SERVER:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTODataServer dataServer = (DTODataServer) request.obj;
                if(user.CanModify(Long.valueOf(dataServer.serverId))){
                    user.GetServer(Long.valueOf(dataServer.serverId)).Update(dataServer);
                }
                break;

            case CHANGE_ROOM:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTODataRoom dataRoom = (DTODataRoom) request.obj;
                if(user.CanModify(Long.valueOf(dataRoom.serverId))){
                    user.GetServer(Long.valueOf(dataRoom.serverId)).Update(dataRoom);
                }
                break;

            case CHANGE_USER:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTODataUser dataUser = (DTODataUser) request.obj;
                if(user.CanModify(Long.valueOf(dataUser.serverId))){
                    user.GetServer(Long.valueOf(dataUser.serverId)).Update(dataUser);
                }
                break;
        }
    }
}