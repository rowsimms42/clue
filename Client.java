import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private Socket socket;
    private Boolean isConnected = false;

    public Client() throws UnknownHostException, ClassNotFoundException {
        try {
            this.socket = new Socket(ClueGameConstants.IP, ClueGameConstants.PORT);
            this.isConnected = true;
            GameState.setNumberOfCharacters();
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


/**
 * Functions that do not involve communicating with server
 */

    public Boolean isPlayerConnected(){
            return this.isConnected;
        }
 


} // Client Class