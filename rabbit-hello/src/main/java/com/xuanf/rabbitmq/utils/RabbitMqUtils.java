package com.xuanf.rabbitmq.utils;

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
public class RabbitMqUtils {
//    static ConnectionFactory factory;
//    static Connection connection;
//    static {
//        //创建连接工厂
//        factory = new ConnectionFactory();
//        factory.setHost("192.168.121.131");
//        factory.setUsername("admin");
//        factory.setPassword("123");
//        try {
//            connection = factory.newConnection();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
//    }

    public static Channel getChannel() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("192.168.121.131");
//        factory.setUsername("admin");
//        factory.setPassword("123");
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();

        return connection.createChannel();
    }


}
