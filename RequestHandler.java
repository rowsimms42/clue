import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class RequestHandler extends Thread
{
    private Socket socket;    

    RequestHandler( Socket socket )
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try
        {
            System.out.println( "New client connected." );

            // Get input and output streams
            BufferedReader dataIn = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
            PrintWriter dataOut = new PrintWriter( socket.getOutputStream() );

            Thread.sleep(100); // sleep gives time to client to set up input & output streams

            // Write out our header to the client
            dataOut.println( "Connected, you can send and revieve messages!" );
            dataOut.flush();

            // Broadcasts to all clients until the client closes the connection or we receive an empty line
            String line = dataIn.readLine();
            while( line != null && line.length() > 0 )
            {
                //send message to all users
                Server.broadcast(line);
                //dataOut.println( "Echo: " + line ); //Send string out to client
                dataOut.flush();        // Flush output buffer
                line = dataIn.readLine();       // read in next input, blocking function call
            }

            // Close our connection
            dataIn.close();
            dataOut.close();
/* Need to add function to remove socket from Server.clientSocketList 
before closing socket to prevent error on server
Once a client closes the connection, the function Server.broadcast is 
trying to send messages to clients that are no longer connected */
            socket.close();

            System.out.println( "Connection closed" );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}