package chat_server;

import java.net.Socket;
import java.util.ArrayList;

public class ServerClientHandler{
    Socket socket;
    Server server;

    ArrayList<ServerClientConnectionThread> connectedClients = new ArrayList<>();


    public void clientJoined(ServerClientConnectionThread connectionThread){
        connectedClients.add(connectionThread);
    }

    public void sendMessageToAll(String msg){
        for(ServerClientConnectionThread thread : connectedClients){
            thread.send(msg);
        }
    }

    


}