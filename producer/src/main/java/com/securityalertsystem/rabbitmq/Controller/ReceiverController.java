package com.securityalertsystem.rabbitmq.Controller;


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
import org.apache.commons.lang3.SerializationUtils;
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

import static com.securityalertsystem.rabbitmq.Controller.SenderController.TYPE;


@Component
@RestController
@RequestMapping("/messageReceiver")
public class ReceiverController {
    @Autowired
    ClientRepository clientRepository;


    private List<String> receivedMessages = new ArrayList<>();
    private List<Integer> high_client = new ArrayList<>();
    private List<Integer> mid_client = new ArrayList<>();
    private List<Integer> low_client = new ArrayList<>();



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

    public void onAlertMessage(String exchangeName,int clientId,int priority) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String queueName = "queue"+clientId;
        channel.exchangeDeclare(exchangeName,"fanout",true);
        channel.queueDeclare(queueName, true, false, true, null); //durable, automatically deleted
        channel.queueBind(queueName,exchangeName,"");

        DeliverCallback deliverCallback = (consumerTag, delivery)->{
            AlertMessage message =  SerializationUtils.deserialize(delivery.getBody());

            receivedMessages.add(transferMessage(clientId,priority,message));
        };
        channel.basicConsume(queueName,true,deliverCallback,consumerTag->{});

    }


    private String transferMessage(int consumer,int priority,AlertMessage message){
        System.err.println("----------received message-----------");
        System.err.println("message ID: "+message.getMessageId());
        message.setReceivedTime(System.currentTimeMillis()-message.getReceivedTime());
        String result = "<p>"+consumer+" "+"priority="+priority+" "+
                "MessageId: "+message.getMessageId()+" "+
                "Location: "+message.getLocation()+" "+
                "Emergency Type: "+message.getType()+" "+
                "Happen Time: "+message.getHappenTime()+" " +
                "Time gap of receiving message: "+message.getReceivedTime()+"</p>";
        return result;

    }

    @RequestMapping("/createQueue")
    public String createQueue(){
        List<Client> clients = clientRepository.findAll();
        if(clients.size()==0){
            return "Need get clients information. Please input url \"/getClients\"";
        }
        if(TYPE.equals("")){
            return "There is no Message";
        }
        boolean useCurLoc = TYPE.equals(Constants.GUNSHOT)||
                TYPE.equals(Constants.ROBBERY)||
                TYPE.equals(Constants.SEXASSUALT);

        for(Client client:clients){
            double distance;
            if(useCurLoc){
                distance = Math.pow(SenderController.latitude-client.getLocationx(),2)+
                        Math.pow(SenderController.longitude-client.getLocationy(),2);
            }else{
                distance = Math.pow(SenderController.latitude-client.getAddressx(),2)+
                        Math.pow(SenderController.longitude-client.getAddressy(),2);
            }

            if(distance<=40){
                high_client.add(client.getClientId());
            }else if(distance>40 && distance<=100){
                mid_client.add(client.getClientId());
            }else{
                low_client.add(client.getClientId());
            }
        }
        for(int id:high_client){
            try {
                onAlertMessage("alert-exchange0",id,0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for(int id:mid_client){
            try {
                onAlertMessage("alert-exchange1",id,1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for(int id:low_client){
            try {
                onAlertMessage("alert-exchange2",id,2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "Queue Created!";
    }

    @RequestMapping("/getMsg")
    public String getMsg(){
        String result = "";
        if(receivedMessages.size()>0){
            for(String receivedMessage:receivedMessages){
                result += receivedMessage;
            }
        }
        return result;
    }
}
