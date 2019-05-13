package com.securityalertsystem.rabbitmq.producer;

import com.securityalertsystem.rabbitmq.entity.AlertMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class alertSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send1(AlertMessage message) throws  Exception{

        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(message.getMessageId());
        rabbitTemplate.convertAndSend("alert-exchange0",
                "alert.abc",
                message,
                correlationData);
    }
    public void send2(AlertMessage message) throws  Exception{

        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(message.getMessageId());
        rabbitTemplate.convertAndSend("alert-exchange1",
                "alert.abc",
                message,
                correlationData);
    }
    public void send3(AlertMessage message) throws  Exception{

        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(message.getMessageId());
        rabbitTemplate.convertAndSend("alert-exchange2",
                "alert.abc",
                message,
                correlationData);
    }
}
