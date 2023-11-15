package chat_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server 
{
    public static  ServerSocket serverSocket;
    public static  ServerClientConnectionThread connectionThread;
    public static  ServerClientHandler handler = new ServerClientHandler();
    public static  Socket socket;
    public static  int port;
    
    public static void main( String[] args )
    {
        port = setupPort();

        try{
            startServer();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void startServer() throws IOException{
        System.out.println("Server online");
        serverSocket = new ServerSocket(port);
        for(;;){
            socket = serverSocket.accept();
            System.out.println("client connesso");
            connectionThread = new ServerClientConnectionThread(socket, handler);
            connectionThread.start();
            handler.clientJoined(connectionThread);
        }
    }

    public static int setupPort(){
        System.out.println("Inserire la porta su cui hostare il server: ");
        Scanner portScanner = new Scanner(System.in);
        int p = portScanner.nextInt();
        portScanner.close();
        return p;
    }
}
