package main

import (
	"git.apache.org/thrift.git/lib/go/thrift"
	"net"
	"fmt"
	"log"
	"context"
	"pkg/service"
)

const (
	HOST = "localhost"
	PORT = "8082"
)

func main()  {
	tSocket, err := thrift.NewTSocket(net.JoinHostPort(HOST, PORT))
	if err != nil {
		log.Fatalln("tSocket error:", err)
	}
	transportFactory := thrift.NewTFramedTransportFactory(thrift.NewTTransportFactory())
	transport,err := transportFactory.GetTransport(tSocket)
	if err != nil{
		panic("-----------------")
	}
	protocolFactory := thrift.NewTBinaryProtocolFactoryDefault()

	client := service.NewFormatDataClientFactory(transport, protocolFactory)

	if err := transport.Open(); err != nil {
		log.Fatalln("Error opening:", HOST + ":" + PORT)
	}
	defer transport.Close()


	data := service.Data{Text:"hello,world!"}

	ctx := context.Background()

	//注意，这里跟网上的其他版本不一样，需要添加ctx参数才可以的
	d, err := client.DoFormat(ctx, &data)

	fmt.Println("--->client receiver from server:\t"+d.Text)
}
