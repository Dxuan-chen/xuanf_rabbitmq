package com.xuanf.rabbitmq.workqueues;

import com.rabbitmq.client.Channel;
import com.xuanf.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/9/10
 */
public class Task01 {
    //队列名称
    public static final String QUEUE_NAME = "hello2";

    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = RabbitMqUtils.getChannel();
        /*
         * 生成一个队列
         * 1.队列名称
         * 2.队列里面的消息是否持久化（磁盘），默认情况消息存储在内存中
         * 3.该队列是否只供一个消费者进行消费，是否进行消息共享，true表示只能一个消费者消费
         * 4.是否自动删除 最后一个消费者断开连接后，该队列是否自动删除，true自动删除，false不自动删除
         * 5.其他参数
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String message = scanner.next();
            /*
             * 发送一个消息
             * 1.发送到哪个交换机
             * 2.路由的Key值是哪个，本次是队列的名称
             * 3.其他参数信息
             * 4.发送消息的消息体
             */
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("发送消息完成："+message);
        }
    }





}
