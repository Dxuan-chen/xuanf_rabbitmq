package com.xuanf.rabbitmq.deadqueue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.xuanf.rabbitmq.utils.RabbitMqUtils;
import com.xuanf.rabbitmq.utils.SleepUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 * 死信队列 之 生产者
 */
public class Producer {
    //普通交换机名称
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //死信消息 设置TTL时间 单位：ms 10000ms=10s
//        AMQP.BasicProperties props =
//                new AMQP.BasicProperties().builder().expiration("10000").build();
        for(int i = 1;i < 11;i++){
            String message = "info"+i;
//            SleepUtils.sleep(1);
            channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",null,message.getBytes());
        }
    }
}
