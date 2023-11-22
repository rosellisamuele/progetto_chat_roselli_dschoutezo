package chat_server;

import java.net.Socket;
import java.util.ArrayList;

public class ServerClientHandler{
    Socket socket;
    Server server;

    ArrayList<ServerClientConnectionThread> connectedClients = new ArrayList<>();


    public void clientJoined(ServerClientConnectionThread connectionThread){
        //A client joined

        /*
         * Joining clients are added to an arrayList<> type object where all connection
         * threads are stored, in order to be easily referred to
         */
        connectedClients.add(connectionThread);
        String joinMsg = "[+] "+connectionThread.getClientName()+" si e' unito alla chat.";
        sendMessageToAll(joinMsg);
        System.out.println(joinMsg);
    }

    public boolean clientIsLogged(String clientName){
        //Checks if client is already logged

        for(ServerClientConnectionThread thread : connectedClients){
            if(clientName.equals(thread.getClientName()))
                return true;
        }
        return false;
    }
    public void sendMessageToAll(String msg){
        //Server is able to send messages in chat

        for(ServerClientConnectionThread thread : connectedClients){
            thread.send(msg);
        }
    }

    public void sendMessageToAll(String msg, String senderName){
        //Client is sending a message to all users connected
        
        /*
         * Checks the name, because the client itself doesn't need
         * to be sent the message back again
         */

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
                thread.closeSocket();
                connectedClients.remove(thread);
                String msg = "[-] " +clientName+ " ha abbandonato la chat";
                System.out.println(msg);
                sendMessageToAll(msg);
            }
        }
    }


}