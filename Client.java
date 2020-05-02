import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private Socket socket;
    private Boolean isConnected = false;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public Client() throws UnknownHostException, ClassNotFoundException {
        try {
            this.socket = new Socket(ClueGameConstants.IP, ClueGameConstants.PORT);
            this.isConnected = true;
        } catch (IOException e) {
            try {
                if(this.socket != null)
                    this.socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        try {
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized public Message getMessage() throws IOException, EOFException, ClassNotFoundException {
            Message msg = (Message) ois.readObject();
            return msg;
        }

    synchronized public void send(Message msg) throws IOException {
        oos.writeObject(msg);    
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