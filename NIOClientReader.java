import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


/**
 * Client Reader
 * Receives messages from server
 */


public class NIOClientReader implements Runnable {

    private SocketChannel readChannel;
    private ByteBuffer buffer = ByteBuffer.allocate(64);

    public NIOClientReader(SocketChannel sc) throws IOException {
        this.readChannel = sc;
        
    }
    

    @Override
    public void run() {
        try{
            int read = readChannel.read(buffer); // pos = n & lim = 64
            while (read != -1) {
                buffer.flip(); // set buffer in read mode - pos = 0 & lim = n
                while(buffer.hasRemaining()){
                    System.out.print((char) buffer.get()); // read 1 byte at a time
                    /* Place bytes into array instead of printing out
                       From there we can call handle function calls based on message */
                }
            buffer.clear(); // make buffer ready for writing - pos = 0 & lim = 64
            read = readChannel.read(buffer); // set to -1
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}