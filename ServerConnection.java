

import java.io.BufferedReader;
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
    

    public static void connect(){
        try {
            Socket clientSocket = new Socket("localhost", 4321);
            InputStream dataIn = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(dataIn));
            String msgFromServer = reader.readLine();
            System.out.println(msgFromServer);

            //new threads get the socket passed into as the constructor argument
            //create new clientToServer thread

            DataClientToServer playerOut = new DataClientToServer();
           // playerOut.start(clientSocket);
            //create new serverToClient thread

            reader.close();
            clientSocket.close(); // automatically closes InputStream too
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        connect();
        
    }
    
}
