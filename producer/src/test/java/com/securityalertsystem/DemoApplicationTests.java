package com.securityalertsystem;

import com.securityalertsystem.rabbitmq.entity.Order;
import com.securityalertsystem.rabbitmq.producer.OrderSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private OrderSender orderSender;

    @Test
    public void testSend1() throws Exception{
        Order order = new Order();
        order.setId("2019090800000001");
        order.setName("test order 1");
        order.setMessageId(System.currentTimeMillis()+"$"+ UUID.randomUUID().toString());
        orderSender.send(order);
    }
}
