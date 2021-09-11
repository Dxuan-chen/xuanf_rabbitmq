package com.xuanf.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/9/10
 */
public class Producer {
    //队列名称
    public static final String QUEUE_NAME = "hello";

    //发消息
    public static void main(String[] args) {
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //工厂IP 连接RabbitMQ的队列
        factory.setHost("192.168.121.131");
        //用户名
        factory.setUsername("admin");
        //密码
        factory.setPassword("123");
        //创建连接
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        //获取信道
        Channel channel = null;
        try {
            channel = connection.createChannel();
        } catch (IOException e) {
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
        try {
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //发送消息
        String message = "hello world";

        /*
        * 发送一个消息
        * 1.发送到哪个交换机
        * 2.路由的Key值是哪个，本次是队列的名称
        * 3.其他参数信息
        * 4.发送消息的消息体
        */
        try {
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("消息发送完毕");

    }
}
