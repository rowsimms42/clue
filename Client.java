import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private Socket socket;
    private Boolean isConnected = false;
    
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public Client() throws UnknownHostException, ClassNotFoundException {
    	
        try {
            this.socket = new Socket(ClueGameConstants.IP, 55332);
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
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e2) {
			System.out.println("Error openining Input or Output Stream in Client.java");
			e2.printStackTrace();
		}
    }


    synchronized public Message getMessage() throws IOException, EOFException, ClassNotFoundException {
            Message msg = (Message) ois.readUnshared();
            return msg;
    }

    synchronized public void send(Message msg) throws IOException {
        oos.writeUnshared(msg);
    }
    
    public Boolean isPlayerConnected(){
            return this.isConnected;
    }
 
    public void closeConnection() {
    	try {
			socket.close();
			oos.close();
			ois.close();
		} catch (IOException e) {
			System.out.println("Error closing connection in Client.java");
			e.printStackTrace();
		}
    }

} // Client Class