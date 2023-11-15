package chat_server;

import java.net.Socket;
import java.util.ArrayList;

public class ServerClientHandler{
    Socket socket;
    Server server;

    ArrayList<ServerClientConnectionThread> connectedClients = new ArrayList<>();


    public void clientJoined(ServerClientConnectionThread connectionThread){
        connectedClients.add(connectionThread);
        sendMessageToAll("[+] "+connectionThread.getClientName()+" e' entrato nel server.");
    }

    public void sendMessageToAll(String msg){
        for(ServerClientConnectionThread thread : connectedClients){
            thread.send(msg);
        }
    }

    public void sendMessageToAll(String msg, String senderName){
        for(ServerClientConnectionThread thread : connectedClients){
            if(!(thread.getClientName().equals(senderName)))
                thread.send(senderName+": "+msg);
        }
    }

    public void sendMessageToOne(String destName, String msg, String senderName){
        for(ServerClientConnectionThread thread : connectedClients){
            if(destName.equals(thread.getClientName()))
                thread.send("[Da "+senderName+"]: "+msg);
        }
    }


    public void clientLeft(String clientName){
        for(ServerClientConnectionThread thread : connectedClients){
            if(clientName.equals(thread.getClientName())){
                thread.send("EXIT");
                thread.closeSocket();
                connectedClients.remove(thread);
            }
        }
    }


}