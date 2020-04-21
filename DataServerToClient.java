import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class DataServerToClient extends Thread {
    Socket socket;

    public void DataClientToServer(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String msgFromServer = "";
            while(true){
                try {
                    msgFromServer = dataIn.readLine();
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