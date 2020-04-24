import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIOServer
 * code adapted from https://gist.github.com/Botffy/3860641
 */
public class NIOServer implements Runnable {

    private final static int port = 1234;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private ByteBuffer buffer = ByteBuffer.allocate(256);

    private final ByteBuffer greeting = ByteBuffer.wrap("Welcome to Clue!\n".getBytes());

    NIOServer() throws IOException {
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().bind(new InetSocketAddress(port));
        this.serverSocketChannel.configureBlocking(false);
        this.selector = Selector.open();

        this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

     @Override
     public void run() {
         try {
             System.out.println("Server listening on port: " + port);

             Iterator<SelectionKey> iter;
             SelectionKey selKey;

             while(this.serverSocketChannel.isOpen()) {
                selector.select();
                iter = this.selector.selectedKeys().iterator();

                 while(iter.hasNext()) {
                    selKey = iter.next();
                    iter.remove();

                    if(selKey.isAcceptable()) {
                        this.handleAccept(selKey);
                    }
                    if(selKey.isReadable()) {
                    this.handleReadAndWrite(selKey);
                    }
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

    private void handleReadAndWrite(SelectionKey selKey) throws IOException {
        SocketChannel readChannel = (SocketChannel) selKey.channel();
        StringBuilder stringBuilder = new StringBuilder();

        buffer.clear();
        int read = 0;
        try{
            while( (read = readChannel.read(buffer)) > 0 ) {
                buffer.flip();
                byte[] bytes = new byte[buffer.limit()];
                buffer.get(bytes);
                stringBuilder.append(new String(bytes));
                buffer.clear();
            }
        } catch(Exception e) {
            selKey.cancel();
            read = -1;
        }
        
        String message;
        if(read < 0) {
            message = selKey.attachment() + " left the game\n";
            readChannel.close();
        } else {
            message = selKey.attachment() + ": " + stringBuilder.toString();
        }
        /* This is the point at which the back end will handle the GUI input
           Based on the message functions will be appropriately called, and 
           output sent back to clients */
           /* Need function within server to select certain player to send messages to. */
        System.out.println(message);
        broadcast(message);
        
    }

   

    private void handleAccept(SelectionKey selKey) throws IOException {
        SocketChannel acceptChannel = ((ServerSocketChannel) selKey.channel()).accept();
        String address = (new StringBuilder( acceptChannel.socket().getInetAddress().toString() )).append(":").append(acceptChannel.socket().getPort()).toString();
        acceptChannel.configureBlocking(false);
        acceptChannel.register(selector, SelectionKey.OP_READ, address);
        acceptChannel.write(this.greeting);
        greeting.rewind();
        System.out.println("New connection accepted at: " + address);
    }



    private void broadcast(String message) throws IOException {
        ByteBuffer messageBuffer = ByteBuffer.wrap(message.getBytes());

        for(SelectionKey key : selector.keys()) {
            if (key.isValid() && key.channel() instanceof SocketChannel) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                socketChannel.write(messageBuffer);
                messageBuffer.rewind();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        NIOServer server = new NIOServer();
        (new Thread(server)).start();
    }
}