package com.securityalertsystem.rabbitmq.consumer;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.securityalertsystem.rabbitmq.Constants.Constants;
import com.securityalertsystem.rabbitmq.Controller.ClientController;
import com.securityalertsystem.rabbitmq.Controller.SenderController;
import com.securityalertsystem.rabbitmq.entity.AlertMessage;
import com.securityalertsystem.rabbitmq.entity.Client;
import com.securityalertsystem.rabbitmq.repository.ClientRepository;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.securityalertsystem.rabbitmq.Controller.ClientController.clients;


@Component
@RestController
public class MessageReceiver {


    private List<String> receivedMessages = new ArrayList<>();



//    @RabbitListener(
//            bindings = @QueueBinding(
//                    value = @Queue(value = "alert-queue0",durable="true"),
//                    exchange = @Exchange(name = "alert-exchange0",durable = "true",type="fanout"),
//                    key = "alert.*"
//            )
//    )
//    @RabbitHandler
//    public void onOrderMessage0(@Payload AlertMessage message,
//                                @Headers Map<String,Object> headers,
//                                Channel channel) throws Exception{
//        String consumerNum = "Consumer with priority 0";
//        receiveMessage(consumerNum,message,headers,channel);
//    }

    public void onAlertMessage(String exchangeName,String queueName) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName,"fanout");
        channel.queueBind(queueName,exchangeName,"");

        DeliverCallback deliverCallback = (consumerTag, delivery)->{
            String message = new String(delivery.getBody(),"UTF-8");
            receivedMessages.add(message);
        };
        channel.basicConsume(queueName,true,deliverCallback,consumerTag->{});

    }


//    private void receiveMessage(String consumer,AlertMessage message,Map<String,Object> headers,
//                                Channel channel) throws Exception{
//        System.err.println("----------received message-----------");
//        System.err.println("message ID: "+message.getMessageId());
//        message.setReceivedTime(System.currentTimeMillis()-message.getReceivedTime());
//        String result = "<p>"+consumer+" "+
//                "MessageId: "+message.getMessageId()+" "+
//                "Location: "+message.getLocation()+" "+
//                "Emergency Type: "+message.getType()+" "+
//                "Happen Time: "+message.getHappenTime()+" " +
//                "Time gap of receiving message: "+message.getReceivedTime()+"</p>";
//        receivedMessages.add(result);
//
//        Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
//        channel.basicAck(deliveryTag,false);
//    }

    @RequestMapping("/message")
    public String getMsg(){
        if(clients.size()==0){
            return "Need get clients information. Please input url \"/getClients\"";
        }
        boolean useCurLoc;
        if(SenderController.TYPE.equals(Constants.FIRE)||
                SenderController.TYPE.equals(Constants.EARTHQUAKE)||
                SenderController.TYPE.equals(Constants.FLOODING)){
            useCurLoc = false;
        }else{
            useCurLoc = true;
        }
        for(Client client:clients){
            double distance;
            if(useCurLoc){
                distance = Math.pow(SenderController.latitude-client.getLocationx(),2)+
                        Math.pow(SenderController.longitude-client.getLocationy(),2);
            }else{
                distance = Math.pow(SenderController.latitude-client.getAddressx(),2)+
                        Math.pow(SenderController.longitude-client.getAddressy(),2);
            }

            if(distance<=3){
                try {
                    onAlertMessage("alert-exchange0","queue"+client.getClientId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(distance>3 && distance<=10){
                try {
                    onAlertMessage("alert-exchange1","queue"+client.getClientId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    onAlertMessage("alert-exchange2","queue"+client.getClientId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        String result = "";
        if(receivedMessages.size()>0){
            for(String receivedMessage:receivedMessages){
                result += receivedMessage;
            }
        }
        return result;
    }
}
