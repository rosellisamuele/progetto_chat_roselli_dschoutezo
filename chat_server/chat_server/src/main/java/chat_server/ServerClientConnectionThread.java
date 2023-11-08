package chat_server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerClientConnectionThread extends Thread{
    Socket socket;
    Server server;
    BufferedReader input;
    DataOutputStream output;
    ServerClientHandler clientHandler;

    public ServerClientConnectionThread(Socket socket) throws IOException{
        this.socket = socket;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run(){
        while(true){
            try{
                this.input.readLine();
            }catch(Exception e){}
            this.clientHandler.sendMessageToAll("");
        }
    }

    public String receive(){
        String msg = "";
        try{
            msg = input.readLine();
        }catch(IOException e){
            e.printStackTrace();
        }
        return msg;
    }

    public void send(String msg){
        try{
            output.writeBytes(msg+'\n');
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
