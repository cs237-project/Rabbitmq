package com.securityalertsystem.rabbitmq.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.securityalertsystem.rabbitmq.entity.Order;

public class FastJsonConvertUtil {

    public static Order convertJSONToObject(String message, Object obj){
        Order order = JSON.parseObject(message, new TypeReference<Order>() {});
        return order;
    }


    public static String convertObjectToJSON(Object obj){
        String text = JSON.toJSONString(obj);
        return text;
    }
}