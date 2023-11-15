package chat_server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ServerClientConnectionThread extends Thread{
    Socket socket;
    Server server;
    BufferedReader input;
    DataOutputStream output;
    ServerClientHandler handler;
    String clientName;

    public ServerClientConnectionThread(Socket socket, ServerClientHandler handler) throws IOException{
        this.socket = socket;
        this.handler = handler;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run(){
        String msg = "";

        //while(true){
            try{
                send("Inserisci il tuo nome: ");
                clientName = receive();
                send("OK");
                handler.clientJoined(this);
                for(;;){
                        msg = receive();

                        if(msg.equals("/exit")){
                            handler.clientLeft(clientName);
                            break;
                        }

                        if(msg != null){
                            System.out.println(clientName+": "+msg);
                            handler.sendMessageToAll(msg, clientName);
                        }

                        
                    
                }


            }catch(Exception e){}
        //}
    }

    public String receive(){
        
        try{
            return input.readLine();
        }catch(IOException e){
            e.printStackTrace();
            return "Errore";
        }
    }

    public void send(String msg){
        try{
            output.writeBytes(msg+"\n");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String getClientName() {
        return clientName;
    }

    public void closeSocket(){
        try{
            input.close();
            output.close();
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
