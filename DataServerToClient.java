import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class DataServerToClient extends Thread {
    Socket socket;

    public DataServerToClient(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader dataFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String msgFromServer = "";
            while(true){
                try {
                    msgFromServer = dataFromServer.readLine();
                    ServerConnection.testToCloseClient(msgFromServer, socket);  //test output string for "exit", if true close connection 
                    System.out.println(msgFromServer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}