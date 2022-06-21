package com.demo.grpc;

import com.demo.grpc.api.DemoRequest;
import com.demo.grpc.api.DemoResponse;
import com.demo.grpc.api.DemoServiceGrpc;
import com.google.protobuf.Any;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.*;

public class GrpcClient {
    public static void main(String[] args) {
        //构建链接信息(并没有真正连接)
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",6666)
                .usePlaintext().build();
        try {

            List<String> testArray = Arrays.asList(new String[]{"item0","item1","item2","item3",});
            Map<String,String> testMap = new HashMap<String, String>(){
                {
                    put("testA","A");
                    put("testB","B");
                }
            };
            //为了测试 any 不是真正的返回结构体
            DemoResponse testAny = DemoResponse.newBuilder().setMessage("for any").build();

            //构建请求信息
            DemoRequest request = DemoRequest.newBuilder()
                    .setTestInt(1)
                    .setTestLong(2L)
                    .setTestString("string")
                    //添加repeated要用addList
                    .addAllTestArray(testArray)
                    //map可以直接放入Map<>
                    .putAllTestMap(testMap)
                    //也可以放入KV对
                    .putTestMap("testC","C")
                    //any可以是proto中定义的任意结构体
                    .setTestAny(Any.pack(testAny))
                    .build();

            //真正创建连接
            DemoServiceGrpc.DemoServiceBlockingStub stub = DemoServiceGrpc.newBlockingStub(channel);
            //传入请求 接收返回
            DemoResponse demoResponse = stub.test(request);
            System.out.println("res code is : " + demoResponse.getCode() + ", message is : " + demoResponse.getMessage());
        }finally {
            channel.shutdown();
        }
    }
}