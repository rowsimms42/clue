import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class RequestHandler extends Thread {
    private Socket socket;
    private GameHandler gameHandler;
    

    RequestHandler(Socket socket, GameHandler gameHandler) {
        this.socket = socket;
        this.gameHandler = gameHandler;
    }

    @Override
    public void run() {
        try {
            System.out.println("New client connected.");

            Thread.sleep(100); // sleep gives time to client to set up input & output streams


            while (socket.isConnected()) {
                sendMessageToClient(listenForMessageFromClient());
            }

            
        } catch (Exception e) {
            // Close our connection
            try {
                socket.close(); //close the socket
                Server.removeSocket(socket); //remove socket from clientSocketList
            } catch (IOException e1) {
                System.out.println("Error closing connection");
                e1.printStackTrace();
            }
            System.out.println("Connection closed");
            e.printStackTrace();
        }
    }

    synchronized private void sendMessageToClient(Message gameObj) throws IOException {
        //Send Updated GameState object to all threads connected to server
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeUnshared(gameObj);
    }

    synchronized private Message listenForMessageFromClient() throws ClassNotFoundException, IOException, EOFException {
        // Server blocks here until Client writes objects to server
        ObjectInputStream in = new ObjectInputStream(this.socket.getInputStream()); 
        Message gameObject = (Message) in.readObject();
        Message returnObject = gameHandler.parseMessage(gameObject);
        //socket.shutdownInput();
        return returnObject;
    }
}