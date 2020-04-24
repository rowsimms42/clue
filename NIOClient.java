import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {

    public void clientConnect() throws IOException, InterruptedException {

        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 1234);
        SocketChannel clientSocket = SocketChannel.open(hostAddress);


        clientSocket.configureBlocking(false);

        System.out.println("Client started.");


        String threadName = Thread.currentThread().getName();

        ByteBuffer inbuf = ByteBuffer.allocate(64);
        StringBuilder stringBuilder = new StringBuilder();
        
        int msgFromServer = 0;
        while((msgFromServer = clientSocket.read(inbuf)) > 0){
            
            inbuf.flip();
            byte[] bytes = new byte[inbuf.limit()];
            inbuf.get(bytes);
            stringBuilder.append(new String(bytes));

            inbuf.clear();
        }
        String message = stringBuilder.toString();
        System.out.println(message);

        
        



        BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
        String msg = "";
        while((msg = reader.readLine()) != null) {
            msg = threadName + ": " + msg;
            byte [] readMessage = new String(msg).getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(readMessage);
            clientSocket.write(buffer);

            buffer.clear();
        }

        /*
        String [] messages = new String [] 
                {threadName + ": test1",threadName + ": test2",threadName + ": test3"};
  
        for (int i = 0; i < messages.length; i++) {
            byte [] message = new String(messages [i]).getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(message);
            clientSocket.write(buffer);
            System.out.println(messages [i]);
            buffer.clear();
            Thread.sleep(5000);
        }
        */
        clientSocket.close(); 

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        NIOClient newClient = new NIOClient();
        newClient.clientConnect();
    }
}