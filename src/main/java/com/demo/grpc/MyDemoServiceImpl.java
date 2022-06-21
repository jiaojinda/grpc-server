package com.demo.grpc;

import com.demo.grpc.api.DemoRequest;
import com.demo.grpc.api.DemoResponse;
import com.demo.grpc.api.DemoServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.Random;

//实现生成文件里的ImplBase 并且在类里重写proto中定义的方法
public class MyDemoServiceImpl extends DemoServiceGrpc.DemoServiceImplBase {
    @Override
    public void test(DemoRequest request, StreamObserver<DemoResponse> responseObserver) {
        try {
            //打印请求
            System.out.println("收到请求:");
            System.out.println(request.getTestInt());
            System.out.println(request.getTestLong());
            System.out.println(request.getTestString());
            System.out.println("list size is :" + request.getTestArrayList().size());
            System.out.println("map size is :" + request.getTestMapMap().size());
            System.out.println("nested.a is : " + request.getTestNested().getA());
            System.out.println("nested.b is : " + request.getTestNested().getB());
            System.out.println("any is : " + request.getTestAny().unpack(DemoResponse.class).getMessage());


            //构建响应
            DemoResponse.Builder resBuilder = DemoResponse.newBuilder();
            //随机测试oneof
            if(new Random().nextBoolean()){
                resBuilder.setCode(200);
            }else{
                resBuilder.setMessage("success");
            }
            //发送数据
            responseObserver.onNext(resBuilder.build());
            //因为是🏥请求 结束链接
            responseObserver.onCompleted();
        } catch (Exception e) {
            System.out.println("server 方法体出现错误" + e.getMessage());
            //发送错误,onError自动断开
            responseObserver.onError(e);
        }
    }
}