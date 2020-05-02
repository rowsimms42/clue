import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class RequestHandler extends Thread {
    private Socket socket;
    private GameHandler gameHandler;
    Message returnedMessage;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    //Player player;

    RequestHandler(Socket socket, GameHandler gameHandler) {
        this.socket = socket;
        this.gameHandler = gameHandler;
        //player = Player();

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("New client connected.");

            //increase the number of players and display amount
            gameHandler.incrementAmountOfPlayers();
            System.out.println("Player amount: " + gameHandler.getNumberOfCurrentPlayers());

            //TODO add the new player to the gamestate
            
            //Print the thread id
            System.out.println("Thread ID is: " + getId());

            // sleep gives time to client to set up input & output streams
            Thread.sleep(100); 

            while (socket.isConnected()) {
                 /*listen for a message from the client and handle
                 the message logic. Also return new message to be sent
                 to the client */
                 returnedMessage = listenForMessageFromClient();
                 //Send the new messaget to the client. 
                 sendMessageToClient(returnedMessage);
            }

            oos.close();
       	 	ois.close();
       	 	socket.close();
        
        } catch (Exception e) {
        	System.out.println("\nException in Request Handler run()");
        	e.printStackTrace();
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
        //Send message to the client
        oos.writeObject(gameObj);
    }

    synchronized private Message listenForMessageFromClient() throws ClassNotFoundException, IOException, EOFException {
        Message gameObject = (Message) ois.readObject();
        Message returnObject = gameHandler.parseMessage(gameObject);
        return returnObject;
    }
}