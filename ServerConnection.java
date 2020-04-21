

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

//Contributors: Jonah Dubbs-Nadeau and Joseph Salazar

/*
 * Class to connect to the server as well
 * as send/receive game data from the 
 * server
 */

public class ServerConnection extends Thread {
    
    public void run(){
        try {
            Socket clientSocket = new Socket("localhost", 4321);
            InputStream dataIn = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(dataIn));
            String msgFromServer = reader.readLine();
            System.out.println(msgFromServer);

            //new threads get the socket passed into as the constructor argument
            //create new ClientToServer thread
            DataClientToServer playerOut = new DataClientToServer(clientSocket);
            playerOut.start();
            
            //create new ServerToClient thread
            DataServerToClient playerIn = new DataServerToClient(clientSocket);
            playerIn.start();
        
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void testToCloseClient(String closeMsg, Socket socket){
        if (closeMsg.equalsIgnoreCase("exit")){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ServerConnection newPlayer = new ServerConnection();
        newPlayer.start();
    }
    
}
