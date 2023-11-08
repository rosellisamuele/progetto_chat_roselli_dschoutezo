package chat_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class Server 
{
    ServerSocket serverSocket;
    public ServerClientConnectionThread connectionThread;
    public ServerClientHandler handler = new ServerClientHandler();
    public Socket socket;
    public int port;
    
    public static void main( String[] args )
    {
        
    }

    public void startServer() throws IOException{
        serverSocket = new ServerSocket(port);
        for(;;){
            socket = serverSocket.accept();
            connectionThread = new ServerClientConnectionThread(socket);
        }
        
        

    }

    public void clientJoined(){

    }

    public void clientLeft(){

    }

    public void sendMessage(){

    }
}
