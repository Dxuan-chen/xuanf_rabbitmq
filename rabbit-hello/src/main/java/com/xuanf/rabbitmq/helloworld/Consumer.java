package com.xuanf.rabbitmq.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/9/10
 */
public class Consumer {

    //队列的名称
    public static final String QUEUE_NAME = "hello";
    //接收消息
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.121.131");
        factory.setUsername("admin");
        factory.setPassword("123");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        //声明 成功接收消息的回调
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println(new String(message.getBody()));
        };

        //取消消息时的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被中断");
        };

        /*
        * 消费者消费消息
        * 1.消费哪个队列
        * 2.消费成功后是否要自动应答 true自动应答 false手动应答
        * 3.消费消息成功时的回调
        * 4.消费者取消消息的回调
        */
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
