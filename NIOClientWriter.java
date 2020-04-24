import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


/*
 * Client Writer 
 * Sends messages from the client to the server 
 */
public class NIOClientWriter implements Runnable {

    private SocketChannel sc;

    public NIOClientWriter(SocketChannel sc) {
        this.sc = sc;
    }


    /**
     * Need to transform logic into callable function
     * 
     * NIOClientWriter.write(String message){
     *      same functionality as in run()
     * }
     *  So that other functions can send data to server as needed.
     */
    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String msg = "";
        try {
            while ((msg = reader.readLine()) != null) {
                msg = "From client: " + msg + "\n";
                byte[] readMessage = new String(msg).getBytes();
                ByteBuffer buffer = ByteBuffer.wrap(readMessage);
                sc.write(buffer);

                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

