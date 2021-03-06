package com.securityalertsystem.rabbitmq.Controller;



import com.securityalertsystem.rabbitmq.entity.AlertMessage;
import com.securityalertsystem.rabbitmq.producer.AlertSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/messageSender")
public class SenderController {

    public static String TYPE = "";
    public static double latitude;
    public static double longitude;

    @Autowired
    private AlertSender alertSender;


    private static String happenTime = new Date().toString();
    private static long sendTime;



    @RequestMapping(value="/send")
    public String sendAlerts(){
        sendAlertNearby(TYPE);
        sendAlertMid(TYPE);
        sendAlertFaraway(TYPE);
        return "Messages Sent Successfully";
    }
    @RequestMapping(value="/create/{type}")
    public String createAlerts(@PathVariable(name = "type") String type){
        TYPE = type;
        latitude = 45+Math.random()*30;
        longitude = 40+Math.random()*30;
        return "Messages Created Successfully";
    }

    public void sendAlertNearby(String type){ //Maybe 1-3 miles
        AlertMessage message = new AlertMessage();
        message.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        message.setHappenTime(happenTime);
        message.setLocation("Within 3 miles");
        message.setType(type);
        sendTime = System.currentTimeMillis();
        message.setReceivedTime(sendTime);
        try {
            alertSender.send1(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendAlertMid(String type){ //Maybe 3-10 miles or further
        AlertMessage message = new AlertMessage();
        message.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        message.setHappenTime(happenTime);
        message.setLocation("3-10 miles away");
        message.setType(type);
        message.setReceivedTime(sendTime);
        try {
            alertSender.send2(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendAlertFaraway(String type){ //Maybe 10 miles or further
        AlertMessage message = new AlertMessage();
        message.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        message.setHappenTime(happenTime);
        message.setLocation("Further than 10 miles");
        message.setType(type);
        message.setReceivedTime(sendTime);
        try {
            alertSender.send3(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
