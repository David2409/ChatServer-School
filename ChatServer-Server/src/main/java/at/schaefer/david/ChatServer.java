package at.schaefer.david;

import at.schaefer.david.Communication.DTO.DTOMessage;
import at.schaefer.david.Communication.DTO.DTORoom;
import at.schaefer.david.Communication.DTO.DTOServer;
import at.schaefer.david.Communication.DTO.DTOUser;
import at.schaefer.david.Communication.Requests.DTORequest;
import at.schaefer.david.Communication.Responses.DTOResponse;
import at.schaefer.david.Communication.Responses.ResponseType;
import at.schaefer.david.Exceptions.InvalidMessageException;
import at.schaefer.david.Exceptions.InvalidOperationException;
import at.schaefer.david.Exceptions.InvalidUserException;
import at.schaefer.david.General.Global;
import at.schaefer.david.General.Room;
import at.schaefer.david.General.Server;
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
        try {
            conn.send(new DTOResponse(ResponseType.REMOVED_MESSAGE, null).toJSON());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
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
                conn.send(new DTOResponse<DTOUser>(ResponseType.LOGGED_IN, new DTOUser(createU.username)).toJSON());
                conn.send(new DTOResponse<DTOServer[]>(ResponseType.SERVER_MAP, DTOServer.GetDTOServerArray(user.servers, user)).toJSON());
                break;

            case DELETE_USER:
                if(user != null){
                    throw new InvalidOperationException();
                }
                user.DeleteUser();
                conn.send(new DTOResponse(ResponseType.LOGGED_OUT, null).toJSON());
                break;

            case LOGIN:
                if(user != null){
                    throw new InvalidOperationException();
                }
                DTOUser login = (DTOUser) request.obj;
                user = User.GetUser(conn, login.username, login.password);
                conn.setAttachment(user);
                conn.send(new DTOResponse<DTOUser>(ResponseType.LOGGED_IN, new DTOUser(login.username)).toJSON());
                conn.send(new DTOResponse<DTOServer[]>(ResponseType.SERVER_MAP, DTOServer.GetDTOServerArray(user.servers, user)).toJSON());
                break;

            case LOGOUT:
                if(user == null){
                    throw new InvalidOperationException();
                }
                user.LogOut();
                conn.setAttachment(null);
                conn.send(new DTOResponse(ResponseType.LOGGED_OUT, null).toJSON());
                break;

            case SEND_MESSAGE:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTOMessage message = (DTOMessage) request.obj;
                if(user.CanSend(message.roomId) == true){
                    user.SendMessage(message.serverId, message.roomId, message.msg);
                }
                break;

            case CREATE_SERVER:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTOServer dtoserver = (DTOServer) request.obj;
                Server s = Server.CreateServer(dtoserver.name);
                s.AddUser(user.id);
                s.JoinSession(user);
                conn.send(new DTOResponse<DTOServer>(ResponseType.NEW_SERVER, DTOServer.GetDTOServer(s, user)).toJSON());
                break;

            case CREATE_ROOM:
                if(user == null){
                    throw new InvalidOperationException();
                }
                DTORoom dtoRoom = (DTORoom) request.obj;
                Server server =user.GetServer(Long.valueOf(dtoRoom.serverId));
                server.CreateRoom(dtoRoom.name);
        }
    }
}