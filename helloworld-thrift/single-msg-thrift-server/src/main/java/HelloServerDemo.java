import com.xej.thrift.demo.HelloWorldService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import server.HelloWorldServiceImpl;

public class HelloServerDemo {
    public static final int port = 8090;

    public void startServer() {
        System.out.println("HelloWorld TSimpleServer start ....");

        TProcessor tProcessor = new HelloWorldService.Processor<>(new HelloWorldServiceImpl());

        TServerSocket serverTransport = null;

        try {
           serverTransport = new TServerSocket(port);
        } catch (TTransportException e) {
            e.printStackTrace();
        }

        TServer.Args targs = new TServer.Args(serverTransport);

        targs.processor(tProcessor);

        targs.protocolFactory(new TBinaryProtocol.Factory());
        TServer server = new TSimpleServer(targs);

        server.serve();
    }

    public static void main(String[] args) {
        HelloServerDemo server = new HelloServerDemo();
        server.startServer();
    }



}
