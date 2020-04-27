import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class RequestHandler extends Thread {
    private Socket socket;
    

    RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("New client connected.");

            Thread.sleep(100); // sleep gives time to client to set up input & output streams


            while (!socket.isClosed()) {
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

    synchronized private void sendMessageToClient(Object gameObj) throws IOException {
        //Send Updated GameState object to all threads connected to server
        for (Socket s : Server.clientSocketList){
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.writeUnshared(gameObj);
        }

    }

    synchronized private Object listenForMessageFromClient() throws ClassNotFoundException, IOException, EOFException {
        // Server blocks here until Client writes objects to server
        ObjectInputStream in = new ObjectInputStream(this.socket.getInputStream()); 
        Object gameObject = in.readObject();
        GameHandler.parseMessage((Message) gameObject);
        //socket.shutdownInput();
        return gameObject;
    }
}