package com.securityalertsystem.rabbitmq.order;

import com.securityalertsystem.rabbitmq.entity.Order;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


public class OrderReceiver {

    Order receivedOrder;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "order-queue",durable="true"),
                    exchange = @Exchange(name = "order-exchange",durable = "true",type="topic"),
                    key = "order.*"
            )
    )
    @RabbitHandler
    public void onOrderMessage(@Payload Order order,
                               @Headers Map<String,Object> headers,
                               Channel channel) throws Exception{
        //Consumer Operation
        System.err.println("----------received message-----------");
        System.err.println("order ID: "+order.getId());
        receivedOrder = order;
        Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag,false);
    }

    @RequestMapping("/message")
    public String hello(){
        return receivedOrder.getId();
    }

}
