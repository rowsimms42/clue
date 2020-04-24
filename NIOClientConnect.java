import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class NIOClientConnect {

    public NIOClientConnect () throws IOException {
        connect();
    }

    public static void connect() throws IOException {
        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 1234);
        SocketChannel clientSocket = SocketChannel.open(hostAddress);
        clientSocket.configureBlocking(false);

        NIOClientReader clientReader = new NIOClientReader(clientSocket);
        Thread t1 = new Thread(clientReader);
        t1.start();

        NIOClientWriter clientWriter = new NIOClientWriter(clientSocket);
        Thread t2 = new Thread(clientWriter);
        t2.start();

        while(clientSocket.isOpen()){}

        clientSocket.close();

        System.out.println("Client started.");
    }

    public static void main(String[] args) throws IOException {
        //testing only, will be called from GUI in real app
        NIOClientConnect newPlayer = new NIOClientConnect();
        
    }
}
