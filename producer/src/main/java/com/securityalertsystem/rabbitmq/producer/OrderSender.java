package com.securityalertsystem.rabbitmq.producer;

import com.securityalertsystem.rabbitmq.constant.Constants;
import com.securityalertsystem.rabbitmq.entity.BrokerMessageLog;
import com.securityalertsystem.rabbitmq.entity.Order;
import com.securityalertsystem.rabbitmq.mapper.BrokerMessageLogMapper;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.Configuration;
import java.util.Date;

@Component
public class OrderSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("correlationData: "+correlationData);
            String messageId = correlationData.getId();
            if(ack){
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageId, Constants.ORDER_SEND_SUCCESS,new Date());
            }else{
                System.err.println("exception process");
            }
        }
    };

    public void send(Order order) throws  Exception{
        rabbitTemplate.setConfirmCallback(confirmCallback);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange",
                "order.abcd",
                order,
                correlationData);
    }
}
