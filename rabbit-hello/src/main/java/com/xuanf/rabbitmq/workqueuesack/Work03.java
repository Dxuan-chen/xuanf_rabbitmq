package com.xuanf.rabbitmq.workqueuesack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.xuanf.rabbitmq.utils.RabbitMqUtils;
import com.xuanf.rabbitmq.utils.SleepUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/9/11
 */
public class Work03 {
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C1等待接收消息处理时间较短");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            SleepUtils.sleep(1);
            System.out.println("接收到的消息："+new String(message.getBody(), StandardCharsets.UTF_8));
            //手动应答
            /*
            * 1.消息的标记 tag（在消息的属性里边）
            * 2.是否批量应答 false:不批量应答信道中的消息
            */
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);

        };

        //设置不公平分发
        //int prefetchCount = 1;
        //预取值2
        int prefetchCount = 2;
        channel.basicQos(prefetchCount);

        //采用手动应答
        channel.basicConsume(TASK_QUEUE_NAME,false,deliverCallback,(consumerTag -> {
            System.out.println("consumerTag"+"消费者取消消费接口回调逻辑");
        }));
    }
}
