import com.xej.thrift.demo.HelloWorldService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import server.HelloWorldServiceImpl;

public class HelloServerDemo {
    public static final int port = 8090;

    public void startServer() {
        System.out.println("HelloWorld TNonBlockingServer start ....");

        TProcessor tProcessor = new HelloWorldService.Processor<>(new HelloWorldServiceImpl());

        TNonblockingServerSocket tnbSocketTransport = null;

        try {
            tnbSocketTransport  = new TNonblockingServerSocket(port);
        } catch (TTransportException e) {
            e.printStackTrace();
        }

        TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(tnbSocketTransport);
        tnbArgs.processor(tProcessor);

        tnbArgs.transportFactory(new TFramedTransport.Factory());
        tnbArgs.protocolFactory(new TCompactProtocol.Factory());

        // 使用非阻塞式IO，服务端和客户端需要指定 TFramedTransport 数据传输的方式。
        TServer server = new TNonblockingServer(tnbArgs);

        server.serve();
    }

    public static void main(String[] args) {
        HelloServerDemo server = new HelloServerDemo();
        server.startServer();
    }


}
