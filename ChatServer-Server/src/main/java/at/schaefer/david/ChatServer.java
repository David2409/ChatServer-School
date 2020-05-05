package at.schaefer.david;

import at.schaefer.david.General.Global;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
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
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message from client: " + message);
        for (WebSocket sock : conns) {
            sock.send(message);
        }
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
}