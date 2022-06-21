package com.demo.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
  public static void main(String[] args) {
    Server server=null;
    try {
      //这里面还可以加入拦截器,过滤器,超时等
      server = ServerBuilder.forPort(6666)
              .addService(new MyDemoServiceImpl())
              .build().start();
      //持续等待消息
      server.awaitTermination();
    } catch (Exception e) {
      System.out.println("服务端出错"+e);
    } finally {
      if (server!=null) {
        server.shutdown();
      }
    }
  }
}