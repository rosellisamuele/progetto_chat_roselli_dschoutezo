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
    ServerClientHandler handler;
    String clientName;

    public ServerClientConnectionThread(Socket socket, ServerClientHandler handler) throws IOException{
        //Constructor
        this.socket = socket;
        this.handler = handler;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run(){
        
        String warning = "";
        String msg = "";

        
            try{
                while(true){
                        //Asking client to specify name
                        send("Inserisci il tuo nome: ");
                        clientName = receive();


                        

                        // BLANK CHECK NOT WORKING

                        if(clientName.isBlank()){
                            warning = "BLANK_USER";
                            send(warning);
                            continue;
                        }

                        

                        clientName = clientName.replaceAll(" ", "_");

                        //An error has occourred, the connection is suspended
                        if(msg.equals("ERROR")){
                            return;
                        }

                        

                        if(handler.clientIsLogged(clientName)){
                            //Username is already logged
                            //Client is asked to change name and login back again
                            warning = "[X] Utente gia' presente. Cambiare nome";
                            send(warning);
                            continue;
                        }else{
                            //Send "OK" if the controls have been passed
                            send("OK");
                            
                        }

                        break;
                }
                

                handler.clientJoined(this);
                for(;;){
                        //Receiving message
                        msg = receive();

                        //Case ERROR
                        /*
                         * In case the client stops responding, the string "ERROR"
                         * will be sent instead of the message, implying that 
                         * a connection error has occourred.
                         */
                        if(msg.equals("ERROR")){
                            handler.clientLeft(clientName);
                            break;
                        }

                        //Case EXIT
                        /*
                         * In case the string "/exit" is sent, the client will be 
                         * logged out of the chat.
                         */
                        if(msg.equals("/exit")){
                            send("EXIT");
                            handler.clientLeft(clientName);
                            break;
                        }

                        


                        if(msg != null){
                            //If message is null nothing has been sent

                            //Check if message is private or public
                            if(msg.startsWith("@")){

                                //Message is private, check required

                                /*
                                 * [ FORMAT: <name> @ message ]
                                 * split msg string to obtain destination and content
                                 */
                                String[] splitmsg = msg.split(" ",2);
                                String destName = splitmsg[0].replaceAll("@", "");

                                //Check if client is logged into handler list
                                if(handler.clientIsLogged(destName)){
                        
                                    //In case client is logged
                                    System.out.println(clientName+" > to > "+destName+" : "+splitmsg[1]);
                                    handler.sendMessageToOne(destName, splitmsg[1], clientName);

                                }else{

                                    //In case client is not logged
                                    send("[X] Impossibile inviare. Il client non e' loggato.");
                                    continue;

                                }
                            }else{

                                //Message is public, no check required

                                System.out.println(clientName+": "+msg);
                                handler.sendMessageToAll(msg, clientName);
                            }
                        }
                }
            }catch(Exception e){
                
            } 
    }

    //Receive
    public String receive(){
        
        /*
         * Used to read line from client stream
         */
        try{
            return input.readLine();
        }catch(IOException e){
            return "ERROR";
        }
    }

    //Send
    public void send(String msg){
        /*
         * Used to send message to client
         */
        try{
            output.writeBytes(msg+"\n");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String getClientName() {
        //Returns client name, can be externally useful
        return clientName;
    }

    public void closeSocket(){
        //Closes the connection socket
        try{
            input.close();
            output.close();
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
