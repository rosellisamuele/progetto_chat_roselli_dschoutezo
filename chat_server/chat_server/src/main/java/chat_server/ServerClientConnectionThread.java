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
    String clientName;

    public ServerClientConnectionThread(Socket socket) throws IOException{
        this.socket = socket;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run(){
        String msg = "";

        while(true){
            try{
                send("Inserisci il tuo nome: ");
                clientName = receive();
                send("OK");
                for(;;){
                    msg = receive();
                    if(msg != null)
                        System.out.println(clientName+": "+msg);
                }


            }catch(Exception e){}
        }
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
            output.writeBytes(msg+'\n');
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String getClientName() {
        return clientName;
    }

}
