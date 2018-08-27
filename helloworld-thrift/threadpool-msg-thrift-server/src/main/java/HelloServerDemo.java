import com.xej.thrift.demo.HelloWorldService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import server.HelloWorldServiceImpl;

public class HelloServerDemo {
    public static final int port = 8090;

    public void startServer() {
        System.out.println("HelloWorld TThreadPoolServer start ....");

        TProcessor tProcessor = new HelloWorldService.Processor<>(new HelloWorldServiceImpl());

        TServerSocket serverTransport = null;

        try {
            serverTransport = new TServerSocket(port);
        } catch (TTransportException e) {
            e.printStackTrace();
        }

        TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(serverTransport);
        ttpsArgs.processor(tProcessor);

        ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());

        // 线程池服务模型，使用标准的阻塞式IO， 预先创建一组线程处理请求
        TServer server = new TThreadPoolServer(ttpsArgs);

        server.serve();
    }

    public static void main(String[] args) {
        HelloServerDemo server = new HelloServerDemo();
        server.startServer();
    }


}
