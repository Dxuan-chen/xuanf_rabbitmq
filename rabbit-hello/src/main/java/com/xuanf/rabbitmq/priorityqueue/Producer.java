package com.xuanf.rabbitmq.priorityqueue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xuanf.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 * 优先级队列
 */
public class Producer {
    //队列名称
    public static final String QUEUE_NAME = "hello";

    //发消息
    public static void main(String[] args) throws IOException {
        Channel channel = null;
        try {
            channel = RabbitMqUtils.getChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

        /*
         * 生成一个队列
         * 1.队列名称
         * 2.队列里面的消息是否持久化（磁盘），默认情况消息存储在内存中
         * 3.该队列是否只供一个消费者进行消费，是否进行消息共享，true表示只能一个消费者消费
         * 4.是否自动删除 最后一个消费者断开连接后，该队列是否自动删除，true自动删除，false不自动删除
         * 5.其他参数
         */
        Map<String, Object> arguments = new HashMap<>();
        //此处设置最大优先级为10 则允许的范围为0-10
        arguments.put("x-max-priority", 10);
        try {
            assert channel != null;
            channel.queueDeclare(QUEUE_NAME, true, false, false, arguments);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //发送消息
        for (int i = 1; i < 11; i++) {
            String message = "info" + i;
            if (i == 5) {

                AMQP.BasicProperties properties =
                        new AMQP.BasicProperties().builder().priority(5).build();
                channel.basicPublish("", QUEUE_NAME, properties, message.getBytes());

            } else {
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            }
        }

        System.out.println("消息发送完毕");

    }
}
