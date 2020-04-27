import java.io.IOException;
import java.net.UnknownHostException;

public class TestClient {

    public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
        Client player = new Client();

        Message msg = new Message(1, null);
        player.send(msg);
        msg = player.getMessage();
        System.out.println(msg.getData());
        
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            //TODO: handle exception
        }

        msg = new Message(2, null);
        player.send(msg);
        msg = player.getMessage();
        System.out.println(msg.getData());

        try {
            Thread.sleep(100000);
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}