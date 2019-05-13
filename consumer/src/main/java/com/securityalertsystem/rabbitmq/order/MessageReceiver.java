package com.securityalertsystem.rabbitmq.order;

import com.rabbitmq.client.Channel;
import com.securityalertsystem.rabbitmq.entity.AlertMessage;
import com.securityalertsystem.rabbitmq.entity.Order;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@RestController
public class MessageReceiver {

    private List<String> receivedMessages = new ArrayList<>();

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "alert-queue0",durable="true"),
                    exchange = @Exchange(name = "alert-exchange0",durable = "true",type="topic"),
                    key = "alert.*"
            )
    )
    @RabbitHandler
    public void onOrderMessage0(@Payload AlertMessage message,
                               @Headers Map<String,Object> headers,
                               Channel channel) throws Exception{
        String consumerNum = "Consumer with priority 0";
        receiveMessage(consumerNum,message,headers,channel);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "alert-queue1",durable="true"),
                    exchange = @Exchange(name = "alert-exchange1",durable = "true",type="topic"),
                    key = "alert.*"
            )
    )
    @RabbitHandler
    public void onOrderMessage1(@Payload AlertMessage message,
                               @Headers Map<String,Object> headers,
                               Channel channel) throws Exception{
        //Consumer Operation
        String consumerNum = "Consumer with priority 1";
        receiveMessage(consumerNum,message,headers,channel);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "alert-queue2",durable="true"),
                    exchange = @Exchange(name = "alert-exchange2",durable = "true",type="topic"),
                    key = "alert.*"
            )
    )
    @RabbitHandler
    public void onOrderMessage2(@Payload AlertMessage message,
                                @Headers Map<String,Object> headers,
                                Channel channel) throws Exception{
        //Consumer Operation
        String consumerNum = "Consumer with priority 2";
        receiveMessage(consumerNum,message,headers,channel);
    }

    public void receiveMessage(String consumer,AlertMessage message,Map<String,Object> headers,
                               Channel channel) throws Exception{
        System.err.println("----------received message-----------");
        System.err.println("message ID: "+message.getMessageId());
        message.setReceivedTime(System.currentTimeMillis()-message.getReceivedTime());
        String result = "<p>"+consumer+" "+
                "MessageId: "+message.getMessageId()+" "+
                "Location: "+message.getLocation()+" "+
                "Emergency Type: "+message.getType()+" "+
                "Happen Time: "+message.getHappenTime()+" " +
                "Time gap of receiving message: "+String.valueOf(message.getReceivedTime())+"</p>";
        receivedMessages.add(result);

        Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag,false);
    }

    @RequestMapping("/message")
    public String hello(){
        String result = "";
        if(receivedMessages.size()>0){
            for(String receivedMessage:receivedMessages){
                result += receivedMessage;
            }
        }
        return result;
    }
}
