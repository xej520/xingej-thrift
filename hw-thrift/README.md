    继续测试练习thrift  
# 环境说明  
- windows 10
- Intellij IDEA  
- thrift-0.11.0.exe
- 服务端用java实现  
- 客户端用go实现  
- 用例的作用是，客户端将字符串传递给服务器，服务器将字符串转换成大写后，返回给客户端
 
# 创建maven工程(父模块)
## 删除自带的src目录(目前没用，删掉)  
![](https://note.youdao.com/yws/public/resource/9c8584c6ec980aee585665c38a65bf9d/xmlnote/E000C835DF3D4A26B1EAE6EC85E33C77/20070)
## 准备IDL文件
- 创建目录thrift
- 创建IDL文件hw.thrift  

        namespace java com.test.thrift
        namespace  go pkg.service
        
        struct Data {
            1: string text;
        }
        
        service format_data {
            Data doFormat(1:Data data);
        }
![](https://note.youdao.com/yws/public/resource/9c8584c6ec980aee585665c38a65bf9d/xmlnote/B6151F77E7244F45B5F78DE2DB34CDE9/20072)

## 创建maven模块，用于存储生成的java版本的代码库  
![](https://note.youdao.com/yws/public/resource/9c8584c6ec980aee585665c38a65bf9d/xmlnote/861C5531ABCB4C6CBD1F3A269AB4B323/20074)  
&nbsp;
&nbsp;
- 利用thrift来生成java版本的代码库  
    thrift --gen java -out ../java-thrift-idl/src/main/java hw.thrift
![](https://note.youdao.com/yws/public/resource/9c8584c6ec980aee585665c38a65bf9d/xmlnote/6D5E4FA87B0B4B67B2E15B6F89E7E2E5/20076)
- 生成的代码报错，是由于缺少libthrift库引起的，因此在pom.xml文件中，添加必要的依赖  
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>hw-thrift</artifactId>
        <groupId>com.test.thrift</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>java-thrift-idl</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.apache.thrift</groupId>
            <artifactId>libthrift</artifactId>
            <version>0.11.0</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.7.0</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```   
- 添加完依赖@Override 注解报错的话，可能是由于thrift的版本不一致引起的。

## 创建go模块，用于存储生成go版本的代码库  
- 利用thrift来生成go版本的代码库  
    thrift --gen go  -out ../go-thrift-idl hw.thrift
![](https://note.youdao.com/yws/public/resource/9c8584c6ec980aee585665c38a65bf9d/xmlnote/42EAB0BE237340D6A8989EEF383753DE/20079)
- 注意生成的代码是有问题的[原因具体不详]  
  - 在hw.go文件中 oprot.Flush()抛异常，not enough arguments in call to oprot.Flush less... (Ctrl+F1)  
  - 解决措施：原因是缺少context.Context类型的参数，刚好方法中已经有，直接添加上就可以了，改成oprot.Flush(ctx)  
- 将生成的代码库pkg 拷贝到gopath的src路径下，这样客户端就可以使用代码库了，不然有可能找不到生成的代码库

## 创建maven模块，用于创建服务端  
- 创建maven模块，java-thrift-server 
- 更新pom.xml文件，添加对java-thrift-idl的依赖 
- 编写FormatDataImpl实现hw.thrift定义的接口
- 编写sever, 实现thrift编程

## 创建go模块，用于go版本的客户端  
- 创建go模块，go-thrift-client  
- 编写业务逻辑

## 测试  
- 启动服务器端 
- 启动客户端

















