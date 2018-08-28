参考文献：
    https://blog.csdn.net/zhu_tianwei/article/details/44002721  
    https://www.cnblogs.com/fingerboy/p/6424248.html  
    https://www.cnblogs.com/duanxz/p/5516558.html  
代码已托管到git上  
    https://github.com/xej520/xingej-thrift  
    
# thrift 协议栈  
    Thrift 对软件栈的定义非常的清晰，使得各个组件能够松散的耦合，
    针对不同的场景，选择不同方式去搭建服务。  
![](https://note.youdao.com/yws/public/resource/9c8584c6ec980aee585665c38a65bf9d/xmlnote/F70A9EFF42634560A12152AD347B90B5/20068)
1. thrift是一种c/s的架构体系.  
2. 在最上层是用户自行实现的业务逻辑代码.  
3. 第二层是由thrift编译器自动生成的代码，主要用于结构化数据的解析，发送和接收。  
4. TServer主要任务是高效的接受客户端请求，并将请求转发给Processor处理。  
5. Processor负责对客户端的请求做出响应，包括RPC请求转发，调用参数解析和用户逻辑调用，返回值写回等处理。  
6. 从TProtocol以下部分是thirft的传输协议和底层I/O通信。
   TProtocol是用于数据类型解析的，将结构化数据转化为字节流给TTransport进行传输。  
7. TTransport是与底层数据传输密切相关的传输层，负责以字节流方式接收和发送消息体，  
8. 不关注是什么数据类型。底层IO负责实际的数据传输，包括socket、文件和压缩数据流等。  
***  

- Transport  传输层  
    定义数据传输方式？
    - TCP/IP传输  
    - 内存共享传输 
    - 文件共享传输  
- Protocol   协议层  
    定义数据传输格式？  
    - 二进制  
    - JSON 或  XML
    - 压缩  
- Processor   处理层  
    这部分由定义的idl来生成，封装了协议输入输出流，并委托给用户实现的handler进行处理  
- Server   服务层  
    整合上述组件，提供网络模型(单线程/多线程/事件驱动)，最终形成真正的服务    
## 再说 传输层  ？  
  * TSocket- 使用堵塞式I/O进行传输，也是最常见的模式。
  * TFramedTransport- 使用非阻塞方式，按块的大小，进行传输，类似于Java中的NIO。
  * TFileTransport- 顾名思义按照文件的方式进程传输，虽然这种方式不提供Java的实现，但是实现起来非常简单。
  * TMemoryTransport- 使用内存I/O，就好比Java中的ByteArrayOutputStream实现。

  * TZlibTransport- 使用执行zlib压缩，不提供Java的实现。

## 再说 协议?  
  Thrift可以让你选择客户端与服务端之间传输通信协议的类别，  
  在传输协议上总体上划分为文本(text)和二进制(binary)传输协议, 为节约带宽，提供传输效率，  
  一般情况下使用二进制类型的传输协议为多数，但有时会还是会使用基于文本类型的协议，这需要根据项目/产品中的实际需求：
  * TBinaryProtocol – 二进制编码格式进行数据传输。  
  
  * TCompactProtocol – 这种协议非常有效的，使用Variable-Length Quantity (VLQ) 编码对数据进行压缩。
  * TJSONProtocol – 使用JSON的数据编码协议进行数据传输。
  * TSimpleJSONProtocol – 这种节约只提供JSON只写的协议，适用于通过脚本语言解析
  * TDebugProtocol – 在开发的过程中帮助开发人员调试用的，以文本的形式展现方便阅读。

*** 


# thrift 服务模型(或者说，Thrift高性能网络服务模型有哪些？)
- TSimpleServer服务模型  
  - 简单的单线程服务模型，一般用于测试   
- TThreadPoolServer 服务模型  
  -  线程池服务模型，标准的阻塞式IO，预先创建一组线程处理请求
- TNonblockingServer 服务模型  
  - 非阻塞式IO，服务端和客户端都需要显示的指定TFramedTransport 数据传输方式
- THsHaServer 服务模型  
  - 半同步半异步的服务模型，需要指定为:TFramedTransport 数据传输方式  
- 异步客户端  
***  

# 编写基本步骤  
## 服务端编码基本步骤:  
- 实现服务处理接口impl, 如HelloWorldServiceImpl
- 创建TProcessor 
- 创建TServerTransport  
- 创建TProtocol
- 创建TServer  
- 启动Server  
 
## 客户端编码基本步骤：  
- 创建Transport  
- 创建TProtocol  
- 基于TTransport和TProtocol创建Client
- 调用Client的相应方法  
***  
# 数据传输协议 有哪些？  
- TBinaryProtocol--> 二进制格式  
- TCompactProtocol--> 压缩格式  
- TJSONProtocol--> JSON格式 
- TSimpleJSONProtocol-->提供JSON只写协议，生成的文件很容器通过脚本语言解析  
tips：客户端和服务端的协议要一致
# Maven依赖  
```$xslt
<dependency>
    <groupId>org.apache.thrift</groupId>
    <artifactId>libthrift</artifactId>
    <version>0.11.0</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.5.8</version>
</dependency>
```  
&nbsp;  
&nbsp;  
&nbsp;  
&nbsp;  
&nbsp;  
&nbsp;  
&nbsp;  
&nbsp;  
&nbsp;  
&nbsp;  

