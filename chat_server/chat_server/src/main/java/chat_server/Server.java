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
    
    //Main method
    public static void main( String[] args )
    {
        //Calling port setup function
        port = setupPort();

        try{
            //Start server
            startServer();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void startServer() throws IOException{
        //Start server
        /*
         * Server is online and accepts new clients
         */
        System.out.println("Server online");
        serverSocket = new ServerSocket(port);
        for(;;){
            socket = serverSocket.accept();
            System.out.println("client connesso");
            connectionThread = new ServerClientConnectionThread(socket, handler);
            connectionThread.start();
        }
    }

    public static int setupPort(){
        //Port setup
        /*
         * The hosting port is submitted from input by client
         */
        System.out.println("Inserire la porta su cui hostare il server: ");
        Scanner portScanner = new Scanner(System.in);
        int p = portScanner.nextInt();
        portScanner.close();
        return p;
    }
}

