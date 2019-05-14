package com.securityalertsystem;

import com.securityalertsystem.rabbitmq.entity.AlertMessage;
import com.securityalertsystem.rabbitmq.producer.AlertSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {


    @Autowired
    private AlertSender alertSender;


    private static String happenTime = new Date().toString();
    private static long sendTime ;
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
        sendTime = System.currentTimeMillis();
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

}
