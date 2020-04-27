import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private int PORT = 4321;
    private Socket socket;

    public Client() throws UnknownHostException, ClassNotFoundException {
        try {
            this.socket = new Socket("localhost", this.PORT);
        } catch (IOException e) {
            try {
                this.socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }


    synchronized public Message getMessage() throws IOException, ClassNotFoundException {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object msg = in.readObject();
            return (Message) msg;
        }

    synchronized public void send(Message msg) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(msg);    
    }

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        Client newPlayer = new Client();
    }
}