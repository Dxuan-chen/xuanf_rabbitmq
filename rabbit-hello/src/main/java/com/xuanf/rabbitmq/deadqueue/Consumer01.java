package com.xuanf.rabbitmq.deadqueue;


import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.xuanf.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

//消费者01
public class Consumer01 {

    //普通交换机的名称
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    //死信交换机的名称
    public static final String DEAD_EXCHANGE = "dead_exchange";
    //普通队列的名称
    public static final String NORMAL_QUEUE = "normal_queue";
    //死信队列的名称
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //声明普通和死信交换机，类型为direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE,BuiltinExchangeType.DIRECT);

        //声明普通队列
        Map<String, Object> arguments = new HashMap<>();

        //正常队列绑定死信队列信息
        //过期时间  10s=10000ms
        //arguments.put("x-message-ttl",100000);//到生产者动态设置
        //正常队列设置过期之后的死信交换机是谁
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //正常队列设置死信 Routing-Key 参数key是固定值
        arguments.put("x-dead-letter-routing-key","lisi");
        //设置正常队列的长度限制
//        arguments.put("x-max-length",6);
        channel.queueDeclare(NORMAL_QUEUE,false,false,false,arguments);
        //绑定普通的交换机与普通的队列
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");

        //声明死信队列
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);

        //绑定死信的交换机与死信队列
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");
        System.out.println("等待接收消息....");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody(),"UTF-8");
            if("info5".equals(msg)) {
                System.out.println("Consumer01接收的消息是："+msg+"：此消息是被C1拒绝的");
                ////requeue 设置为 false 代表拒绝重新入队 该队列如果配置了死信交换机将发送到死信队列中
                channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
            }else {
                System.out.println("Consumer01接收的消息是："+msg);
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            }
            System.out.println("Consumer01接收的消息是：" + msg);
        };
        channel.basicConsume(NORMAL_QUEUE,false,deliverCallback,consumerTag -> {});
    }
}
