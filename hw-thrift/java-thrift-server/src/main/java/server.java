import com.test.thrift.FormatDataImpl;
import com.test.thrift.format_data;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

public class server {

   final static int port = 8082;

    public static void main(String[] args) {
        FormatDataImpl fdi = new FormatDataImpl();

        TProcessor processor = new format_data.Processor(fdi);

        TNonblockingServerSocket socket = null;
        try {
            socket = new TNonblockingServerSocket(port);
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        TNonblockingServer.Args tnArgs = new TNonblockingServer.Args(socket);

        tnArgs.processor(processor);
        tnArgs.transportFactory(new TFramedTransport.Factory());
        tnArgs.protocolFactory(new TBinaryProtocol.Factory());

        TServer server = new TNonblockingServer(tnArgs);

        System.out.println("-----start server ok:\t");
        System.out.println("-----port:\t" + port);
        server.serve();
    }
}
