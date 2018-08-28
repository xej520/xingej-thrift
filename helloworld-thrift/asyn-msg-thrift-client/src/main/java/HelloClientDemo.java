import com.xej.thrift.demo.HelloWorldService;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

// 线程池模型下
// 客户端与简单单线程模式是一样的
public class HelloClientDemo {

    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8090;
    public static final int TIMEOUT = 30000;

    public void startClient(String userName) {
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            TNonblockingTransport transport = new TNonblockingSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            TProtocolFactory tprotocol = new TCompactProtocol.Factory();

            HelloWorldService.AsyncClient asyncClient = new HelloWorldService.AsyncClient(tprotocol, clientManager, transport);
            System.out.println("Client start .......");

            CountDownLatch latch = new CountDownLatch(1);

            AsynCallback callback = new AsynCallback(latch);

            System.out.println("call method sayHello start ...");

            asyncClient.sayHello(userName, callback);

            System.out.println("call method sayHello .... end");
            boolean wait = latch.await(30, TimeUnit.SECONDS);
            System.out.println("latch.await =\t" + wait);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    注意这里，跟文档中的不一样，是String类型
    public class AsynCallback implements AsyncMethodCallback<String> {
        private CountDownLatch latch;

        public AsynCallback(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onComplete(String response) {
            System.out.println("---->onComplete--->");
            try {
                // Thread.sleep(1000L * 1);
//                System.out.println("AsynCall result =:" + response.getResult().toString());
                System.out.println("AsynCall result =\t" + response);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }

        @Override
        public void onError(Exception exception) {
            System.out.println("onError :" + exception.getMessage());
            latch.countDown();
        }
    }


    public static void main(String[] args) {
        HelloClientDemo client = new HelloClientDemo();
        client.startClient("china");
    }

}













