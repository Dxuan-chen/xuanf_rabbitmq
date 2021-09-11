package com.xuanf.rabbitmq.workqueues;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.xuanf.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 * 一个工作线程（相当于之前的消费者）
 */
public class Worker01 {

    //队列的名称
    public static final String QUEUE_NAME = "hello2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();

        //声明 成功接收消息的回调
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("接收到的消息"+new String(message.getBody()));
        };

        //取消消息时的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println(consumerTag+"消费者取消消费接口回调逻辑");
        };
        /*
         * 消费者消费消息
         * 1.消费哪个队列
         * 2.消费成功后是否要自动应答 true自动应答 false手动应答
         * 3.消费消息成功时的回调
         * 4.消费者取消消息的回调
         */
        System.out.println("C2等待接收消息....");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }

}
