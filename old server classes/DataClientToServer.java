
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class DataClientToServer extends Thread {

    Socket socket;

    public DataClientToServer(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader dataToServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String input = "";

            while(true){
                input = dataToServer.readLine();
                out.println(input);
                ServerConnection.testToCloseClient(input, socket);  //test input string for "exit", 
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}