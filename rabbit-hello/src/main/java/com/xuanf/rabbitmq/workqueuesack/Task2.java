package com.xuanf.rabbitmq.workqueuesack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.xuanf.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 * 消息在手动应答时是不丢失的，当丢失时，将消息放回队列中重新消费。
 */
public class Task2 {

    //队列名称
    public static final String TASK_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //声明队列
        boolean durable = true;//让队列持久化
        channel.queueDeclare(TASK_NAME,durable,false,false,null);
        Scanner scanner = new Scanner(System.in);
        String msg = "aaa";
        channel.basicPublish("",TASK_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
//        while (scanner.hasNext()) {
//            String message = scanner.next();
//           System.out.println("生产者发出消息："+ message);
//        }

    }
}
