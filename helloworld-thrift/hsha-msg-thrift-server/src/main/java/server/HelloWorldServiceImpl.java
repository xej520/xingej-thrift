package server;

import com.xej.thrift.demo.HelloWorldService;
import org.apache.thrift.TException;

public class HelloWorldServiceImpl implements HelloWorldService.Iface {
    @Override
    public String sayHello(String username) throws TException {
        return "[hsha]" + username + " welcome to thrift world";
    }
}
