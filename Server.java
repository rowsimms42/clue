import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread
{
    private ServerSocket serverSocket;
    private boolean running = false;
    public static ArrayList<Socket> clientSocketList = new ArrayList<Socket>();

    public Server()
    {
        //Set up initial GameState with server start
        GameState.initializeVariables();
        startServer();
        //should not be reached
        stopServer();
    }

    public void startServer()
    {
        try
        {
            serverSocket = new ServerSocket( ClueGameConstants.PORT );
            this.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void stopServer()
    {
        running = false;
        this.interrupt();
    }

    public static void removeSocket(Socket socket){
        for(Socket s : clientSocketList){
            if (s == socket){
                clientSocketList.remove(s);
            }
        }
    }

    public Boolean isServerConnected()
    {
        if(!this.serverSocket.isClosed()){
            return true;
        } else if(this.serverSocket.isClosed()){
            return false;
        } else {
            System.out.println("Serious Error when testing for ServerSocket connection on IP and PORT");
            return false;
        }
    }

    @Override
    public void run()
    {
        System.out.println( "Listening for connections" );
        running = true;
        while( running )
        {
            try
            {

                // Call accept() to receive the next connection
                Socket socket = serverSocket.accept();

                //Add new socket to list of sockets
                clientSocketList.add(socket);

                // Pass the socket to the RequestHandler thread for processing
                RequestHandler requestHandler = new RequestHandler( socket );

                requestHandler.start();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main( String[] args )
    {

        Server server = new Server();

    }
}