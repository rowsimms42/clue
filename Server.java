import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread
{
    private ServerSocket serverSocket;
    private int port;
    private boolean running = false;
    private static List<Socket> clientSocketList = new ArrayList<Socket>();

    public Server( int port )
    {
        this.port = port;
    }

    public void startServer()
    {
        try
        {
            serverSocket = new ServerSocket( port );
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


    /*
    * Basic function to iterate through stored list of 
    * client sockets and output messages passed as argument
    */ 
    public static void broadcast(String msg)
    {
        for(Socket s : clientSocketList)
        {
            if(s != null) 
            {
                PrintStream out = null;
                try 
                {   //can change from println to some other data output stream for non text output
                    out = new PrintStream(s.getOutputStream());
                    out.println("[BROADCAST]: " + msg);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            
        }
    }


    @Override
    public void run()
    {
        running = true;
        while( running )
        {
            try
            {
                System.out.println( "Listening for connections" );

                // Call accept() to receive the next connection
                Socket socket = serverSocket.accept();

                // Pass the socket to the RequestHandler thread for processing
                RequestHandler requestHandler = new RequestHandler( socket );

                //Add new client socket to list
                clientSocketList.add(socket);


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
        int port = 4321;
        System.out.println( "Start server on port: " + port );

        Server server = new Server( port );
        server.startServer();

        // Automatically shutdown in 1 minute
        try
        {
            Thread.sleep( 60000 );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

        server.stopServer();
    }
}