package com.xuanf.rabbitmq.utils;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/9/11
 */
public class SleepUtils {
    public static void sleep(int second) {
        try {
            Thread.sleep(1000*second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
