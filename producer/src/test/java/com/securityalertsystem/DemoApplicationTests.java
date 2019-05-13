package com.securityalertsystem;

import com.securityalertsystem.rabbitmq.entity.AlertMessage;
import com.securityalertsystem.rabbitmq.entity.Order;
import com.securityalertsystem.rabbitmq.producer.OrderSender;
import com.securityalertsystem.rabbitmq.producer.alertSender;
import com.securityalertsystem.rabbitmq.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    private static long counter = 0;

    @Autowired
    private OrderSender orderSender;

    @Autowired
    private alertSender alertSender;

    @Autowired
    private OrderService orderService;


    private static String happenTime = new Date().toString();
    private static long sendTime = System.currentTimeMillis();
    @Test
    public void sendAlerts(){
        sendAlertNearby();
        sendAlertMid();
        sendAlertFaraway();
    }

    public void sendAlertNearby(){ //Maybe 1-3 miles
        AlertMessage message = new AlertMessage();
        message.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        message.setHappenTime(happenTime);
        message.setLocation("Within 3 miles");
        message.setType("Flooding");
        message.setReceivedTime(sendTime);
        try {
            alertSender.send1(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendAlertMid(){ //Maybe 3-10 miles or further
        AlertMessage message = new AlertMessage();
        message.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        message.setHappenTime(happenTime);
        message.setLocation("3-10 miles away");
        message.setType("Robbery");
        message.setReceivedTime(sendTime);
        try {
            alertSender.send2(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendAlertFaraway(){ //Maybe 10 miles or further
        AlertMessage message = new AlertMessage();
        message.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        message.setHappenTime(happenTime);
        message.setLocation("Further than 10 miles");
        message.setType("Earthquake");
        message.setReceivedTime(sendTime);
        try {
            alertSender.send3(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void contextLoads() {
        Order order = new Order();
        order.setId("aaa");
        order.setName("测试消息a");
        order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        try {
            orderSender.send(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createOrders(){
        int numMessage = 1000;
        //得到long类型当前时间
        long l = System.currentTimeMillis();
//new日期对象
        Date date = new Date(l);
//转换提日期输出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String nyr = dateFormat.format(date);
        while(numMessage>0){
            createOrder(nyr+counter);
            counter++;
            numMessage--;
        }

    }
    /**
     * 测试订单创建
     */
    public void createOrder(String messageId){
        Order order = new Order();
        order.setId(messageId);
        order.setName("测试订单");
        order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        try {
            orderService.createOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
